package day22

import getResourceAsText
import java.lang.Integer.min
import kotlin.math.max

fun main(args: Array<String>) {
    val input = getResourceAsText("input.txt")
    val inputTest = getResourceAsText("inputExample.txt")
    val inputLines = input.lines()

    val operations = inputLines.map { it.split(Regex(" x=|,y=|,z=")) }.map { it.map { it.split("..") } }
    val rules: MutableList<RuleTwo> = mutableListOf()
    
    var X: MutableList<Long> = mutableListOf()
    var Y: MutableList<Long> = mutableListOf()
    var Z: MutableList<Long> = mutableListOf()
    for (operation in operations) {
        val x1 = operation[1][0].toLong()
        val x2 = operation[1][1].toLong() + 1 
        val y1 = operation[2][0].toLong()
        val y2 = operation[2][1].toLong() + 1
        val z1 = operation[3][0].toLong()
        val z2 = operation[3][1].toLong() + 1
        val on = if (operation[0][0].equals("on")) true else false
        rules.add(RuleTwo(on, x1, x2, y1, y2, z1, z2))
        X.add(x1)
        X.add(x2)
        Y.add(y1)
        Y.add(y2)
        Z.add(z1)
        Z.add(z2)
    }
    
    X.sort()
    Y.sort()
    Z.sort()
    
    val N = X.size
    var lights: Array<Array<BooleanArray>> = Array(N) { Array(N) { BooleanArray(N) { false } } }
    
    for (rule in rules) {
        val x1 = X.binarySearch(rule.x1)
        val x2 = X.binarySearch(rule.x2)
        val y1 = Y.binarySearch(rule.y1)
        val y2 = Y.binarySearch(rule.y2)
        val z1 = Z.binarySearch(rule.z1)
        val z2 = Z.binarySearch(rule.z2)
        
        for (i in x1 until x2) {
            for (j in y1 until y2) {
                for (k in z1 until z2) {
                    lights[i][j][k] = rule.on
                }
            }
        }
    }

    var sum = 0L
    for (i in 0 until N - 1) {
        for (j in 0 until N - 1) {
            for (k in 0 until N - 1) {
                if (lights[i][j][k]) sum += (X[i + 1] - X[i]) * (Y[j + 1] - Y[j]) * (Z[k + 1] - Z[k])
            }
        }
    }
    println(sum)
}


private data class RuleTwo(val on: Boolean, val x1: Long, val x2: Long, val y1: Long, val y2: Long, val z1: Long, val z2: Long) {
    override fun toString(): String {
        return "Rule(on=$on, x1=$x1, x2=$x2, y1=$y1, y2=$y2, z1=$z1, z2=$z2)"
    }
} 





