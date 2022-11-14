package by.coolightman.randomizer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import by.coolightman.randomizer.presenter.ui.screens.MainScreen
import by.coolightman.randomizer.presenter.ui.screens.MainViewModel
import by.coolightman.randomizer.presenter.ui.theme.RandomizerTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val viewModel: MainViewModel = viewModel()
            val uiState by viewModel.uiState.collectAsState()

            RandomizerTheme {
                val systemUiController = rememberSystemUiController()
                val useDarkIcons = !isSystemInDarkTheme()
                val barsColor = MaterialTheme.colorScheme.background

                SideEffect {
                    systemUiController.setSystemBarsColor(
                        color = barsColor,
                        darkIcons = useDarkIcons
                    )
                }

                MainScreen(
                    uiState = uiState,
                    onClickGenerate = { viewModel.onClickGenerate() },
                    onClickMode = { viewModel.onClickMode(it) },
                    onClickPlusOne = {viewModel.onClickPlusOne()},
                    onSwitchTheme = {viewModel.switchTheme()}
                )
            }
        }
    }
}