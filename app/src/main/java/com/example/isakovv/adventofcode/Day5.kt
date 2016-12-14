package com.example.isakovv.adventofcode

import android.util.Log
import java.nio.charset.Charset
import java.security.MessageDigest
import java.util.*

/**
 * Created by isakov.v on 12/9/16.
 */
object Advert9 {
    fun result(): String {
        var result = ""
        for (i in 0..Int.MAX_VALUE){
            val str = input5 + i
            val digStr = getMD5(str)
            var index = digStr.indexOf("00000")
            if (index == 0) {
                    Log.d("index", "index=$index, chat=${digStr[index]}")
                    result += digStr[5]
                    if (result.length == 8)
                        return result
                }
        }
        return result
    }
}

fun getMD5(input: String) : String {
    val md5 = MessageDigest.getInstance("MD5")
    return md5.digest(input.toByteArray(Charset.forName("UTF-8"))).toHexString()
}

fun ByteArray.toHexString(): String {
    val sb = StringBuilder(size * 2)
    for (b in this)
        sb.append(String.format("%02x", b and 0xff.toByte()))
    return sb.toString()
}

object Advert10 {
    fun result(): String {
        var res =""
        val result = HashMap<Int, Char>()
        for (i in 0..Int.MAX_VALUE){
            val str = input5 + i
            val digStr = getMD5(str)
            val index = digStr.indexOf("00000")
            if (index == 0) {
                if (digStr[5] in '0'.. '7') {
                    val pos = Integer.valueOf("" + digStr[5])
                    if (result[pos] == null) {
                        result[pos] = digStr[6]
                        if (result.size == 8) {
                            for (j in 0..7) {
                                res += result[j]
                            }
                            return res
                        }
                    }
                }
            }
        }
        return res
    }
}