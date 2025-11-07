package br.pucpr.appdev.dosecerta.ui.screens.myprescriptions.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
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
import br.pucpr.appdev.dosecerta.ui.theme.BackgroundLight
import br.pucpr.appdev.dosecerta.ui.theme.DestructiveActionRed

@Composable
fun DeletePrescriptionButton(onOpenDeletePrescriptionModal: () -> Unit) {
    Button(
        onClick = { onOpenDeletePrescriptionModal() },
        shape = ShapeDefaults.Small,
        colors = ButtonColors(
            containerColor = DestructiveActionRed,
            contentColor = BackgroundLight,
            disabledContainerColor = DestructiveActionRed,
            disabledContentColor = BackgroundLight,
        ),
        modifier = Modifier.height(40.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Outlined.Delete,
                contentDescription = stringResource(R.string.my_prescriptions_delete_a11y_label)
            )
            Text(
                text = stringResource(R.string.my_prescriptions_delete),
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}