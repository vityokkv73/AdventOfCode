package com.example.isakovv.adventofcode

import android.util.Log
import java.nio.charset.Charset
import java.security.MessageDigest

/**
 * Created by deerhunter on 12/11/2016.
 */
object Advert11 {
    fun result(): String {
        val data = ArrayList<HashMap<Char, Int>>(6)

        input6.lines().forEach {
            it.forEachIndexed { i, char ->
                if (data.size <= i) {
                    data.add(HashMap())
                }
                val charFrequency = data[i]
                charFrequency[char] = (charFrequency[char] ?: 0) + 1
                data[i] = charFrequency
            }
        }

        val mostFrequentLetters = ArrayList<Pair<Char, Int>>()

        for ((i, charFrequencyMap) in data.withIndex()) {
            val mostFrequest = Pair(' ', 0)
            mostFrequentLetters.add(mostFrequest)
            for ((key, value) in charFrequencyMap) {
                if (mostFrequentLetters[i].second < value) {
                    mostFrequentLetters[i] = Pair(key, value)
                }
            }
        }
        return kotlin.text.String(mostFrequentLetters.map { it.first }.toCharArray(), 0, mostFrequentLetters.size)
    }
}

object Advert12 {
    fun result(): String {
        val data = ArrayList<HashMap<Char, Int>>(6)

        input6.lines().forEach {
            it.forEachIndexed { i, char ->
                if (data.size <= i) {
                    data.add(HashMap())
                }
                val charFrequency = data[i]
                charFrequency[char] = (charFrequency[char] ?: 0) + 1
                data[i] = charFrequency
            }
        }

        val mostFrequentLetters = ArrayList<Pair<Char, Int>>()

        for ((i, charFrequencyMap) in data.withIndex()) {
            val mostFrequest = Pair(' ', Integer.MAX_VALUE)
            mostFrequentLetters.add(mostFrequest)
            for ((key, value) in charFrequencyMap) {
                if (mostFrequentLetters[i].second > value) {
                    mostFrequentLetters[i] = Pair(key, value)
                }
            }
        }
        return kotlin.text.String(mostFrequentLetters.map { it.first }.toCharArray(), 0, mostFrequentLetters.size)
    }
}