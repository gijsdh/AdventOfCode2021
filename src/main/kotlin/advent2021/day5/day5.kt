fun main(args: Array<String>) {
    val input = getResourceAsText("input.txt")
    val inputTest = getResourceAsText("inputExample.txt")
    val inputLines = input.lines()


    val lines = inputLines.map { it.split("->") }.map { it.map { it.trim().split(",").map { it.toInt() } }.flatten() }

    //find max number
    val max =
        lines.map { it.reduceRight { i, j -> if (i > j) i else j } }.reduceRight { i, j -> if (i > j) i else j } + 1

    var result = Array(max) { IntArray(max) }
    var result2 = Array(max) { IntArray(max) }

    for (i in 0 until lines.size) {
        val x1 = lines[i][0]
        val x2 = lines[i][2]

        val y1 = lines[i][1]
        val y2 = lines[i][3]


        //HANDLE VERTICAL LINES
        if (x1 == x2) {
            var arrayY = if (y1 < y2) IntRange(y1, y2) else y1 downTo y2
            for(j in arrayY) {
                result[j][x1]++
                result2[j][x1]++
            }
        }

        //HANDLE HORIZONTAL LINES
        if (y1 == y2) {
            var arrayX = if (x1 < x2) IntRange(x1, x2) else x1 downTo x2
            for(j in arrayX) {
                result[y1][j]++
                result2[y1][j]++
            }
        }

        //HANDLE DIAGONAL LINES
        if (y1 != y2 && x1 != x2) {
            if (slopeIsOne(x1, x2, y1, y2)) {
                var rangeX = if (x1 < x2) IntRange(x1, x2) else x1 downTo x2
                var rangeY = if (y1 < y2) IntRange(y1, y2) else y1 downTo y2
                val iterX = rangeX.iterator()
                val iterY = rangeY.iterator()
                while(iterX.hasNext()) {
                    result2[iterY.nextInt()][iterX.nextInt()]++
                }
            }
        }

    }

    //calculate all sqaures where more then 2 lines crossed.
    println(result.map { it.toList().filter { it > 1 } }.flatten().size)
    println(result2.map { it.toList().filter { it > 1 } }.flatten().size)
}

fun slopeIsOne(x1: Int, x2: Int, y1: Int, y2: Int): Boolean {
    val slope = ((y2 - y1) / (x2 - x1)).toDouble()
    return slope == 1.0 || slope == -1.0
}


