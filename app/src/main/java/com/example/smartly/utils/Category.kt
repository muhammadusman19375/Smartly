package com.example.smartly.utils

enum class Category(val value: Int) {
    Mythology(20),
    Sports(21),
    Geography(22),
    History(23),
    Politics(24),
    Art(25),
    Celebrities(26),
    Animals(27),
    Vehicles(28);

    companion object {
        fun get(value: String): Int {
            val viewType = Category.values().find { it.name == value }
            return viewType?.value ?: -1
        }
    }
}