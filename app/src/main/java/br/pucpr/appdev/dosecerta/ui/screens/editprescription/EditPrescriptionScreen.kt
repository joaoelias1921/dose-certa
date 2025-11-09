package br.pucpr.appdev.dosecerta.ui.screens.editprescription

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import br.pucpr.appdev.dosecerta.R
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.pucpr.appdev.dosecerta.base.util.StringProvider
import br.pucpr.appdev.dosecerta.repositories.PrescriptionRepository
import br.pucpr.appdev.dosecerta.ui.components.AddMedicineButton
import br.pucpr.appdev.dosecerta.ui.components.MedicineCard
import br.pucpr.appdev.dosecerta.ui.components.MedicineDialog
import br.pucpr.appdev.dosecerta.ui.components.NoMedicinesAddedInformational
import br.pucpr.appdev.dosecerta.ui.components.ScreenLoadingIndicator
import br.pucpr.appdev.dosecerta.ui.components.TimePickerDialog
import br.pucpr.appdev.dosecerta.ui.theme.BackgroundLight
import br.pucpr.appdev.dosecerta.ui.theme.DisabledBlue
import br.pucpr.appdev.dosecerta.ui.theme.DoseCertaBlue
import br.pucpr.appdev.dosecerta.ui.theme.LightBorder

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditPrescriptionScreen(
    repository: PrescriptionRepository,
    stringProvider: StringProvider,
    onSavePrescription: () -> Unit,
    onNavigateBack: () -> Unit,
) {
    val viewModel: EditPrescriptionViewModel = viewModel(
        factory = EditPrescriptionViewModel.factory(repository, stringProvider)
    )
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current.applicationContext
    val alreadyAddedMedicineErrorText = stringResource(
        R.string.new_prescription_already_added_medicine
    )

    LaunchedEffect(uiState.isSaved, uiState.duplicatedMedicineError) {
        if (uiState.duplicatedMedicineError) {
            Toast.makeText(
                context,
                alreadyAddedMedicineErrorText,
                Toast.LENGTH_LONG
            ).show()
            viewModel.resetDuplicatedMedicineError()
            return@LaunchedEffect
        }
        if (uiState.isSaved) onSavePrescription()
    }

    Scaffold(
        topBar = {
            Column {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = stringResource(R.string.edit_prescription_title),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            onNavigateBack()
                        }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Default.ArrowBack,
                                contentDescription = stringResource(R.string.go_back)
                            )
                        }
                    }
                )
                HorizontalDivider(
                    color = LightBorder,
                    thickness = 2.dp
                )
            }
        }
    ) { paddingValues ->
        when {
            uiState.isLoading -> { ScreenLoadingIndicator() }
            !uiState.isLoading -> {
                if (uiState.isCreatingMedicine) {
                    MedicineDialog(
                        medicineData = uiState.newMedicine,
                        onChangeName = viewModel::setNewMedicineName,
                        onChangeDosage = viewModel::setNewMedicineDosage,
                        onChangeObservations = viewModel::setNewMedicineObservations,
                        onContinue = viewModel::chooseTimeToTake,
                        onDismissDialog = viewModel::toggleNewMedicineModal
                    )
                }

                if (uiState.isPickingTimeToTake) {
                    TimePickerDialog(
                        selectedHourToTake = uiState.selectedHourToTake,
                        selectedMinuteToTake = uiState.selectedMinuteToTake,
                        onDismiss = viewModel::toggleTimeToTakePickerModal,
                        onConfirm = viewModel::addNewMedicine
                    )
                }

                Box (
                    modifier = Modifier
                        .padding(paddingValues)
                        .padding(top = 24.dp)
                        .fillMaxSize()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp)
                    ) {
                        OutlinedTextField(
                            value = uiState.prescriptionName,
                            onValueChange = { viewModel.setPrescriptionName(it) },
                            label = {
                                Text(
                                    text = stringResource(R.string.new_prescription_presc_name)
                                )
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = DoseCertaBlue,
                                focusedLabelColor = DoseCertaBlue,
                            ),
                            placeholder = {
                                Text(
                                    text = stringResource(
                                        R.string.new_prescription_presc_name_placeholder
                                    )
                                )
                            },
                            singleLine = true
                        )
                        Box(
                            modifier = Modifier.padding(vertical = 32.dp)
                        ) {
                            Text(
                                text = stringResource(R.string.medicines),
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            )
                        }
                        if (uiState.prescriptionMedicines.isEmpty()) {
                            NoMedicinesAddedInformational(
                                onAddMedicineClick = viewModel::toggleNewMedicineModal
                            )
                        } else {
                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .weight(1f),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Top
                            ) {
                                uiState.prescriptionMedicines.forEachIndexed { index, medicine ->
                                    item {
                                        MedicineCard(
                                            medicineData = medicine,
                                            onRemoveMedicine = {
                                                viewModel.removeMedicineAt(index)
                                            }
                                        )
                                    }
                                }
                            }
                        }
                        if (uiState.prescriptionMedicines.isNotEmpty()) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp)
                                    .background(color = BackgroundLight),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Button(
                                    onClick = { viewModel.saveEditedPrescription() },
                                    shape = ShapeDefaults.Small,
                                    modifier = Modifier.fillMaxWidth(),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = DoseCertaBlue,
                                        disabledContainerColor = DisabledBlue,
                                        disabledContentColor = BackgroundLight
                                    ),
                                    enabled = uiState.prescriptionMedicines.isNotEmpty() &&
                                            uiState.prescriptionName.isNotEmpty()
                                ) {
                                    Text(text = stringResource(R.string.save))
                                }
                                AddMedicineButton(
                                    onAddMedicineClick = viewModel::toggleNewMedicineModal
                                )
                                Button(
                                    onClick = { onNavigateBack() },
                                    shape = ShapeDefaults.Small,
                                    modifier = Modifier.fillMaxWidth(),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color.Transparent,
                                        contentColor = DoseCertaBlue
                                    )
                                ) {
                                    Text(text = stringResource(R.string.cancel))
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}