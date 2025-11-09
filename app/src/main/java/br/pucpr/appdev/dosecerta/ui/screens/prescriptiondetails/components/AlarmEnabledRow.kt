package br.pucpr.appdev.dosecerta.ui.screens.prescriptiondetails.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AlarmOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import br.pucpr.appdev.dosecerta.R
import br.pucpr.appdev.dosecerta.ui.theme.DoseCertaBlue
import br.pucpr.appdev.dosecerta.ui.theme.SecondaryText

@Composable
fun AlarmEnabledRow(
    onEnableAgain: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(
                8.dp
            )
        ) {
            Icon(
                imageVector = Icons.Default.AlarmOn,
                contentDescription = null,
                tint = DoseCertaBlue,
                modifier = Modifier.size(18.dp)
            )
            Text(
                text = stringResource(
                    R.string
                        .presc_details_alarm_enabled
                ),
                overflow = TextOverflow.Ellipsis,
                color = SecondaryText
            )
        }
        Button(
            onClick = onEnableAgain,
            shape = ShapeDefaults.Small,
            colors = ButtonDefaults.buttonColors(
                containerColor = DoseCertaBlue,
            ),
        ) {
            Text(text = stringResource(
                R.string.presc_details_enable_again
            ))
        }
    }
}