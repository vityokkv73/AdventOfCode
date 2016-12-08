package com.example.isakovv.adventofcode

import android.graphics.Point
import android.util.Log

/**
 * Created by isakov.v on 12/8/16.
 */

val numbers = arrayOf(
        arrayOf("1", "2", "3"),
        arrayOf("4", "5", "6"),
        arrayOf("7", "8", "9")
)

object Advert3 {
    fun result(): String {
        var res = ""
        var curPlace = Point(1, 1)
        input2.lines().forEach {
            it.forEach {
                curPlace = curPlace.next(it)
                Log.d("results", curPlace.toString())
            }
            res += numbers[curPlace.y][curPlace.x]
        }

        return res
    }

    private fun Point.next(side: Char): Point {
        return correctPoint(
                when (side) {
                    'U' -> Point(x, y - 1)
                    'R' -> Point(x + 1, y)
                    'L' -> Point(x - 1, y)
                    else -> Point(x, y + 1)
                }
        )
    }

    private fun correctPoint(pointToFix: Point): Point {
        val point = Point(pointToFix)
        if (point.x < 0) {
            point.x = 0
        } else if (point.x >= 3) {
            point.x = 2
        }

        if (point.y < 0) {
            point.y = 0
        } else if (point.y >= 3) {
            point.y = 2
        }
        return point
    }
}

val numbersAndLetters = arrayOf(
        arrayOf("", "", "1", "", ""),
        arrayOf("", "2", "3", "4", ""),
        arrayOf("5", "6", "7", "8", "9"),
        arrayOf("", "A", "B", "C", ""),
        arrayOf("", "", "D", "", "")
)

object Advert4 {
    fun result(): String {
        var res = ""
        var curPlace = Point(1, 1)
        input2.lines().forEach {
            it.forEach {
                curPlace = curPlace.next(it)
                Log.d("results", curPlace.toString())
            }
            res += numbersAndLetters[curPlace.y][curPlace.x]
        }

        return res
    }

    private fun Point.next(side: Char): Point {
        return correctPoint(
                this,
                when (side) {
                    'U' -> Point(x, y - 1)
                    'R' -> Point(x + 1, y)
                    'L' -> Point(x - 1, y)
                    else -> Point(x, y + 1)
                }
        )
    }

    private fun correctPoint(prevPoint: Point, nextPoint: Point): Point {
        val point = Point(nextPoint)
        if (point.x < 0) {
            point.x = 0
        } else if (point.x >= 5) {
            point.x = 4
        }

        if (point.y < 0) {
            point.y = 0
        } else if (point.y >= 5) {
            point.y = 4
        }

        if (!numbersAndLetters[point.y][point.x].isEmpty()) {
            return point
        }
        return prevPoint
    }
}



