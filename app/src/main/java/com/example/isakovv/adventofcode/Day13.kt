package com.example.isakovv.adventofcode

import android.util.Pair

/**
 * Created by isakov.v on 12/16/16.
 */
object Day13 {
    val markedPoints = ArrayList<MarkedLocation>()


    fun result() {

    }

    fun isLocationAnOpenSpace(location: MarkedLocation): Boolean {
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
        if (other !is MarkedLocation) {
            return false
        }
        return other.first == first && other.second == second
    }

    override fun hashCode(): Int {
        return x.hashCode() xor x.hashCode()
    }
}