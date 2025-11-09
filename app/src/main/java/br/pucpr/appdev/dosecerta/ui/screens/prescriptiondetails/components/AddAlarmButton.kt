package br.pucpr.appdev.dosecerta.ui.screens.prescriptiondetails.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AlarmAdd
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import br.pucpr.appdev.dosecerta.ui.theme.DoseCertaBlue
import br.pucpr.appdev.dosecerta.ui.theme.LightBorder
import br.pucpr.appdev.dosecerta.ui.theme.PrimaryText
import br.pucpr.appdev.dosecerta.ui.theme.SecondaryText

@Composable
fun AddAlarmButton(
    buttonLabel: String,
    onClick: () -> Unit
) {
    Button(
        onClick,
        shape = ShapeDefaults.ExtraLarge,
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = PrimaryText,
        ),
        border = BorderStroke(2.dp, LightBorder),
        contentPadding = PaddingValues(
            horizontal = 16.dp,
            vertical = 2.dp
        )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(
                8.dp
            )
        ) {
            Icon(
                imageVector = Icons.Default.AlarmAdd,
                contentDescription = null,
                tint = DoseCertaBlue,
                modifier = Modifier.size(18.dp)
            )
            Text(
                text = buttonLabel,
                color = SecondaryText
            )
        }
    }
}