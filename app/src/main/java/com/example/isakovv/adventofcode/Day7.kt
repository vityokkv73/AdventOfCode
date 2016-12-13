package com.example.isakovv.adventofcode

import android.content.Context
import android.util.Log

/**
 * Created by deerhunter on 12/11/2016.
 */
object Advent13 {
    fun result(context: Context): Int {
        var res = 0
        context.resources.assets.open("input7.txt").reader().readLines().forEach outerLbl@ {
            var shouldTest = true
            var supports = false
            it.forEachIndexed { pos, char ->
                if (pos <= it.length - 4) {
                    if (shouldTest) {
                        if (char == '[') {
                            shouldTest = false
                        } else {
                            if (it.substring(pos, pos + 4).isABBA()) {
                                supports = true
                                Log.d("res", it + " " + it.substring(pos, pos + 4))
                            }
                        }
                    } else {
                        if (it.substring(pos, pos + 4).isABBA()) {
                            return@outerLbl
                        }
                        if (char == ']') {
                            shouldTest = true
                        }
                    }
                }
            }
            if (supports) {
                res++
            }
        }
        return res
    }
}

object Advent14 {
    fun result(context: Context): Int {
        var res = 0
        context.resources.assets.open("input7.txt").reader().readLines().forEach outerLbl@ {
            var outsideBrackets = true
            val aba = ArrayList<String>()
            val abaInBrackets = ArrayList<String>()
            it.forEachIndexed { pos, char ->
                if (pos <= it.length - 3) {
                    if (outsideBrackets) {
                        if (char == '[') {
                            outsideBrackets = false
                        } else {
                            if (it.substring(pos, pos + 3).isABA()) {
                                aba.add(it.substring(pos, pos + 3))
                            }
                        }
                    } else {
                        if (it.substring(pos, pos + 3).isABA()) {
                            abaInBrackets.add(it.substring(pos, pos + 3))
                        }
                        if (char == ']') {
                            outsideBrackets = true
                        }
                    }
                }
            }
            aba.forEach { abaItem ->
                if (abaInBrackets.find { it == "" + abaItem[1] + abaItem[0] + abaItem[1] } != null) {
                    res++
                    return@outerLbl
                }
            }
        }
        return res
    }
}

private fun String.isABBA(): Boolean {
    return (this[0] == this[3] && this[1] == this[2] && this[0] != this[1])
}

private fun String.isABA(): Boolean {
    return this[0] == this[2]
}