package by.coolightman.randomizer.presenter.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.coolightman.randomizer.domain.model.RandomMode
import by.coolightman.randomizer.domain.repository.PreferencesRepository
import by.coolightman.randomizer.util.MAX_RANGE_KEY
import by.coolightman.randomizer.util.MIN_RANGE_KEY
import by.coolightman.randomizer.util.PLUS_ONE_KEY
import by.coolightman.randomizer.util.RANDOM_MODE_KEY
import by.coolightman.randomizer.util.THEME_MODE_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val preferencesRepository: PreferencesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    init {
        getPreferencesData()
    }

    private fun getPreferencesData() {
        getThemeMode()
        getRandomMode()
        getPlusOneState()
        getSpecialRange()
    }

    private fun getPlusOneState() {
        viewModelScope.launch {
            _uiState.update { currentState ->
                val value = preferencesRepository.getBoolean(PLUS_ONE_KEY, false).first()
                currentState.copy(selectedPlusOne = value)
            }
        }
    }

    private fun getRandomMode() {
        viewModelScope.launch {
            val modeIndex = preferencesRepository.getInt(RANDOM_MODE_KEY, 0).first()
            _uiState.update { currentState ->
                currentState.copy(selectedMode = RandomMode.values()[modeIndex])
            }
        }
    }

    private fun getSpecialRange() {
        viewModelScope.launch {
            _uiState.update { currentState ->
                currentState.copy(
                    specialRangeMin = preferencesRepository.getInt(MIN_RANGE_KEY, 0).first(),
                    specialRangeMax = preferencesRepository.getInt(MAX_RANGE_KEY, 14).first()
                )
            }
        }
    }

    private fun getThemeMode() {
        viewModelScope.launch {
            preferencesRepository.getBoolean(THEME_MODE_KEY, true).collectLatest {
                _uiState.update { currentState ->
                    currentState.copy(isDarkMode = it)
                }
            }
        }
    }

    fun onClickGenerate() {
        _uiState.update { currentState ->
            currentState.copy(result = getRandomValue())
        }
    }

    private fun getRandomValue(): String {
        return when (uiState.value.selectedMode) {
            RandomMode.TO_9 -> (0..9).random().toString()
            RandomMode.TO_10 -> (1..10).random().toString()
            RandomMode.TO_99 -> (0..99).random().toString()
            RandomMode.TO_100 -> (1..100).random().toString()
            RandomMode.TO_999 -> (0..999).random().toString()
            RandomMode.TO_1000 -> (1..1000).random().toString()
            RandomMode.COIN -> tossCoin()
            RandomMode.DICE -> rollTheDice()
            RandomMode.SPECIAL -> generateBySpecialRange()
        }
    }

    private fun rollTheDice(): String {
        return "Dice"
    }

    private fun generateBySpecialRange(): String {
        val min = uiState.value.specialRangeMin
        val max = uiState.value.specialRangeMax
        return if (min < max) {
            (min..max).random().toString()
        } else {
            "--"
        }
    }

    private fun tossCoin(): String {
        return when ((0..1).random()) {
            0 -> "HEADS"
            1 -> "TAILS"
            else -> "Error"
        }
    }

    fun onClickMode(mode: RandomMode) {
        viewModelScope.launch {
            preferencesRepository.putInt(RANDOM_MODE_KEY, mode.ordinal)
            _uiState.update { currentState ->
                currentState.copy(selectedMode = mode)
            }
        }
    }

    fun onClickPlusOne() {
        viewModelScope.launch {
            _uiState.update { currentState ->
                preferencesRepository.putBoolean(PLUS_ONE_KEY, !currentState.selectedPlusOne)
                currentState.copy(selectedPlusOne = !currentState.selectedPlusOne)
            }
        }
    }

    fun switchTheme() {
        viewModelScope.launch {
            delay(100)
            preferencesRepository.putBoolean(THEME_MODE_KEY, !uiState.value.isDarkMode)
        }
    }

    fun setSpecialRange(min: Int, max: Int) {
        _uiState.update { currentState ->
            currentState.copy(
                specialRangeMin = min,
                specialRangeMax = max
            )
        }
        viewModelScope.launch {
            preferencesRepository.putInt(MIN_RANGE_KEY, min)
            preferencesRepository.putInt(MAX_RANGE_KEY, max)
        }
    }
}