package com.example.isakovv.adventofcode

import android.util.Log

/**
 * Created by isakov.v on 12/8/16.
 */
object Advert7 {
    fun result(): Int {
        var result = 0
        input4.lines().forEach {
            val splittedCode = splitCode(it)
            Log.d("__", splittedCode.toString())
        }
        return result
    }

    private fun splitCode(code: String): SplittedCode {
        val indexOfSector = code.indexOfFirst { it in '0'..'9' }
        val indexOfChecksum = code.indexOf("[")
        val letters = code.substring(0, indexOfSector)
        val sectorId = Integer.parseInt(code.substring(indexOfSector, indexOfChecksum))
        val checksum = code.substring(indexOfChecksum + 1, code.lastIndex)
        return SplittedCode(letters, sectorId, checksum)
    }

    data class SplittedCode(val letters: String, val sectorId: Int, val checksum: String)
}