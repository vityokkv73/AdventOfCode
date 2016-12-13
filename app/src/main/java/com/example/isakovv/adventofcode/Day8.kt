package com.example.isakovv.adventofcode

import java.util.regex.Pattern

/**
 * Created by deerhunter on 12/12/2016.
 */
object Advent15 {
    val rotateRowPattern = Pattern.compile("rotate row y=(\\d+) by (\\d+)")
    val rotateColumnPattern = Pattern.compile("rotate column x=(\\d+) by (\\d+)")
    val rectPattern = Pattern.compile("rect (\\d+)x(\\d+)")
    val lcd = Array(input8RowsCount, { BooleanArray(input8ColumnsCount, { false }) })

    fun result(): Int {
        var res = 0
        input8.lines().forEach { command ->
            val createRectMatcher = rectPattern.matcher(command)
            if (createRectMatcher.find()) {
                setRect(Integer.valueOf(createRectMatcher.group(0)), Integer.valueOf(createRectMatcher.group(1)))
            }
            val rotateRowMatcher = rotateRowPattern.matcher(command)
            if (rotateRowMatcher.find()) {
                rotateRow(rotateRowMatcher.group(1), rotateRowMatcher.group(2))
            }
            val rotateColumnMatcher = rotateColumnPattern.matcher(command)
            if (rotateColumnMatcher.find()) {
                rotateColumn(rotateColumnMatcher.group(1), rotateColumnMatcher.group(2))
            }
        }
        return res
    }

    private fun setRect(width: Int, height: Int) {
        for (i in 0 until height) {
            val row = lcd[i]
            for (j in 0 .. width) {
                row[j] = true
            }
        }
    }

    private fun rotateRow(row: String, times: String) {

    }

    private fun rotateColumn(column: String, times: String) {

    }
}