import java.lang.Long.min
import java.util.*

fun main(args: Array<String>) {
    val input = getResourceAsText("input.txt")
    val inputTest = getResourceAsText("inputExample.txt")
    val inputLines = input.lines()

    //Let implement dijkstra algorithm:
    val maze = inputLines.map { it.split("").filter { !it.isEmpty() }.map { it.toLong() }.toLongArray() }.toTypedArray()
    //println(maze)
    findMinimumCostPath(maze)
    var mazePartTwo = Array(maze.size * 5) { LongArray(maze[0].size * 5) { 0 } }

    for (i in mazePartTwo.indices) {
        for (j in mazePartTwo[0].indices) {
            mazePartTwo[i][j] = (maze[i % maze.size][j % maze[0].size] + i / maze.size + j / maze[0].size)
            if (mazePartTwo[i][j] > 9) {
                mazePartTwo[i][j] = mazePartTwo[i][j] % 9
            }
        }
    }
    findMinimumCostPath(mazePartTwo)
}

private fun findMinimumCostPath(maze: Array<LongArray>) {
    var visted = Array(maze.size) { BooleanArray(maze[0].size) { false } }
    var costMap = Array(maze.size) { LongArray(maze[0].size) { Long.MAX_VALUE } }

    val comparByCost: Comparator<Cost> = compareBy { it.costValue }
    val costQueue = PriorityQueue<Cost>(comparByCost)

    costQueue.add(Cost(0, 0, 0))
    costMap[0][0] = 0L

    while (costQueue.isNotEmpty()) {
        val cell: Cost = costQueue.remove()
        val i: Int = cell.index_i
        val j: Int = cell.index_j

        if (visted[i][j]) continue
        visted[i][j] = true

        val ith = intArrayOf(0, 1, -1, 0)
        val jth = intArrayOf(1, 0, 0, -1)

        for (k in ith.indices) {
            val index_I = i + ith[k]
            val index_J = j + jth[k]
            if (isValidIndex(index_I, index_J, maze.size, maze[0].size) && !visted[index_I][index_J]) {
                costMap[index_I][index_J] =
                    min(costMap[index_I][index_J], costMap[i][j] + maze[index_I][index_J])
                costQueue.add(Cost(index_I, index_J, costMap[index_I][index_J]))
            }
        }
    }
    println(costMap[costMap.size - 1][costMap[0].size - 1])
}

private fun printNumber(numbers: Array<LongArray>) {
    for (el in numbers) {
        println(el.toList())
    }
    println("--------------------------------------------------------------------------")
}
private fun checkNeighbours(numbers: Array<IntArray>, i: Int, j: Int): Int {
    val ith = intArrayOf(0, 1, -1, 0)
    val jth = intArrayOf(1, 0, 0, -1)
    val number = numbers[i][j]
    var sum = 1;
    for (k in ith.indices) {
        val index_I = i + ith[k]
        val index_J = j + jth[k]
        if (isValidIndex(index_I, index_J, numbers.size, numbers[0].size)) {
      
        }
    }
    return sum
}

private fun isValidIndex(i: Int, j: Int, l: Int, k: Int): Boolean {
    if (i < 0 || j < 0 || i >= l || j >= k) return false
    return true
}
class Cost(val index_i: Int, val index_j: Int, val costValue: Long) {
    
}
