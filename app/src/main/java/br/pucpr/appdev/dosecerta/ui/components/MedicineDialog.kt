package br.pucpr.appdev.dosecerta.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import br.pucpr.appdev.dosecerta.R
import br.pucpr.appdev.dosecerta.models.Medicine
import br.pucpr.appdev.dosecerta.ui.theme.BackgroundLight
import br.pucpr.appdev.dosecerta.ui.theme.DisabledBlue
import br.pucpr.appdev.dosecerta.ui.theme.DoseCertaBlue

@Composable
fun MedicineDialog(
    medicineData: Medicine,
    onChangeName: (name: String) -> Unit,
    onChangeDosage: (dosage: String) -> Unit,
    onChangeFrequency: (frequency: String) -> Unit,
    onChangeObservations: (obs: String) -> Unit,
    onAddMedicine: () -> Unit,
    onDismissDialog: () -> Unit,
) {
    Dialog(onDismissRequest = { onDismissDialog() }) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = BackgroundLight)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 24.dp, bottom = 32.dp)
            ) {
                Box(
                    modifier = Modifier.padding(vertical = 16.dp)
                ) {
                    Text(
                        text = stringResource(R.string.new_prescription_add_medicine),
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }
                OutlinedTextField(
                    value = medicineData.name,
                    onValueChange = { onChangeName(it) },
                    label = {
                        Text(text = stringResource(R.string.new_prescription_med_name))
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = DoseCertaBlue,
                        focusedLabelColor = DoseCertaBlue,
                    ),
                    singleLine = true
                )
                OutlinedTextField(
                    value = medicineData.dosage,
                    onValueChange = { onChangeDosage(it) },
                    label = { Text(text = stringResource(R.string.new_prescription_dosage)) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = DoseCertaBlue,
                        focusedLabelColor = DoseCertaBlue,
                    ),
                    singleLine = true
                )
                OutlinedTextField(
                    value = medicineData.frequency,
                    onValueChange = { onChangeFrequency(it) },
                    label = { Text(text = stringResource(R.string.new_prescription_frequency)) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = DoseCertaBlue,
                        focusedLabelColor = DoseCertaBlue,
                    ),
                    singleLine = true
                )
                OutlinedTextField(
                    value = medicineData.observations,
                    onValueChange = { onChangeObservations( it) },
                    label = { Text(text = stringResource(R.string.new_prescription_observations)) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = DoseCertaBlue,
                        focusedLabelColor = DoseCertaBlue,
                    ),
                    singleLine = true
                )
                Box(
                    modifier = Modifier.padding(top = 12.dp)
                ) {
                    Button(
                        onClick = { onAddMedicine() },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = DoseCertaBlue,
                            contentColor = BackgroundLight,
                            disabledContainerColor = DisabledBlue,
                            disabledContentColor = BackgroundLight
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp),
                        shape = ShapeDefaults.Small,
                        enabled = medicineData.name.isNotEmpty()
                                && medicineData.dosage.isNotEmpty()
                                && medicineData.frequency.isNotEmpty()
                    ) {
                        Text(text = stringResource(R.string.save))
                    }
                }
            }
        }
    }
}