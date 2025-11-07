package br.pucpr.appdev.dosecerta.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import br.pucpr.appdev.dosecerta.R
import br.pucpr.appdev.dosecerta.ui.theme.DoseCertaBlue

@Composable
fun NoMedicinesAddedInformational(onAddMedicineClick: () -> Unit) {
    Column {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.Info,
                contentDescription = null,
                tint = DoseCertaBlue
            )
            Text(text = stringResource(R.string.new_prescription_no_medicines_added_yet))
        }
        AddMedicineButton(onAddMedicineClick)
    }
}