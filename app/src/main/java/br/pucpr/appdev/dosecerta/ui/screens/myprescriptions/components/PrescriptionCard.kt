package br.pucpr.appdev.dosecerta.ui.screens.myprescriptions.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.pucpr.appdev.dosecerta.R
import br.pucpr.appdev.dosecerta.models.Prescription
import br.pucpr.appdev.dosecerta.ui.theme.LightBorder
import br.pucpr.appdev.dosecerta.ui.theme.SecondaryText

@Composable
fun PrescriptionCard(
    prescription: Prescription,
    onOpenDeletePrescriptionModal: () -> Unit,
    onEditPrescription: (prescriptionId: String) -> Unit,
    onPrescriptionDetails: (prescriptionId: String) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
            .border(
                BorderStroke(2.dp, LightBorder),
                shape = RoundedCornerShape(8.dp)
            )
            .clickable { onPrescriptionDetails(prescription.id) }
    ) {
        Column (
            modifier = Modifier
                .padding(top = 32.dp, bottom = 16.dp, start = 16.dp, end = 16.dp)
        ) {
            Text(
                text = prescription.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 4.dp),
                textAlign = TextAlign.Left,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1

            )
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp)
            ) {
                Text(
                    text = "${stringResource(R.string.medicines)}: ",
                    fontWeight = FontWeight.Bold
                )
                Text(text = "${prescription.medicines.size}", color = SecondaryText)
            }
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp)
            ) {
                Text(
                    text = "${stringResource(R.string.updated_at)}: ",
                    fontWeight = FontWeight.Bold
                )
                Text(text = prescription.createdAt, color = SecondaryText)
            }
            Box(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    EditPrescriptionButton { onEditPrescription(prescription.id) }
                    DeletePrescriptionButton(
                        onOpenDeletePrescriptionModal = {
                            onOpenDeletePrescriptionModal()
                        },
                    )
                }
            }
        }
    }
}