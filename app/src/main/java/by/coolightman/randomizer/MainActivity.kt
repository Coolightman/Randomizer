package by.coolightman.randomizer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import by.coolightman.randomizer.presenter.ui.screens.MainScreen
import by.coolightman.randomizer.presenter.ui.screens.MainViewModel
import by.coolightman.randomizer.presenter.ui.theme.RandomizerTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val viewModel: MainViewModel = viewModel()
            val uiState by viewModel.uiState.collectAsState()

            RandomizerTheme {
                MainScreen(
                    result = uiState.result,
                    onClickGenerate = {viewModel.onClickGenerate()}
                )
            }
        }
    }
}