package by.coolightman.randomizer.domain.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import by.coolightman.randomizer.R

enum class CoinFace(
    @DrawableRes val imgRes: Int,
    @StringRes val descriptionRes: Int
) {
    HEADS(R.drawable.heads_o, R.string.coin_heads_description),
    TAILS(R.drawable.tails_r, R.string.coin_tails_description)
}