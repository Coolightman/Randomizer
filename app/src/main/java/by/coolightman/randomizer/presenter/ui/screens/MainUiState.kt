package by.coolightman.randomizer.presenter.ui.screens

import by.coolightman.randomizer.domain.model.CoinFace
import by.coolightman.randomizer.domain.model.DiceFace
import by.coolightman.randomizer.domain.model.RandomMode
import by.coolightman.randomizer.util.EMPTY_NUMBER

data class MainUiState(
    val result: String = EMPTY_NUMBER,
    val selectedMode: RandomMode = RandomMode.TO_9,
    val selectedPlusOne: Boolean = false,
    val isDarkMode: Boolean = true,
    val specialRangeMin: Int = 0,
    val specialRangeMax: Int = 0,
    val tossedCoinFace: CoinFace = CoinFace.HEADS,
    val rolledDiceFace: DiceFace = DiceFace.FIVE
)
