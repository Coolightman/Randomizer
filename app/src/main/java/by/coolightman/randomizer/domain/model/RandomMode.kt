package by.coolightman.randomizer.domain.model

import androidx.annotation.StringRes
import by.coolightman.randomizer.R

enum class RandomMode(@StringRes val title: Int, val description: String) {
    TO_9(R.string.random_0_9, "0 ≤ X ≤ 9"),
    TO_10(R.string.random_1_10, "1 ≤ X ≤ 10"),
    TO_99(R.string.random_0_99, "0 ≤ X ≤ 99"),
    TO_100(R.string.random_1_100, "1 ≤ X ≤ 100"),
    TO_999(R.string.random_0_999, "0 ≤ X ≤ 999"),
    TO_1000(R.string.random_1_1000, "1 ≤ X ≤ 1000"),
    COIN(R.string.coin_bt, "Heads/Tails"),
    DICE(R.string.dice_bt, "Dice"),
    SPECIAL(R.string.special_bt, "")
}