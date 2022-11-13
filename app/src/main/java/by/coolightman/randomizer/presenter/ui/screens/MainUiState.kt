package by.coolightman.randomizer.presenter.ui.screens

import by.coolightman.randomizer.domain.model.RandomMode

data class MainUiState(
    val result: String = "--",
    val selectedMode: RandomMode = RandomMode.TO_10
)