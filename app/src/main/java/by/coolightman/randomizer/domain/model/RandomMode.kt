package by.coolightman.randomizer.domain.model

enum class RandomMode(val title: String) {
    TO_9("0..9"),
    TO_10("1..10"),
    TO_99("0..99"),
    TO_100("1..100"),
    TO_999("0..999"),
    TO_1000("1..1000"),
    COIN("Coin"),
    DICE("Dice"),
    SPECIAL("Special")
}