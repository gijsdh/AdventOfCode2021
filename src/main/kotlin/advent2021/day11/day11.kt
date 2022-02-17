fun main(args: Array<String>) {
    val input = getResourceAsText("input.txt")
    val inputTest = getResourceAsText("inputExample.txt")
    val inputLines = input.lines()
    
    var numbers = inputLines.map { it.split("").filter { it.isNotEmpty() }.map { it.toInt() }.toIntArray()}.toTypedArray()

    val cells =  numbers.size*numbers[0].size

    var sum = 0
    for (i in 1 until 1000) {
        numbers = numbers.map { it.map { it + 1 }.toIntArray() }.toTypedArray()
        var index = findNewExplosion(numbers);
        var sumPart = 0
        while (!index.equals(Pair(-1, -1))) {
            numbers[index.first][index.second] = 0
            sumPart += checkNeighbours(numbers, index.first, index.second)
            index = findNewExplosion(numbers);
        }
        if (cells == sumPart) {
            println("Answer B =" + i)
            break;
        }
        sum += sumPart
       // printNumber(numbers)
    }
    print("Answer A =" + sum)
}

private fun printNumber(numbers: Array<IntArray>) {
    for (el in numbers) {
        println(el.toList())
    }
    println("--------------------------------------------------------------------------")
}

fun findNewExplosion(numbers: Array<IntArray>): Pair<Int,Int> {
    for (i in numbers.indices) {
        for (j in numbers[0].indices) {
            if (numbers[i][j] == 10) {
                return Pair(i, j)
            }
        }
    }
    return Pair(-1, -1)
}

// check neighbours and recursively handle any chain reactions
private fun checkNeighbours(numbers: Array<IntArray>, i: Int, j: Int): Int {
    val ith = intArrayOf(0, 1, -1, 0, -1, 1, 1, -1)
    val jth = intArrayOf(1, 0, 0, -1, -1, 1, -1, 1)
    val number = numbers[i][j]
    var sum = 1;
    for (k in ith.indices) {
        val index_I = i + ith[k]
        val index_J = j + jth[k]
        if (isValidIndex(index_I, index_J, numbers.size, numbers[0].size) && numbers[index_I][index_J] != 0) {
            numbers[index_I][index_J]++
            if( numbers[index_I][index_J] >= 10) {
                numbers[index_I][index_J] = 0
                sum += checkNeighbours(numbers, index_I, index_J)
            }
        }
    }
    return sum
}

private fun isValidIndex(i: Int, j: Int, l: Int, k: Int): Boolean {
    if (i < 0 || j < 0 || i >= l || j >= k) return false
    return true
}