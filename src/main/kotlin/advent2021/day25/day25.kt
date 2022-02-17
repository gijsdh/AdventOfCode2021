package day25

import getResourceAsText

fun main(args: Array<String>) {
    val input = getResourceAsText("input.txt")
    val inputTest = getResourceAsText("inputExample.txt")
    var grid = input.lines().map { it.trim().split("").filter { it.isNotEmpty() }.map { it[0] }.toCharArray()}.toTypedArray()



    print(grid)

    val sizeI = grid.size
    val sizeJ = grid[0].size

    var steps = 0
    while (true) {
        steps++
        var count = 0
        var newGrid = Array(sizeI) { CharArray(sizeJ) { '#' } }
        for (i in 0 until sizeI) {
            for (j in 0 until sizeJ) {
                val index_j = index(j, sizeJ)
                if (grid[i][j] == '.' && grid[i][index_j] == '>') {
                    count++
                    newGrid[i][index_j] = '.'
                    newGrid[i][j] = '>'
                } else if (newGrid[i][j] == '#') {
                    newGrid[i][j] = grid[i][j]
                }
            }
        }
  
        grid = newGrid.clone()
        newGrid = Array(sizeI) { CharArray(sizeJ) { '#' } }

        for (i in 0 until sizeI) {
            for (j in 0 until sizeJ) {
                val index_i = index(i, sizeI)
                if (grid[i][j] == '.' && grid[index_i][j] == 'v') {
                    count++
                    newGrid[index_i][j] = '.'
                    newGrid[i][j] = 'v'
                } else if (newGrid[i][j] == '#') {
                    newGrid[i][j] = grid[i][j]
                }
            }
        }
        println(count)
        if (count == 0) break
        grid = newGrid
    }
    println(steps)
}

private fun index(j: Int, sizeJ: Int) = if (j == 0) sizeJ - 1 else j - 1


fun print(map: Array<CharArray>) {
    for(a in map) {
        println(a.joinToString(""))
    }
    println("______________________________________")
}