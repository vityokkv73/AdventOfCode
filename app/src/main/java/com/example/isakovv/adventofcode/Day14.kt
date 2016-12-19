package com.example.isakovv.adventofcode

import android.util.Log
import java.nio.charset.Charset
import java.security.MessageDigest
import java.util.regex.Matcher
import java.util.regex.Pattern

/**
 * Created by isakov.v on 12/19/16.
 */
object Day14 {
    val threeOrMoreTimesPattern = Pattern.compile("(.)\\1{2,}")
    val fiveOrMoreTimesPattern = Pattern.compile("(.)\\1{4,}")
    fun advent27(): Int {
        var index = -1
        var curMD5: String
        var threeTimesMatcher: Matcher
        var fiveTimesMatcher: Matcher
        val letterToTriplePositions = HashMap<String, ArrayList<Int>>()
        val keys = arrayListOf<String>()
        var indexOfFirstTriple = -1
        for (i in 0..Int.MAX_VALUE) {
            curMD5 = getMD5(input14 + i)
            threeTimesMatcher = threeOrMoreTimesPattern.matcher(curMD5)
            fiveTimesMatcher = fiveOrMoreTimesPattern.matcher(curMD5)
            if (fiveTimesMatcher.find()) {
                val tripleLetterPositions = letterToTriplePositions.getOrPut(fiveTimesMatcher.group(1), { arrayListOf<Int>() })
                Log.d("Five", "letter = ${fiveTimesMatcher.group(1)}, pos = $i")
                val indexOfIndexOfFirstTriple = tripleLetterPositions.map { i - it }.indexOfFirst { it in (1..1000) }
                if (indexOfIndexOfFirstTriple != -1) {
                    keys.add(curMD5)
                    indexOfFirstTriple = tripleLetterPositions[indexOfIndexOfFirstTriple]
                    Log.d("Five eurica", "letter = ${fiveTimesMatcher.group(1)}, pos = $i")
                }
            }
            if (threeTimesMatcher.find()) {
                letterToTriplePositions.getOrPut(threeTimesMatcher.group(1), { arrayListOf<Int>() }).add(i)
                Log.d("Triple", "letter = ${threeTimesMatcher.group(1)}, pos = $i")
            }
            if (keys.size == 64) {
                return indexOfFirstTriple
            }
        }
        return index
    }

    fun getMD5(input: String): String {
        val md5 = MessageDigest.getInstance("MD5")
        return md5.digest(input.toByteArray(Charset.forName("UTF-8"))).toHexString()
    }

    fun ByteArray.toHexString(): String {
        val sb = StringBuilder(size * 2)
        for (b in this)
            sb.append(String.format("%02x", b and 0xff.toByte()))
        return sb.toString()
    }
}