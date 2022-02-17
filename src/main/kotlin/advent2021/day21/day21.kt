package day21

import getResourceAsText
import java.lang.Integer.min
import kotlin.math.max

fun main(args: Array<String>) {
    val input = getResourceAsText("input.txt")
    val inputTest = getResourceAsText("inputExample.txt")
    val inputLines = input.lines()

    var positionPLayerOne = getStartPos(inputLines, 0)
    var positionPLayerTwo = getStartPos(inputLines, 1)
    
    var scorePlayerOne = 0
    var scorePlayerTwo = 0
    var dice = 0
    var count = 0
    while (scorePlayerOne < 1000 || scorePlayerTwo < 1000) {
        
        val throwOne: Pair<Int, Int> = getStepAndDice(dice)
        positionPLayerOne = modulo( positionPLayerOne+throwOne.first,10)
        scorePlayerOne += positionPLayerOne
        count += 3
        if (scorePlayerOne >= 1000) break

        val throwTwo: Pair<Int, Int> = getStepAndDice(throwOne.second)
        positionPLayerTwo = modulo( positionPLayerTwo+throwTwo.first,10)
        scorePlayerTwo += positionPLayerTwo
        
        count += 3
        if (scorePlayerTwo >= 1000) break

        dice = throwTwo.second
    }
    println(min(scorePlayerOne, scorePlayerTwo)*count)


    positionPLayerOne = getStartPos(inputLines, 0)
    positionPLayerTwo = getStartPos(inputLines, 1)
    println(calcPartTwo(GameState(positionPLayerOne, 0, positionPLayerTwo, 0)).let { if(it.first > it.second) it.first else it.second  })
}

private fun getStartPos(inputLines: List<String>, index: Int) = inputLines[index].drop(inputLines[index].indexOf(":") + 1).trim().toInt()

data class GameState(val positionOne: Int, val scoreOne: Int, val positionTwo: Int, val scoreTwo: Int) {
} // need to be data class, equals/hash otherwise not implemented.



val map : HashMap<GameState,Pair<Long,Long>> = hashMapOf()
fun calcPartTwo(gameState: GameState): Pair<Long, Long> {
    if (gameState.scoreOne > 20) {
        return Pair(1, 0)
    }
    if (gameState.scoreTwo > 20) {
        return Pair(1, 0)
    }
    
    val mem = map.get(gameState)
    if (mem != null) {
        return mem
    }
    
    var ans = LongArray(2) { 0 }
    for (i in 1..3) {
        for (j in 1..3) {
            for (k in 1..3) {
                var newPos = modulo(gameState.positionOne + i + j + k, 10)
                val result = calcPartTwo(GameState(gameState.positionTwo, gameState.scoreTwo, newPos, gameState.scoreOne + newPos))
                ans[0] +=  result.second
                ans[1] +=  result.first
            }
        }
    }
    
    map.put(gameState, Pair(ans[0], ans[1]))
    return Pair(ans[0], ans[1])
}

fun getStepAndDice(dice: Int): Pair<Int, Int> {
    var sum = 0
    var newDice = dice
    for (i in 0 until 3) {
        newDice = modulo(newDice + 1, 100)
        sum += newDice
    }
    return Pair(sum, newDice)
}

private fun modulo(i: Int, mod: Int): Int {
    return (i - 1) % mod + 1
}




