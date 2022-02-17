package day22

import getResourceAsText
import java.lang.Integer.min
import kotlin.math.max

fun main(args: Array<String>) {
    val input = getResourceAsText("input.txt")
    val inputTest = getResourceAsText("inputExample.txt")
    val inputLines = input.lines()

    val operations = inputLines.map { it.split(Regex(" x=|,y=|,z=")) }.map { it.map { it.split("..") } }
    println(operations)

    val rules: MutableList<Rule> = mutableListOf()
    for (operation in operations) {
        if (operation[1][0].toInt() > 50|| operation[2][0].toInt() > 50 ||operation[3][0].toInt() > 50) break
        
        rules.add(
            Rule(
                if (operation[0][0].equals("on")) true else false,
                operation[1][0].toInt() + 50,
                operation[1][1].toInt() + 50,
                operation[2][0].toInt() + 50,
                operation[2][1].toInt() + 50,
                operation[3][0].toInt() + 50,
                operation[3][1].toInt() + 50
            )
        )
    }

    
    
    
    println(rules)

    var lights: Array<Array<BooleanArray>> = Array(100) { Array(100) { BooleanArray(100) { false } } }
    for (rule in rules) {
        for (i in rule.x1..rule.x2) {
            for (j in rule.y1..rule.y2) {
                for (k in rule.z1..rule.z2) {
                    lights[i][j][k] = rule.on
//                    println(lights[i][j][k])
                }
            }
        }
    }
    
    
    
    println(lights.map { it.map { it.toList() }.toList() }.toList().flatten().flatten().count { it })
}


private data class Rule(val on: Boolean, val x1: Int, val x2: Int, val y1: Int, val y2: Int, val z1: Int, val z2: Int) {
    override fun toString(): String {
        return "Rule(on=$on, x1=$x1, x2=$x2, y1=$y1, y2=$y2, z1=$z1, z2=$z2)"
    }
} 








