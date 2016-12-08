package com.example.isakovv.adventofcode

/**
 * Created by isakov.v on 12/8/16.
 */

object Advert5 {
    fun result(): Int {
        var result = 0
        input3.lines().forEach {
            if (isTriangle(it.split(" ").filter(String::isNotEmpty).map { Integer.parseInt(it.trim()) })) {
                result++
            }
        }
        return result
    }
}

object Advert6 {
    fun result(): Int {
        var result = 0
        val numbers = input3.lines().map {
            it.split(" ").filter(String::isNotEmpty).map { Integer.parseInt(it.trim()) }
        }

        for (j in 0..2) {
            (0 until numbers.size / 3)
                    .filter { isTriangle(listOf(numbers[it * 3][j], numbers[it * 3 + 1][j], numbers[it * 3 + 2][j])) }
                    .forEach { result++ }
        }
        return result
    }
}

private fun isTriangle(map: List<Int>): Boolean {
    val sum = map.sum().toFloat()
    val max = map.max()!!.toFloat()
    return (sum - max) > max
}
