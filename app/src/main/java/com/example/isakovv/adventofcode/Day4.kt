package com.example.isakovv.adventofcode

import android.util.Log
import java.util.*

/**
 * Created by isakov.v on 12/8/16.
 */
object Advert7 {
    fun result(): Int {
        var result = 0
        input4.lines().forEach {
            val splittedCode = splitCode(it)
            val lettersFrequencyMap = getFrequencyMap(splittedCode.letters)
            val sortedLettersByFrequencyAndAlphabetically = getSortedLetters(lettersFrequencyMap)
            if (splittedCode.checksum == sortedLettersByFrequencyAndAlphabetically.substring(0, splittedCode.checksum.length)) {
                result += splittedCode.sectorId
            }
            Log.d("__", splittedCode.letters)
            Log.d("__", sortedLettersByFrequencyAndAlphabetically)
        }
        return result
    }
}

object Advert8 {
    fun result(): Int {
        var result = 0
        input4.lines().forEach {
            val splittedCode = splitCode(it)
            val encryptedText = splittedCode.letters.filter { it != '-' }.map {
                val alphabetPos = it.toByte() - 'a'.toByte()
                val resAlphabetPos = (alphabetPos + splittedCode.sectorId) % 26
                'a'.plus(resAlphabetPos)
            }.joinToString("")
            Log.d("__", encryptedText)
            if (encryptedText.contains(input4_1.toLowerCase().replace(" ", ""))) {
                return splittedCode.sectorId
            }
        }
        return result
    }
}

private fun getSortedLetters(lettersFrequencyMap: Map<Char, Int>): String {
    val frequencyToListOfLettersMap = HashMap<Int, MutableList<Char>>()
    for ((key, value) in lettersFrequencyMap) {
        val listOfLettersWithSameFrequency = frequencyToListOfLettersMap[value] ?: ArrayList()
        listOfLettersWithSameFrequency.add(key)
        frequencyToListOfLettersMap.put(value, listOfLettersWithSameFrequency)
    }
    for ((key, value) in frequencyToListOfLettersMap) {
        value.sort()
    }
    val keys = ArrayList(frequencyToListOfLettersMap.keys)
    keys.sortDescending()
    var sortedLetters = ""

    keys.map { frequencyToListOfLettersMap[it] }.forEach { listOfLetters -> listOfLetters!!.forEach { sortedLetters += it } }
    return sortedLetters
}

private fun getFrequencyMap(letters: String): Map<Char, Int> {
    val frequencyMap = HashMap<Char, Int>()
    letters.filter { it != '-' }.forEach {
        val frequency = frequencyMap[it] ?: 0
        frequencyMap.put(it, frequency + 1)
    }
    return frequencyMap
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