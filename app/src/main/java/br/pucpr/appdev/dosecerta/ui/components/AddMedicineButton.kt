package br.pucpr.appdev.dosecerta.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import br.pucpr.appdev.dosecerta.R
import br.pucpr.appdev.dosecerta.ui.theme.LightBorder
import br.pucpr.appdev.dosecerta.ui.theme.PrimaryText

@Composable
fun AddMedicineButton(onAddMedicineClick: () -> Unit) {
    Button(
        onClick = { onAddMedicineClick() },
        modifier = Modifier.fillMaxWidth(),
        shape = ShapeDefaults.Small,
        colors = ButtonDefaults.buttonColors(
            containerColor = LightBorder,
            contentColor = PrimaryText
        ),
    ) {
        Text(text = stringResource(R.string.new_prescription_add_medicine))
    }
}
