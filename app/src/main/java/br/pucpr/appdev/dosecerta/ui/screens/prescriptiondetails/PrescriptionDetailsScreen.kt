package br.pucpr.appdev.dosecerta.ui.screens.prescriptiondetails

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import br.pucpr.appdev.dosecerta.R
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import br.pucpr.appdev.dosecerta.base.util.StringProvider
import br.pucpr.appdev.dosecerta.repositories.PrescriptionRepository
import br.pucpr.appdev.dosecerta.ui.components.MedicineCard
import br.pucpr.appdev.dosecerta.ui.components.ScreenLoadingIndicator
import br.pucpr.appdev.dosecerta.ui.theme.BackgroundLight
import br.pucpr.appdev.dosecerta.ui.theme.DestructiveActionRed
import br.pucpr.appdev.dosecerta.ui.theme.DisabledBlue
import br.pucpr.appdev.dosecerta.ui.theme.DoseCertaBlue
import br.pucpr.appdev.dosecerta.ui.theme.LightBorder
import br.pucpr.appdev.dosecerta.ui.theme.PrimaryText
import br.pucpr.appdev.dosecerta.ui.theme.SecondaryText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrescriptionDetailsScreen(
    repository: PrescriptionRepository,
    stringProvider: StringProvider,
    onEditPrescription: (prescriptionId: String) -> Unit,
    onDeletePrescription: () -> Unit,
    onNavigateBack: () -> Unit,
) {
    val viewModel: PrescriptionDetailsViewModel = viewModel(
        factory = PrescriptionDetailsViewModel.factory(repository, stringProvider)
    )
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.isDeleted) {
        if (uiState.isDeleted) onDeletePrescription()
    }

    if (uiState.isDeleted) return

    Scaffold(
        topBar = {
            Column {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = stringResource(R.string.presc_details_title),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { onNavigateBack() }) {
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
            uiState.prescription == null -> { ScreenLoadingIndicator() }
            else -> {
                if (uiState.showDeleteModal) {
                    Dialog(onDismissRequest = viewModel::closeDeletePrescriptionModal) {
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
                                        onClick = viewModel::closeDeletePrescriptionModal,
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
                                        onClick = {
                                            viewModel.deletePrescription()
                                            onDeletePrescription()
                                        },
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
                        Box(
                            modifier = Modifier.padding(top = 40.dp)
                        ) {
                            Text(
                                text = stringResource(R.string.prescription),
                                color = SecondaryText
                            )
                        }
                        Text(
                            text = uiState.prescription!!.name,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(top = 32.dp)
                                .weight(1f),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Top
                        ) {
                            items(
                                items = uiState.prescription?.medicines ?: emptyList()
                            ) { medicine ->
                                MedicineCard(medicineData = medicine)
                            }
                        }
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                                .background(color = BackgroundLight),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Button(
                                onClick = { onEditPrescription(viewModel.prescriptionId) },
                                shape = ShapeDefaults.Small,
                                modifier = Modifier.fillMaxWidth(),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = DoseCertaBlue,
                                    disabledContainerColor = DisabledBlue,
                                    disabledContentColor = BackgroundLight
                                ),
                            ) {
                                Text(text = stringResource(R.string.my_prescriptions_edit))
                            }
                            Button(
                                onClick = viewModel::openDeletePrescriptionModal,
                                shape = ShapeDefaults.Small,
                                modifier = Modifier.fillMaxWidth(),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.Transparent,
                                    contentColor = DestructiveActionRed
                                )
                            ) {
                                Text(text = stringResource(R.string.my_prescriptions_delete))
                            }
                        }
                    }
                }
            }
        }
    }
}