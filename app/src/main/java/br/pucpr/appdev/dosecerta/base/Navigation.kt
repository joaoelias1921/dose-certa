package br.pucpr.appdev.dosecerta.base

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import br.pucpr.appdev.dosecerta.base.util.AndroidStringProvider
import br.pucpr.appdev.dosecerta.repositories.AppPreferencesRepository
import br.pucpr.appdev.dosecerta.repositories.PrescriptionRepository
import br.pucpr.appdev.dosecerta.repositories.UserRepository
import br.pucpr.appdev.dosecerta.ui.screens.editprescription.EditPrescriptionScreen
import br.pucpr.appdev.dosecerta.ui.screens.myprescriptions.MyPrescriptionsScreen
import br.pucpr.appdev.dosecerta.ui.screens.newprescription.NewPrescriptionScreen
import br.pucpr.appdev.dosecerta.ui.screens.prescriptiondetails.PrescriptionDetailsScreen
import br.pucpr.appdev.dosecerta.ui.screens.startup.StartupScreen
import br.pucpr.appdev.dosecerta.ui.screens.auth.AuthScreen
import br.pucpr.appdev.dosecerta.ui.screens.welcome.WelcomeScreen

class Navigation {
    private lateinit var navController: NavHostController

    @Composable
    fun Create() {
        val applicationContext = LocalContext.current
        navController = rememberNavController()

        val stringProvider = remember { AndroidStringProvider(applicationContext) }
        val userRepository = remember { UserRepository() }
        val prescriptionsRepository = remember { PrescriptionRepository(stringProvider) }
        val appPreferencesRepository = remember { AppPreferencesRepository(applicationContext) }

        NavHost(navController = navController, startDestination = Routes.Startup.route) {
            composable(Routes.Startup.route) {
                StartupScreen(
                    userRepository = userRepository,
                    appPreferencesRepository = appPreferencesRepository,
                    onStartup = { destination -> navController.navigate(destination) {
                        popUpTo(Routes.Startup.route) { inclusive = true}
                    }}
                )
            }
            composable(Routes.Welcome.route) {
                WelcomeScreen(
                    appPreferencesRepository = appPreferencesRepository,
                    onBeginJourney = { navController.navigate(Routes.Auth.route) }
                )
            }
            composable(Routes.Auth.route) {
                AuthScreen(
                    repository = userRepository,
                    stringProvider = stringProvider,
                    onAuthSuccess = { navController.navigate(Routes.MyPrescriptions.route) {
                        popUpTo(Routes.Auth.route) { inclusive = true }
                        launchSingleTop = true
                    }
                })
            }
            composable(Routes.MyPrescriptions.route) {
                MyPrescriptionsScreen(
                    prescriptionsRepository = prescriptionsRepository,
                    userRepository = userRepository,
                    stringProvider = stringProvider,
                    onPrescriptionDetails = { prescriptionId ->
                      navController.navigate(
                          "${Routes.PrescriptionDetails.route}/${prescriptionId}"
                      )
                    },
                    onNewPrescription = {
                        navController.navigate(Routes.NewPrescription.route)
                    },
                    onEditPrescription = { prescriptionId ->
                        navController.navigate(
                            "${Routes.EditPrescription.route}/${prescriptionId}"
                        )
                    },
                    onSignOut = {
                        navController.navigate(Routes.Auth.route) {
                            popUpTo(Routes.MyPrescriptions.route) { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                )
            }
            composable(
                "${Routes.PrescriptionDetails.route}/{prescriptionId}",
                arguments = listOf(navArgument("prescriptionId") {
                    type = NavType.StringType
                })
            ) {
                PrescriptionDetailsScreen(
                    repository = prescriptionsRepository,
                    stringProvider = stringProvider,
                    onEditPrescription = { prescriptionId ->
                        navController.navigate(
                            "${Routes.EditPrescription.route}/${prescriptionId}"
                        )
                    },
                    onDeletePrescription = {
                        navController.navigate(Routes.MyPrescriptions.route) {
                            popUpTo(Routes.MyPrescriptions.route) { inclusive = true }
                            launchSingleTop = true
                        }
                    },
                    onNavigateBack = { navController.popBackStack() }
                )
            }
            composable(Routes.NewPrescription.route) {
                NewPrescriptionScreen(
                    repository = prescriptionsRepository,
                    onSavePrescription = {
                        navController.navigate(Routes.MyPrescriptions.route) {
                            popUpTo(Routes.NewPrescription.route) { inclusive = true }
                            launchSingleTop = true
                        }
                    },
                    onNavigateBack = { navController.popBackStack() }
                )
            }
            composable(
                route = "${Routes.EditPrescription.route}/{prescriptionId}",
                arguments = listOf(navArgument("prescriptionId") {
                    type = NavType.StringType
                })
            ) { backStackEntry ->
                EditPrescriptionScreen(
                    repository = prescriptionsRepository,
                    stringProvider = stringProvider,
                    onSavePrescription = {
                        navController.navigate(Routes.MyPrescriptions.route) {
                            popUpTo(Routes.MyPrescriptions.route) { inclusive = true }
                            launchSingleTop = true
                        }
                    },
                    onNavigateBack = { navController.popBackStack() }
                )
            }
        }
    }
}