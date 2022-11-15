package by.coolightman.randomizer.domain.model

data class ResultItem(
    val createdAt: Long = System.currentTimeMillis(),
    val type: RandomMode,
    val value: Int
)
