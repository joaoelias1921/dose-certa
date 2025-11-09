package br.pucpr.appdev.dosecerta.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import br.pucpr.appdev.dosecerta.R
import br.pucpr.appdev.dosecerta.models.Medicine
import br.pucpr.appdev.dosecerta.ui.theme.BackgroundLight
import br.pucpr.appdev.dosecerta.ui.theme.DisabledBlue
import br.pucpr.appdev.dosecerta.ui.theme.DoseCertaBlue
import br.pucpr.appdev.dosecerta.ui.theme.SecondaryText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MedicineDialog(
    medicineData: Medicine,
    onChangeName: (name: String) -> Unit,
    onChangeDosage: (dosage: String) -> Unit,
    onChangeObservations: (obs: String) -> Unit,
    onContinue: () -> Unit,
    onDismissDialog: () -> Unit,
) {
    Dialog(
        onDismissRequest = { onDismissDialog() },
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .height(IntrinsicSize.Min)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 24.dp, bottom = 32.dp)
            ) {
                Column(
                    modifier = Modifier.padding(vertical = 16.dp)
                ) {
                    Text(
                        text = stringResource(R.string.new_prescription_add_medicine),
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Text(
                            text = stringResource(R.string.new_prescription_wip_app_disclaimer),
                            fontSize = 14.sp,
                            color = SecondaryText
                        )
                    }
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
                    value = medicineData.observations,
                    onValueChange = { onChangeObservations(it) },
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
                        onClick = onContinue,
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
                        enabled = medicineData.name.isNotEmpty() &&
                                medicineData.dosage.isNotEmpty()
                    ) {
                        Text(text = stringResource(R.string.continue_action))
                    }
                }
            }
        }
    }
}