package by.coolightman.randomizer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import by.coolightman.randomizer.presenter.ui.screens.MainScreen
import by.coolightman.randomizer.presenter.ui.screens.MainViewModel
import by.coolightman.randomizer.presenter.ui.theme.RandomizerTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val viewModel = hiltViewModel<MainViewModel>()
            val uiState by viewModel.uiState.collectAsState()

            RandomizerTheme(uiState.isDarkMode) {
                val systemUiController = rememberSystemUiController()
                val useDarkIcons = !uiState.isDarkMode
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
                    onClickPlusOne = { viewModel.onClickPlusOne() },
                    onSwitchTheme = { viewModel.switchTheme() },
                    onUpdateSpecialRange = { min, max ->
                        viewModel.setSpecialRange(min, max)
                    }
                )
            }
        }
    }
}