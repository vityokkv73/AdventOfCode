package com.example.isakovv.adventofcode

import java.nio.charset.Charset
import java.security.MessageDigest

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
            if (index != -1) {
               index +=5
                if (digStr.lastIndex <= index) {
                    result += digStr[index]
                    if (result.length == 8)
                        return result
                }
            }
        }
        return result
    }
}

fun getMD5(input: String) : String {
    val md5 = MessageDigest.getInstance("MD5")
    return String(md5.digest(input.toByteArray(Charset.forName("UTF-8"))))
}