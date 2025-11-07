package br.pucpr.appdev.dosecerta.ui.screens.myprescriptions.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import br.pucpr.appdev.dosecerta.R
import br.pucpr.appdev.dosecerta.ui.theme.BackgroundLight
import br.pucpr.appdev.dosecerta.ui.theme.DestructiveActionRed
import br.pucpr.appdev.dosecerta.ui.theme.LightBorder
import br.pucpr.appdev.dosecerta.ui.theme.PrimaryText
import br.pucpr.appdev.dosecerta.ui.theme.SecondaryText

@Composable
fun DeletePrescriptionModal(
    onCloseModal: () -> Unit,
    onDeletePrescription: () -> Unit
) {
    Dialog(onDismissRequest = onCloseModal) {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier
                .fillMaxWidth()
                .background(color = BackgroundLight)
                .padding(horizontal = 16.dp, vertical = 32.dp)
        ) {
            Box(
                modifier = Modifier.padding(bottom = 8.dp)
            ) {
                Text(
                    text = stringResource(R.string.my_prescriptions_delete_presc),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }
            Text(
                text = stringResource(R.string.my_prescriptions_confirm_delete_presc),
                fontSize = 16.sp,
                color = SecondaryText
            )
            Text(
                text = stringResource(R.string.my_prescriptions_action_cant_be_undone),
                fontSize = 16.sp,
                color = SecondaryText
            )
            Box(
                modifier = Modifier.fillMaxWidth().padding(top = 12.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = onCloseModal,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent,
                            contentColor = PrimaryText
                        ),
                        modifier = Modifier.height(40.dp),
                        border = BorderStroke(1.dp, color = LightBorder),
                        shape = ShapeDefaults.Small,
                    ) {
                        Text(text = stringResource(R.string.cancel))
                    }
                    Button(
                        onClick = onDeletePrescription,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = DestructiveActionRed,
                            contentColor = BackgroundLight,
                        ),
                        modifier = Modifier.height(40.dp),
                        shape = ShapeDefaults.Small,
                    ) {
                        Text(text = stringResource(R.string.my_prescriptions_delete))
                    }
                }
            }
        }
    }
}