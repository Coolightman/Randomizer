package by.coolightman.randomizer.presenter.ui.screens

import by.coolightman.randomizer.domain.model.RandomMode

data class MainUiState(
    val result: String = "--",
    val selectedMode: RandomMode = RandomMode.TO_9,
    val selectedPlusOne: Boolean = false,
    val isDarkMode: Boolean = true
)
