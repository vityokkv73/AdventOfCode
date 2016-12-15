package com.example.isakovv.adventofcode

import java.util.regex.Pattern

/**
 * Created by isakov.v on 12/15/16.
 */
object Day12 {
    val copyCommandPattern = Pattern.compile("cpy ((-?\\d+)|a|b|c|d) (a|b|c|d)")
    val jnzCommandPattern = Pattern.compile("jnz ((-?\\d+)|a|b|c|d) ((-?\\d+)|a|b|c|d)")
    val incCommandPattern = Pattern.compile("inc (a|b|c|d)")
    val decCommandPattern = Pattern.compile("dec (a|b|c|d)")
    val registers = mutableMapOf("a" to 0, "b" to 0, "c" to 0, "d" to 0)

    fun advent1(): Int {
        val commands = input12.lines().map { parseCommand(it) }
        var currentPos = 0
        var currentCommand: CpuCommand
        val commandsLength = commands.size

        while (currentPos < commands.size) {
            currentCommand = commands[currentPos]
            when (currentCommand) {
                is CpuCommand.IncCommand -> processIncrement(currentCommand)
                is CpuCommand.DecCommand -> processDecrement(currentCommand)
                is CpuCommand.JNZCommand -> currentPos += processJNZ(currentCommand)
                is CpuCommand.CopyCommand -> processCopy(currentCommand)
            }

        }

        return registers["a"]!!
    }

    private fun processIncrement(currentCommand: CpuCommand.IncCommand) {
        val curValue = registers[currentCommand.register] ?: 0
        registers.put(currentCommand.register, curValue + 1)
    }

    private fun processDecrement(currentCommand: CpuCommand.DecCommand) {
        val curValue = registers[currentCommand.register] ?: 0
        registers.put(currentCommand.register, curValue - 1)
    }

    private fun processJNZ(currentCommand: CpuCommand.JNZCommand): Int {
        val valueToTest = getValue(currentCommand.valueToTest)
        if (valueToTest == 0) {
            return if (currentCommand.valueToTest in "a".."d") {
                registers[currentCommand.valueToTest] ?: 0
            } else {
                Integer.valueOf(currentCommand.valueToTest)
            }
        }
    }

    private fun getValue(value: String): Int {
        return if (value in "a".."d") {
            registers[value] ?: 0
        } else {
            Integer.valueOf(value)
        }
    }

    private fun processCopy(currentCommand: CpuCommand.CopyCommand) {

    }

    private fun parseCommand(commandStr: String): CpuCommand {
        val copyCommandMatcher = copyCommandPattern.matcher(commandStr)
        val jnzCommandMatcher = jnzCommandPattern.matcher(commandStr)
        val incCommandMatcher = incCommandPattern.matcher(commandStr)
        val decCommandMatcher = decCommandPattern.matcher(commandStr)

        if (copyCommandMatcher.find()) {
            return CpuCommand.CopyCommand(copyCommandMatcher.group(1), copyCommandMatcher.group(3))
        } else if (jnzCommandMatcher.find()) {
            return CpuCommand.JNZCommand(jnzCommandMatcher.group(1), jnzCommandMatcher.group(3))
        } else if (incCommandMatcher.find()) {
            return CpuCommand.IncCommand(incCommandMatcher.group(1))
        } else if (decCommandMatcher.find()) {
            return CpuCommand.DecCommand(decCommandMatcher.group(1))
        }
        throw IllegalArgumentException("Problem to parse command $commandStr")
    }
}

sealed class CpuCommand {
    data class CopyCommand(val from: String, val to: String) : CpuCommand()
    data class IncCommand(val register: String) : CpuCommand()
    data class DecCommand(val register: String) : CpuCommand()
    data class JNZCommand(val valueToTest: String, val offset: String) : CpuCommand()
}