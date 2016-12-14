package com.example.isakovv.adventofcode

import java.util.regex.Pattern

/**
 * Created by isakov.v on 12/14/16.
 */
object Advent17 {
    fun result(): Int {
        var markerStartIndex = 0
        var markerEndIndex = 0
        var sequenceEndPos = 0
        var decodedSequence = ""
        var marker: Marker? = null
        while (true) {
            markerStartIndex = input8.indexOf('(', sequenceEndPos)
            if (markerStartIndex > sequenceEndPos) {
                decodedSequence += input.substring(sequenceEndPos, markerStartIndex)
            }
            if (markerStartIndex == -1) {
                decodedSequence += input.substring(sequenceEndPos, input.length)
                break
            }

        }

    }
}

data class Marker(val length: Int, val repeatedSequenceLength: Int, val repeatedCount: Int) {
    companion object {
        val pattern = Pattern.compile("((\\d+)x(\\d+))")
        fun parseMarker(input: String, startPos: Int): Marker {
            val endPos = input.indexOf(')', startPos)
            if (endPos == -1)
                throw IllegalStateException("Can't find marker end")
            val markerString = input.substring(startPos, endPos + 1)
            return Marker(endPos - startPos, Integer.valueOf(pattern.matcher(markerString).group(1)), Integer.valueOf(pattern.matcher(markerString).group(2)))
        }
    }
}