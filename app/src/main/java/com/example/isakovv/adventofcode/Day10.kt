package com.example.isakovv.adventofcode

import java.util.regex.Pattern

/**
 * Created by isakov.v on 12/15/16.
 */
object Advent19_20 {
    val giveCommandPattern = Pattern.compile("bot (\\d+) gives low to (bot|output) (\\d+) and high to (bot|output) (\\d+)")
    val takeCommandPattern = Pattern.compile("value (\\d+) goes to bot (\\d+)")

    val bots = ArrayList<Bot>()
    val buckets = ArrayList<Bucket>()

    fun result(): Int {
        var res = -1

        val commands = input10.lines().map { commandStr -> parseCommand(commandStr) }.toMutableList()

        val commandsToRemove = ArrayList<Command>()

        while (commands.isNotEmpty()) {
            commands.forEach { command ->
                when (command) {
                    is Command.TakeCommand -> if (processTakeCommand(command)) commandsToRemove.add(command)
                    is Command.GiveCommand -> if (processGiveCommand(command)) commandsToRemove.add(command)
                }
                // 19
                if (res == -1) {
                    bots.find {
                        (it.firstChipId == input10_1 && it.secondChipId == input10_2) ||
                                (it.firstChipId == input10_2 && it.secondChipId == input10_1)
                    }?.run {
                        res = id
                    }
                }
            }
            commands.removeAll(commandsToRemove)
        }

        res = 1
        buckets.filter { it.id in 0..2 }.forEach { res *= it.chips.reduce { accumulator, chip -> accumulator * chip } }

        return res
    }

    private fun processTakeCommand(command: Command.TakeCommand): Boolean {
        var bot = bots.find { it.id == command.botId }
        if (bot == null) {
            bot = Bot(command.botId)
            bots.add(bot)
        }
        val chipId = command.chipId
        if (bot.hasEmptySlot()) {
            bot.takeChip(chipId)
            return true
        }
        return false
    }

    private fun processGiveCommand(command: Command.GiveCommand): Boolean {
        var fromBot = bots.find { it.id == command.fromBotId }
        if (fromBot == null) {
            fromBot = Bot(command.fromBotId)
            bots.add(fromBot)
            return false
        }
        val firstChipId = fromBot.firstChipId
        val secondChipId = fromBot.secondChipId
        if (firstChipId != null && secondChipId != null) {
            val higherChipId = if (firstChipId > secondChipId) firstChipId else secondChipId
            val lowerChipId = if (firstChipId > secondChipId) secondChipId else firstChipId
            val higherChipHolder = getAndPutChipHolderToList(command.higherChipTo)
            val lowerChipHolder = getAndPutChipHolderToList(command.lowerChipTo)
            putChipToChipHolder(lowerChipHolder, lowerChipId)
            putChipToChipHolder(higherChipHolder, higherChipId)
            return true
        }
        return false
    }

    private fun getAndPutChipHolderToList(chipHolderToFind: ChipHolder): ChipHolder {
        var chipHolder = findChipHolder(chipHolderToFind)
        if (chipHolder == null) {
            chipHolder = chipHolderToFind
            if (chipHolder is Bot) {
                bots.add(chipHolder)
            } else if (chipHolder is Bucket) {
                buckets.add(chipHolder)
            }
        }
        return chipHolder
    }

    private fun putChipToChipHolder(lowerChipHolder: ChipHolder?, lowerChipId: Int) {
        if (lowerChipHolder is Bucket) {
            lowerChipHolder.chips.add(lowerChipId)
        } else if (lowerChipHolder is Bot && lowerChipHolder.hasEmptySlot()) {
            lowerChipHolder.takeChip(lowerChipId)
        }
    }

    private fun findChipHolder(chipHolder: ChipHolder): ChipHolder? {
        return when (chipHolder) {
            is Bot -> bots.find { it.id == chipHolder.id }
            is Bucket -> buckets.find { it.id == chipHolder.id }
            else -> null
        }
    }

    private fun parseCommand(commandStr: String): Command {
        val giveCommandMatcher = giveCommandPattern.matcher(commandStr)
        val takeCommandMatcher = takeCommandPattern.matcher(commandStr)

        if (giveCommandMatcher.find()) {
            val fromBotId = Integer.valueOf(giveCommandMatcher.group(1))
            val lowerChipHolderId = Integer.valueOf(giveCommandMatcher.group(3))
            val higherChipHolderId = Integer.valueOf(giveCommandMatcher.group(5))
            val lowerHolderStr = giveCommandMatcher.group(2)
            val higherHolderStr = giveCommandMatcher.group(4)
            return Command.GiveCommand(fromBotId,
                    if (lowerHolderStr == "bot") Bot(lowerChipHolderId) else Bucket(lowerChipHolderId),
                    if (higherHolderStr == "bot") Bot(higherChipHolderId) else Bucket(higherChipHolderId)
            )
        } else if (takeCommandMatcher.find()) {
            val chipId = Integer.valueOf(takeCommandMatcher.group(1))
            val botId = Integer.valueOf(takeCommandMatcher.group(2))
            return Command.TakeCommand(chipId, botId)
        }
        throw  IllegalArgumentException("Can not parse command: $commandStr")
    }
}

class Bot(id: Int, var firstChipId: Int? = null, var secondChipId: Int? = null) : ChipHolder(id) {
    override fun toString(): String {
        return "Bot(id = $id, firstChipId=$firstChipId, secondChipId=$secondChipId)"
    }

    fun hasEmptySlot() = firstChipId == null || secondChipId == null

    fun takeChip(chipId: Int) {
        if (firstChipId == null) {
            firstChipId = chipId
        } else if (secondChipId == null) {
            secondChipId = chipId
        } else {
            throw IllegalStateException("Bot already has 2 chips!")
        }
    }
}

class Bucket(id: Int, val chips: MutableList<Int> = ArrayList()) : ChipHolder(id) {
    override fun toString(): String {
        return "Bucket(id = $id, chips=${chips.joinToString()}"
    }
}

open class ChipHolder(val id: Int)

sealed class Command {
    class TakeCommand(val chipId: Int, val botId: Int) : Command()
    class GiveCommand(val fromBotId: Int, val lowerChipTo: ChipHolder, val higherChipTo: ChipHolder) : Command()
}