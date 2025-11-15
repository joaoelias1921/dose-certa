/**
 * Import function triggers from their respective submodules:
 *
 * const {onCall} = require("firebase-functions/v2/https");
 * const {onDocumentWritten} = require("firebase-functions/v2/firestore");
 *
 * See a full list of supported triggers at https://firebase.google.com/docs/functions
 */

const { setGlobalOptions, firestore } = require("firebase-functions");
const { onSchedule } = require("firebase-functions/v2/scheduler");
const admin = require("firebase-admin");
const { onRequest } = require("firebase-functions/https");
const logger = require("firebase-functions/logger");

// For cost control, you can set the maximum number of containers that can be
// running at the same time. This helps mitigate the impact of unexpected
// traffic spikes by instead downgrading performance. This limit is a
// per-function limit. You can override the limit for each function using the
// `maxInstances` option in the function's options, e.g.
// `onRequest({ maxInstances: 5 }, (req, res) => { ... })`.
// NOTE: setGlobalOptions does not apply to functions using the v1 API. V1
// functions should each use functions.runWith({ maxInstances: 10 }) instead.
// In the v1 API, each function can only serve one request per container, so
// this will be the maximum concurrent request count.
setGlobalOptions({ maxInstances: 10 });
admin.initializeApp();
const db = admin.firestore();

// This uses Cloud Scheduler (from Google Cloud)
// Define the function that runs every minute
// The schedule format is Unix cron: 'minute hour day_of_month month day_of_week'
exports.checkAndSendNotifications = onSchedule(
    {
        schedule: "*/1 * * * *", // Runs every minute
        timeZone: "America/Sao_Paulo", // Set an appropriate timezone
    },
    async (event) => {
        logger.info("Running scheduled job to check notifications...");

        const now = admin.firestore.Timestamp.now();
        // Define a time window (e.g., the last 5 minutes) to catch relevant items reliably
        const fiveMinutesAgo = admin.firestore.Timestamp.fromDate(
            new Date(Date.now() - 5 * 60000)
        );

        try {
            // Query Firestore for tasks that are scheduled within this window AND haven't been sent yet
            const snapshot = await db
                .collection("notifications")
                .where("scheduledTime", "<=", now)
                .where("scheduledTime", ">=", fiveMinutesAgo)
                .where("sent", "==", false)
                .get();

            if (snapshot.empty) {
                logger.info("No pending notifications found.");
                return;
            }

            const messages = [];
            const updates = [];

            snapshot.forEach((doc) => {
                const data = doc.data();
                const fcmToken = data.fcmToken;

                messages.push({
                    topic: "time_to_take",
                    notification: {
                        title: data.title || "Scheduled Reminder",
                        body: data.body || "Your scheduled time has arrived.",
                    },
                    android: { priority: "high" },
                });

                // Prepare an update to mark this notification as sent
                updates.push(doc.ref.update({ sent: true, sentTime: now }));
            });

            if (messages.length > 0) {
                // Send all the FCM messages in a single batch operation
                await admin.messaging().send(messages[0]);
                logger.info(`Successfully sent notification`);
            }

            if (updates.length > 0) {
                // Mark the notifications as sent in Firestore
                await Promise.all(updates);
                logger.info(`Updated ${updates.length} documents as sent.`);
            }
        } catch (error) {
            logger.error("Error processing scheduled notifications:", error);
        }
    }
);

exports.androidPushNotification = firestore.onDocumentCreated(
    "users/{docId}",
    (event) => {
        const documentData = event.data.data();

        if (!documentData || !documentData.name) {
            return admin.messaging().send({
                topic: "new_user_registered",
                notification: {
                    title: "Olá!",
                    body: "Seja muito bem vindo ao DoseCerta. Você já pode começar a usar nossos serviços",
                },
                android: { priority: "high" },
            });
        }
        const firstName = documentData.name.split(" ")[0];

        return admin.messaging().send({
            topic: "new_user_registered",
            notification: {
                title: `Olá ${firstName}!`,
                body: "Seja muito bem vindo ao DoseCerta. Você já pode começar a usar nossos serviços",
            },
            android: { priority: "high" },
        });
    }
);
