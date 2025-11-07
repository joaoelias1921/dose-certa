package br.pucpr.appdev.dosecerta.ui.screens.myprescriptions.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.EditNote
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import br.pucpr.appdev.dosecerta.R
import br.pucpr.appdev.dosecerta.ui.theme.LightBorder
import br.pucpr.appdev.dosecerta.ui.theme.PrimaryText

@Composable
fun EditPrescriptionButton(onEdit: () -> Unit) {
    Button(
        onClick = { onEdit() },
        shape = ShapeDefaults.Small,
        colors = ButtonColors(
            containerColor = Color.Transparent,
            contentColor = PrimaryText,
            disabledContainerColor = Color.Transparent,
            disabledContentColor = PrimaryText,
        ),
        border = BorderStroke(1.dp, color = LightBorder),
        modifier = Modifier.height(40.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Outlined.EditNote,
                contentDescription = stringResource(R.string.my_prescriptions_edit_a11y_label)
            )
            Text(
                text = stringResource(R.string.my_prescriptions_edit),
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}