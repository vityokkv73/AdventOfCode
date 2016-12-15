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
        var marker: Marker
        while (true) {
            markerStartIndex = input9.indexOf('(', sequenceEndPos)
            if (markerStartIndex > sequenceEndPos) {
                decodedSequence += input9.substring(sequenceEndPos, markerStartIndex)
            }
            if (markerStartIndex == -1) {
                decodedSequence += input9.substring(sequenceEndPos, input9.length)
                break
            }
            marker = Marker.parseMarker(input9, markerStartIndex)
            markerEndIndex = markerStartIndex + marker.length + 1
            decodedSequence += input9.substring(markerEndIndex, markerEndIndex + marker.repeatedSequenceLength).repeat(marker.repeatedCount)
            sequenceEndPos = markerEndIndex + marker.repeatedSequenceLength
        }
        return decodedSequence.length
    }
}

object Advent18 {
    fun result(input: String): Long {
        var markerStartIndex :Int
        var markerEndIndex :Int
        var sequenceEndPos = 0
        var decodedSequenceSize = 0L
        var decodedChunk: String
        var marker: Marker
        while (true) {
            markerStartIndex = input.indexOf('(', sequenceEndPos)
            if (markerStartIndex > sequenceEndPos) {
                decodedSequenceSize += input.substring(sequenceEndPos, markerStartIndex).length
            }
            if (markerStartIndex == -1) {
                decodedSequenceSize += input.substring(sequenceEndPos, input.length).length
                break
            }
            marker = Marker.parseMarker(input, markerStartIndex)
            markerEndIndex = markerStartIndex + marker.length
            sequenceEndPos = markerEndIndex + marker.repeatedSequenceLength

            decodedChunk = input.substring(markerEndIndex, markerEndIndex + marker.repeatedSequenceLength).repeat(marker.repeatedCount)

            decodedSequenceSize += result(decodedChunk)
        }
        return decodedSequenceSize
    }
}

data class Marker(val length: Int, val repeatedSequenceLength: Int, val repeatedCount: Int) {
    companion object {
        val pattern = Pattern.compile("\\((\\d+)x(\\d+)\\)")
        fun parseMarker(input: String, startPos: Int): Marker {
            val endPos = input.indexOf(')', startPos)
            if (endPos == -1)
                throw IllegalStateException("Can't find marker end")
            val markerString = input.substring(startPos, endPos + 1)
            val matcher = pattern.matcher(markerString)
            if (matcher.find()) {
                return Marker(endPos - startPos + 1, Integer.valueOf(matcher.group(1)), Integer.valueOf(matcher.group(2)))
            }
            throw IllegalStateException("Can't find marker. startPos = $startPos")
        }
    }
}