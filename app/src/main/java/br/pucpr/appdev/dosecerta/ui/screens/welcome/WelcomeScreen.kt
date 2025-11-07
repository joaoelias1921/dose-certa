package br.pucpr.appdev.dosecerta.ui.screens.welcome

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.pucpr.appdev.dosecerta.R
import br.pucpr.appdev.dosecerta.repositories.AppPreferencesRepository
import br.pucpr.appdev.dosecerta.ui.theme.BackgroundLight
import br.pucpr.appdev.dosecerta.ui.theme.DisabledBlue
import br.pucpr.appdev.dosecerta.ui.theme.DoseCertaBlue
import br.pucpr.appdev.dosecerta.ui.theme.SecondaryText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WelcomeScreen(
    appPreferencesRepository: AppPreferencesRepository,
    onBeginJourney: () -> Unit
) {
    val viewModel: WelcomeViewModel = viewModel(
        factory = WelcomeViewModel.factory(appPreferencesRepository)
    )

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "")
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(all = 16.dp)
                .fillMaxSize()
        ) {
            Box (
                modifier = Modifier.fillMaxWidth().padding(top = 40.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.logo_dose_certa),
                    contentDescription = stringResource(R.string.logo_content_description),
                    modifier = Modifier.size(150.dp)
                )
            }
            Box (
                modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.app_name),
                    textAlign = TextAlign.Center,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Italic,
                    color = DoseCertaBlue
                )
            }
            Box (
                modifier = Modifier.fillMaxWidth().padding(top = 150.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.welcome_to_app),
                    textAlign = TextAlign.Center,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Box (
                modifier = Modifier.fillMaxWidth().padding(top = 24.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.welcome_descriptive),
                    textAlign = TextAlign.Center,
                    color = SecondaryText
                )
            }
            Box (
                modifier = Modifier.fillMaxSize().padding(bottom = 24.dp),
                contentAlignment = Alignment.BottomEnd
            ) {
                Button(
                    onClick = {
                        viewModel.userBeginsJourney()
                        onBeginJourney()
                    },
                    shape = ShapeDefaults.Small,
                    modifier = Modifier.fillMaxWidth().height(48.dp),
                    colors = ButtonColors(
                        DoseCertaBlue,
                        BackgroundLight,
                        DisabledBlue,
                        SecondaryText
                    )
                ) {
                    Text(text = stringResource(R.string.welcome_button_text))
                }
            }
        }
    }
}