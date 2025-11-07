package br.pucpr.appdev.dosecerta.ui.screens.myprescriptions

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import br.pucpr.appdev.dosecerta.R
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.pucpr.appdev.dosecerta.base.util.StringProvider
import br.pucpr.appdev.dosecerta.repositories.PrescriptionRepository
import br.pucpr.appdev.dosecerta.repositories.UserRepository
import br.pucpr.appdev.dosecerta.ui.components.ScreenLoadingIndicator
import br.pucpr.appdev.dosecerta.ui.screens.myprescriptions.components.ConfirmSignOutModal
import br.pucpr.appdev.dosecerta.ui.screens.myprescriptions.components.DeletePrescriptionModal
import br.pucpr.appdev.dosecerta.ui.screens.myprescriptions.components.PrescriptionCard
import br.pucpr.appdev.dosecerta.ui.theme.BackgroundLight
import br.pucpr.appdev.dosecerta.ui.theme.DoseCertaBlue
import br.pucpr.appdev.dosecerta.ui.theme.LightBorder
import br.pucpr.appdev.dosecerta.ui.theme.SecondaryText
import kotlin.Unit

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyPrescriptionsScreen(
    prescriptionsRepository: PrescriptionRepository,
    userRepository: UserRepository,
    stringProvider: StringProvider,
    onNewPrescription: () -> Unit,
    onEditPrescription: (prescriptionId: String) -> Unit,
    onPrescriptionDetails: (prescriptionId: String) -> Unit,
    onSignOut: () -> Unit
) {
    val viewModel: MyPrescriptionsViewModel = viewModel(
        factory = MyPrescriptionsViewModel.factory(
            prescriptionsRepository,
            userRepository,
            stringProvider
        )
    )
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.isSignedOut) {
        if (uiState.isSignedOut) onSignOut()
    }

    Scaffold(
        topBar = {
            Column {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = stringResource(R.string.my_prescriptions_title),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    actions = {
                        IconButton(onClick = viewModel::toggleSignOutConfirmModal) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Default.Logout,
                                contentDescription = null
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
                if (uiState.prescriptionIdToDelete != null) {
                    DeletePrescriptionModal(
                        onDeletePrescription = viewModel::deletePrescription,
                        onCloseModal = viewModel::closeDeletePrescriptionModal
                    )
                }

                if (uiState.isSigningOut) {
                    ConfirmSignOutModal(
                        onSignOut = viewModel::performLogout,
                        onCloseModal = viewModel::toggleSignOutConfirmModal
                    )
                }

                Box (
                    modifier = Modifier
                        .padding(paddingValues)
                        .padding(top = 24.dp)
                        .fillMaxSize()
                ) {
                    if (uiState.prescriptions.isEmpty()) {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(12.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Info,
                                contentDescription = null,
                                tint = DoseCertaBlue,
                                modifier = Modifier.size(64.dp)
                            )
                            Text(
                                text = stringResource(R.string.my_prescriptions_no_added_prescs_yet),
                                fontSize = 20.sp
                            )
                            Text(
                                text = stringResource(R.string.my_prescriptions_use_floating_button_to_start),
                                color = SecondaryText,
                                fontSize = 16.sp
                            )
                        }
                        return@Box
                    }
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Top
                    ) {
                        items(
                            items = uiState.prescriptions,
                            key = { prescription -> prescription.id }
                        ) { prescription ->
                            PrescriptionCard(
                                prescription = prescription,
                                onOpenDeletePrescriptionModal = {
                                    viewModel.openDeletePrescriptionModal(
                                        prescription.id
                                    )
                                },
                                onEditPrescription = onEditPrescription,
                                onPrescriptionDetails = onPrescriptionDetails
                            )
                        }
                    }
                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            contentAlignment = Alignment.BottomEnd
        ) {
            FloatingActionButton(
                onClick = { onNewPrescription() },
                shape = ShapeDefaults.ExtraLarge,
                containerColor = DoseCertaBlue,
                contentColor = BackgroundLight
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.my_prescriptions_add_a11y_label)
                )
            }
        }
    }
}