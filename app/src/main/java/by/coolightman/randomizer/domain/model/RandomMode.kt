package by.coolightman.randomizer.domain.model

enum class RandomMode(val title: String, val description: String) {
    TO_9("0..9", "0 ≤ X ≤ 9"),
    TO_10("1..10", "1 ≤ X ≤ 10"),
    TO_99("0..99", "0 ≤ X ≤ 99"),
    TO_100("1..100", "1 ≤ X ≤ 100"),
    TO_999("0..999", "0 ≤ X ≤ 999"),
    TO_1000("1..1000", "1 ≤ X ≤ 1000"),
    COIN("Coin", "Heads/Tails"),
    DICE("Dice", "Dice"),
    SPECIAL("Special", "")
}