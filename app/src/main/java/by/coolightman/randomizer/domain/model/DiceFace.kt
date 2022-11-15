package by.coolightman.randomizer.domain.model

import androidx.annotation.DrawableRes
import by.coolightman.randomizer.R

enum class DiceFace(
    @DrawableRes val imgRes: Int,
) {
    ONE(R.drawable.dice_1),
    TWO(R.drawable.dice_2),
    THREE(R.drawable.dice_3),
    FOUR(R.drawable.dice_4),
    FIVE(R.drawable.dice_5),
    SIX(R.drawable.dice_6)
}