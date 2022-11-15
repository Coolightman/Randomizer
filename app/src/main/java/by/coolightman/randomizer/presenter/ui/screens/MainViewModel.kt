package by.coolightman.randomizer.presenter.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.coolightman.randomizer.domain.model.CoinFace
import by.coolightman.randomizer.domain.model.DiceFace
import by.coolightman.randomizer.domain.model.RandomMode
import by.coolightman.randomizer.domain.model.ResultItem
import by.coolightman.randomizer.domain.repository.PreferencesRepository
import by.coolightman.randomizer.util.EMPTY_NUMBER
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
        when (uiState.value.selectedMode) {
            RandomMode.COIN -> tossCoin()
            RandomMode.DICE -> rollTheDice()
            RandomMode.SPECIAL -> generateBySpecialRange()
            else -> generateByPredefinedMode(uiState.value.selectedMode)
        }
    }

    private fun generateByPredefinedMode(selectedMode: RandomMode) {
        val result = when (selectedMode) {
            RandomMode.TO_9 -> (0..9).random()
            RandomMode.TO_10 -> (1..10).random()
            RandomMode.TO_99 -> (0..99).random()
            RandomMode.TO_100 -> (1..100).random()
            RandomMode.TO_999 -> (0..999).random()
            RandomMode.TO_1000 -> (1..1000).random()
            else -> -1
        }
        updateHistory(selectedMode, result)
        updateResult(result.toString())
    }

    private fun rollTheDice() {
        val result = (0..5).random()
        updateHistory(RandomMode.DICE, result)
        _uiState.update { currentState ->
            currentState.copy(
                rolledDiceFace = DiceFace.values()[result]
            )
        }
    }

    private fun generateBySpecialRange() {
        val min = uiState.value.specialRangeMin
        val max = uiState.value.specialRangeMax
        if (min < max) {
            val result = (min..max).random()
            updateHistory(RandomMode.SPECIAL, result)
            updateResult(result.toString())
        } else {
            updateResult(EMPTY_NUMBER)
        }
    }

    private fun updateResult(result: String) {
        _uiState.update { currentState ->
            currentState.copy(result = result)
        }
    }

    private fun updateHistory(type: RandomMode, value: Int) {
        _uiState.update { currentState ->
            val updatedHistory = currentState.history.toMutableList().apply {
                add(ResultItem(type = type, value = value))
            }.sortedByDescending { it.createdAt }
            currentState.copy(history = updatedHistory)
        }
    }

    private fun tossCoin() {
        val result = (0..1).random()
        updateHistory(RandomMode.COIN, result)
        _uiState.update { currentState ->
            currentState.copy(
                tossedCoinFace = CoinFace.values()[result]
            )
        }
    }

    fun onClickMode(mode: RandomMode) {
        viewModelScope.launch {
            preferencesRepository.putInt(RANDOM_MODE_KEY, mode.ordinal)
            _uiState.update { currentState ->
                currentState.copy(
                    selectedMode = mode,
                    result = EMPTY_NUMBER
                )
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