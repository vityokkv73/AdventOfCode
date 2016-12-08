package com.example.isakovv.adventofcode

import android.graphics.Point
import java.util.*

/**
 * Created by isakov.v on 12/8/16.
 */
object Advert2 {
    fun result(): Int {
        val places = HashSet<Point>()

        var curPlace = Point(0, 0)
        var curSide = Side.NORTH
        places.add(curPlace)
        val listOfMoves = input.split(", ").map {
            curSide = Side.getNextSide(curSide, it[0])
            Move.parseMove(curSide, it)
        }

        for (move in listOfMoves) {
            val listOfNewVisitedPlaces = generateNewPlaces(move, curPlace)
            curPlace = listOfNewVisitedPlaces.last()
            for (newPlace in listOfNewVisitedPlaces) {
                if (places.contains(newPlace))
                    return newPlace.calcDistance(Point(0, 0))
                places.add(newPlace)
            }
        }

        return 0
    }

    private fun generateNewPlaces(move: Move, curPlace: Point): List<Point> {
        val newPlaces = ArrayList<Point>()
        var place = curPlace
        for (i in 1..move.steps) {
            place = place.next(move.side)
            newPlaces.add(place)
        }
        return newPlaces
    }


    private fun Point.next(side: Side): Point {
        return when (side) {
            Side.NORTH -> Point(x, y - 1)
            Side.EAST -> Point(x + 1, y)
            Side.SOUTH -> Point(x, y + 1)
            Side.WEST -> Point(x - 1, y)
        }
    }

    private fun Point.calcDistance(point: Point): Int {
        return Math.abs(point.x - x) + Math.abs(point.y - y)
    }

    data class Move(val side: Side, val steps: Int) {
        companion object {
            fun parseMove(side: Side, input: String): Move {
                return Move(side, Integer.parseInt(input.subSequence(1, input.length).toString()))
            }
        }
    }

    enum class Side {
        NORTH,
        EAST,
        SOUTH,
        WEST;

        companion object {
            fun getNextSide(side: Side, turnSide: Char): Side {
                return if (turnSide == 'L') turnLeft(side) else turnRight(side)
            }

            fun turnRight(prevSide: Side) = values()[(prevSide.ordinal + 1) % values().size]

            fun turnLeft(prevSide: Side) = values()[(prevSide.ordinal - 1 + values().size) % values().size]
        }
    }
}
