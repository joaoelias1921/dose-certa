package br.pucpr.appdev.dosecerta.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Notes
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.BubbleChart
import androidx.compose.material.icons.filled.Cached
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Medication
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import br.pucpr.appdev.dosecerta.R
import br.pucpr.appdev.dosecerta.models.Medicine
import br.pucpr.appdev.dosecerta.ui.theme.DestructiveActionRed
import br.pucpr.appdev.dosecerta.ui.theme.DoseCertaBlue
import br.pucpr.appdev.dosecerta.ui.theme.LightBorder
import br.pucpr.appdev.dosecerta.ui.theme.SecondaryText

@Composable
fun MedicineCard(
    medicineData: Medicine,
    onRemoveMedicine: (() -> Unit)? = null,
    extraContent: @Composable (() -> Unit)? = null
) {
    val isCreatingOrEditing = onRemoveMedicine != null

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    BorderStroke(2.dp, LightBorder),
                    shape = RoundedCornerShape(8.dp)
                )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier.weight(0.8f)
                ) {
                    Row (
                        modifier = Modifier.padding(top = 4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Medication,
                            contentDescription = null,
                            tint = DoseCertaBlue,
                            modifier = Modifier.padding(end = 8.dp)
                        )
                        InformationalText(
                            title = "${stringResource(R.string.medicine)}: ",
                            description = medicineData.name,
                            isCreatingOrEditing
                        )
                    }
                    Row (
                        modifier = Modifier.padding(top = 4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.BubbleChart,
                            contentDescription = null,
                            tint = DoseCertaBlue,
                            modifier = Modifier.padding(end = 8.dp)
                        )
                        InformationalText(
                            title = "${stringResource(
                                R.string.new_prescription_dosage
                            )}: ",
                            description = medicineData.dosage,
                            isCreatingOrEditing
                        )
                    }
                    if (medicineData.frequency.isNotBlank()) {
                        Row (
                            modifier = Modifier.padding(top = 4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Cached,
                                contentDescription = null,
                                tint = DoseCertaBlue,
                                modifier = Modifier.padding(end = 8.dp)
                            )
                            InformationalText(
                                title = "${stringResource(
                                    R.string.new_prescription_frequency
                                )}: ",
                                description = medicineData.frequency,
                                isCreatingOrEditing
                            )
                        }
                    }
                    Row (
                        modifier = Modifier.padding(top = 4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.AccessTime,
                            contentDescription = null,
                            tint = DoseCertaBlue,
                            modifier = Modifier.padding(end = 8.dp)
                        )
                        InformationalText(
                            title = "${stringResource(R.string.hour)}: ",
                            description = medicineData.timeToTake,
                            isCreatingOrEditing
                        )
                    }
                    if (medicineData.observations.isNotEmpty()) {
                        Row (
                            modifier = Modifier.padding(top = 4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Default.Notes,
                                contentDescription = null,
                                tint = DoseCertaBlue,
                                modifier = Modifier.padding(end = 8.dp)
                            )
                            InformationalText(
                                title = "${stringResource(
                                    R.string.new_prescription_observations
                                )}: ",
                                description = medicineData.observations,
                                isCreatingOrEditing
                            )
                        }
                    }
                }
                if (isCreatingOrEditing) {
                    IconButton(
                        onClick = { onRemoveMedicine() },
                        modifier = Modifier.weight(0.2f)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            tint = DestructiveActionRed,
                            contentDescription = null
                        )
                    }
                }
            }
        }
        if (extraContent != null) extraContent()
    }
}

@Composable
fun InformationalText(
    title: String,
    description: String,
    isCreatingOrEditing: Boolean
) {
    val overflow = if (isCreatingOrEditing) TextOverflow.Ellipsis else TextOverflow.Visible
    val maxLines = if (isCreatingOrEditing) 1 else Int.MAX_VALUE

    Text(
        text = buildAnnotatedString {
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                append(title)
            }
            withStyle(style = SpanStyle(color = SecondaryText)) {
                append(description)
            }
        },
        overflow = overflow,
        maxLines = maxLines
    )
}
