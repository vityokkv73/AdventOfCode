package com.example.isakovv.adventofcode

import android.util.Log
import android.util.Pair

/**
 * Created by isakov.v on 12/16/16.
 */
object Day13 {
    val markedPointsMap = HashMap<Int, ArrayList<MarkedLocation>>()
    val markedPoints = ArrayList<MarkedLocation>()

    fun advent25(): Int {
        markedPointsMap[0] = arrayListOf(input13_startPoint)
        markedPoints.add(input13_startPoint)
        var currentDistance = 0
        do {
            markedPointsMap[currentDistance + 1] = ArrayList()
            markedPointsMap[currentDistance]?.forEach {
                val leftLocation = MarkedLocation(it.x - 1, it.y, currentDistance + 1)
                val rightLocation = MarkedLocation(it.x + 1, it.y, currentDistance + 1)
                val topLocation = MarkedLocation(it.x, it.y - 1, currentDistance + 1)
                val bottomLocation = MarkedLocation(it.x, it.y + 1, currentDistance + 1)

                checkAndMarkLocation(currentDistance, leftLocation)
                checkAndMarkLocation(currentDistance, rightLocation)
                checkAndMarkLocation(currentDistance, topLocation)
                checkAndMarkLocation(currentDistance, bottomLocation)
            }
            currentDistance++
        } while (markedPointsMap[currentDistance]?.isNotEmpty() == true && !markedPointsMap[currentDistance]!!.contains(input13_finishPoint))
        return currentDistance
    }

    fun advent26(): Int {
        markedPointsMap[0] = arrayListOf(input13_startPoint)
        markedPoints.add(input13_startPoint)
        var currentDistance = 0
        do {
            markedPointsMap[currentDistance + 1] = ArrayList()
            markedPointsMap[currentDistance]?.forEach {
                val leftLocation = MarkedLocation(it.x - 1, it.y, currentDistance + 1)
                val rightLocation = MarkedLocation(it.x + 1, it.y, currentDistance + 1)
                val topLocation = MarkedLocation(it.x, it.y - 1, currentDistance + 1)
                val bottomLocation = MarkedLocation(it.x, it.y + 1, currentDistance + 1)

                checkAndMarkLocation(currentDistance, leftLocation)
                checkAndMarkLocation(currentDistance, rightLocation)
                checkAndMarkLocation(currentDistance, topLocation)
                checkAndMarkLocation(currentDistance, bottomLocation)
            }
            currentDistance++
        } while (markedPointsMap[currentDistance]?.isNotEmpty() == true && currentDistance < input13_stepsCount)
        return markedPoints.size
    }

    private fun checkAndMarkLocation(currentDistance: Int, location: MarkedLocation) {
        if (isLocationAnOpenSpace(location) && !markedPoints.contains(location)) {
            markedPoints.add(location)
            markedPointsMap[currentDistance + 1]!!.add(location)
        }
    }

    fun isLocationAnOpenSpace(location: MarkedLocation): Boolean {
        if (location.x < 0 || location.y < 0)
            return false

        var value = location.x * location.x + 3 * location.x + 2 * location.x * location.y + location.y + location.y * location.y + input13_designerFavoriteNumber
        var onesCount = 0
        while (value != 0) {
            if (value % 2 == 1) {
                onesCount++
            }
            value = value shr 1
        }
        return onesCount % 2 == 0
    }
}

data class MarkedLocation(val x: Int, val y: Int, var value: Int? = null) : Pair<Int, Int>(x, y) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false
        if (!super.equals(other)) return false

        other as MarkedLocation

        if (x != other.x) return false
        if (y != other.y) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + x
        result = 31 * result + y
        return result
    }
}