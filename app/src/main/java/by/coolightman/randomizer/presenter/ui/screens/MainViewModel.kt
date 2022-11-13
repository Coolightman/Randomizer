package by.coolightman.randomizer.presenter.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.coolightman.randomizer.domain.model.RandomMode
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    private val selectedMode = MutableStateFlow(RandomMode.TO_9)

    init {
        collectSelectedMode()
        getRandomModeProperty()
    }

    private fun collectSelectedMode() {
        viewModelScope.launch {
            selectedMode.collectLatest { mode ->
                _uiState.update { currentState ->
                    currentState.copy(selectedMode = mode)
                }
            }
        }
    }

    private fun getRandomModeProperty() {
        selectedMode.update { RandomMode.TO_100 }
    }

    fun onClickGenerate() {
        _uiState.update { currentState ->
            currentState.copy(result = getRandomValue())
        }
    }

    private fun getRandomValue(): String {
        return when (selectedMode.value) {
            RandomMode.TO_9 -> (0..9).random().toString()
            RandomMode.TO_10 -> (1..10).random().toString()
            RandomMode.TO_99 -> (0..99).random().toString()
            RandomMode.TO_100 -> (1..100).random().toString()
            RandomMode.TO_999 -> (0..999).random().toString()
            RandomMode.TO_1000 -> (1..1000).random().toString()
            RandomMode.COIN -> tossCoin()
            RandomMode.SPECIAL -> generateBySpecialRange()
        }
    }

    private fun generateBySpecialRange(): String {
        return "Special"
    }

    private fun tossCoin(): String {
        return when ((0..1).random()) {
            0 -> "HEADS"
            1 -> "TAILS"
            else -> "Error"
        }
    }

    fun onClickMode(mode: RandomMode) {
        selectedMode.update { mode }
    }
}