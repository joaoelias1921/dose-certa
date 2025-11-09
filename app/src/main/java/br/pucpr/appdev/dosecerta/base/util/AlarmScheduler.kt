package br.pucpr.appdev.dosecerta.base.util

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.provider.AlarmClock
import android.widget.Toast
import java.util.Calendar
import br.pucpr.appdev.dosecerta.R

class AlarmScheduler(private val context: Context, val stringProvider: StringProvider) {
    fun scheduleDailyAlarm(
        medicineName: String,
        hour: Int,
        minute: Int
    ) {
        val intent = Intent(AlarmClock.ACTION_SET_ALARM).apply {
            putExtra(AlarmClock.EXTRA_HOUR, hour)
            putExtra(AlarmClock.EXTRA_MINUTES, minute)
            putExtra(
                AlarmClock.EXTRA_MESSAGE,
                "${stringProvider.getString(
                    R.string.alarm_time_to_take
                )} $medicineName!",
            )
            putExtra(
                AlarmClock.EXTRA_DAYS, arrayListOf(
                Calendar.MONDAY,
                Calendar.TUESDAY,
                Calendar.WEDNESDAY,
                Calendar.THURSDAY,
                Calendar.FRIDAY,
                Calendar.SATURDAY,
                Calendar.SUNDAY
            ))

            putExtra(AlarmClock.EXTRA_SKIP_UI, false)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }

        try {
            context.startActivity(intent)
        } catch (_: ActivityNotFoundException) {
            Toast.makeText(
                context,
                stringProvider.getString(
                    R.string.alarm_enable_something_went_wrong
                ),
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}