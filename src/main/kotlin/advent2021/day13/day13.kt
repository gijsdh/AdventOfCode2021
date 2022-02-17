fun main(args: Array<String>) {
    val input = getResourceAsText("input.txt")
    val inputTest = getResourceAsText("inputExample.txt")
    val inputLines = input.lines()

    val dots = inputLines.filter { !it.startsWith("fold") }.filter { !it.isEmpty() }.map { it.trim().split(",").map { it.toInt() } }
    val folds = inputLines.filter { it.startsWith("fold") }.filter { !it.isEmpty() }.map { it.removePrefix("fold along ").split("=") }

    println(dots)
    println(folds)

    val maxX = dots.map { it.take(1) }.flatten().toIntArray().maxOrNull() ?: 0
    val maxY = dots.map { it.drop(0) }.flatten().toIntArray().maxOrNull() ?: 0

    var dotsMap = Array(maxY+1) { CharArray(maxX+1){'.'} }
    for (elements in dots) {
        dotsMap[elements[1]][elements[0]] = '#'
    }
    //printMap(dotsMap)
    for (fold in folds) {
        if (fold[0].equals("y")) {
            dotsMap = foldMapY(dotsMap, fold[1].toInt())
        } else {
            dotsMap = foldMapX(dotsMap, fold[1].toInt())
        }
        //    printMap(dotsMap)
    }
    printMap(dotsMap)
    println(dotsMap.map { it.count{it.equals('#')} }.sum())

}

fun foldMapY(dotsMap: Array<CharArray>, size: Int): Array<CharArray> {
    val newMap = Array(size) { CharArray(dotsMap[0].size) { '.' } }
    for (i in newMap.indices) {
        for (j in newMap[0].indices) {
            if (dotsMap[dotsMap.size - i - 1][j] == '#' || dotsMap[i][j] == '#') {
                newMap[i][j] = '#'
            }
        }
    }
    return newMap
}

fun foldMapX(dotsMap: Array<CharArray>, size: Int): Array<CharArray> {
    val newMap = Array(dotsMap.size) { CharArray(size) { '.' } }
    for (i in newMap.indices) {
        for (j in newMap[0].indices) {
            if (dotsMap[i][dotsMap[0].size - j - 1] == '#' || dotsMap[i][j] == '#') {
                newMap[i][j] = '#'
            }
        }
    }
    return newMap
}

private fun printMap(numbers: Array<CharArray>) {
    for (el in numbers) {
        println(el.toList())
    }
    println("--------------------------------------------------------------------------")
}


