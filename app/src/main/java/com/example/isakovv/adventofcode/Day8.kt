package com.example.isakovv.adventofcode

import android.util.Log
import java.util.regex.Pattern

/**
 * Created by deerhunter on 12/12/2016.
 */
object Advent15_16 {
    val rotateRowPattern = Pattern.compile("rotate row y=(\\d+) by (\\d+)")
    val rotateColumnPattern = Pattern.compile("rotate column x=(\\d+) by (\\d+)")
    val rectPattern = Pattern.compile("rect (\\d+)x(\\d+)")
    val lcd = Array(input8RowsCount, { BooleanArray(input8ColumnsCount, { false }) })

    fun result(): Int {
        var res = 0
        input8.lines().forEach { command ->
            val createRectMatcher = rectPattern.matcher(command)
            if (createRectMatcher.find()) {
                setRect(Integer.valueOf(createRectMatcher.group(1)), Integer.valueOf(createRectMatcher.group(2)))
            }
            val rotateRowMatcher = rotateRowPattern.matcher(command)
            if (rotateRowMatcher.find()) {
                rotateRow(Integer.valueOf(rotateRowMatcher.group(1)), Integer.valueOf(rotateRowMatcher.group(2)))
            }
            val rotateColumnMatcher = rotateColumnPattern.matcher(command)
            if (rotateColumnMatcher.find()) {
                rotateColumn(Integer.valueOf(rotateColumnMatcher.group(1)), Integer.valueOf(rotateColumnMatcher.group(2)))
            }
        }
        var resStr = ""
        lcd.forEach {
            it.forEach {
                if (it) {
                    resStr += "#"
                    res++
                } else {
                    resStr += "."
                }
            }
            resStr += "\n"
        }
        Log.d("res", resStr)
        return res
    }

    private fun setRect(width: Int, height: Int) {
        for (i in 0 until height) {
            lcd[i].fill(true, 0, width)
        }
    }

    private fun rotateRow(row: Int, times: Int) {
        val rowCopy = lcd[row].copyOf()
        val rowWidth = lcd[row].size
        val shiftRight = times % rowWidth
        for (i in rowCopy.indices) {
            lcd[row][i] = rowCopy[(rowWidth - shiftRight + i) % rowWidth]
        }
    }

    private fun rotateColumn(column: Int, times: Int) {
        val columnHeight = lcd.size
        val columnCopy = BooleanArray(columnHeight, { false })
        lcd.forEachIndexed { index, array -> run { columnCopy[index] = array[column] } }
        val shift = times % columnHeight
        for (i in columnCopy.indices) {
            lcd[i][column] = columnCopy[(columnHeight - shift + i) % columnHeight]
        }
    }
}