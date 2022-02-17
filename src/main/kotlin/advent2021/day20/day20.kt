fun main(args: Array<String>) {
    val input = getResourceAsText("input.txt")
    val inputTest = getResourceAsText("inputExample.txt")
    val inputLines = input.lines()
    
    
    // move to 0,1 immediately. 
    val binaryArray = inputLines.take(1).get(0).toCharArray().map { if(it == '.') '0' else '1' }; 

    var inputArray =
        inputLines.drop(2).map {
            it.split("")
                .filter { it.isNotEmpty() }
                .map { it.trim() }
                .map { if (it[0] == '.') '0' else '1' }.toCharArray()
        }
            .toTypedArray()
    
    
    // As the binaryArray[0] = 1 and binaryArray[255] = 0. The spaces moves from all 0 -> 1 and back again.  
    // So for copying array we need to make sure the background is correct. But also for indexes out of bounds.
    
    for (i in 0..49) {
        var pixel = (i % 2).digitToChar()
        println(pixel)
        var input = copyArray(inputArray, pixel)
        var output = Array(input.size) { CharArray(input[0].size) { pixel } }

        for (i in output.indices) {
            for (j in output[0].indices) {
                output[i][j] = binaryArray[getIndexThroughNeighbours(input, i, j, pixel)]
            }
        }
        inputArray = output;
    } 

    println(inputArray.map { it.toList() }.toList().flatten().count { it == '1' })
    
}

private fun println(image: Array<CharArray>) {
    println(image.map { it.toList() }.toList())
}

private fun copyArray(image: Array<CharArray>, pixel : Char): Array<CharArray> {
    val output = Array(image.size + 2) { CharArray(image[0].size + 2) { pixel } }
    for (i in 1 until output.size - 1) {
        for (j in 1 until output[0].size - 1) {
            output[i][j] = image[i - 1][j - 1]
        }
    }
    return output
}


private fun getIndexThroughNeighbours(image: Array<CharArray>, i: Int, j: Int, pixel : Char): Int {
    val ith = intArrayOf(-1, -1, -1, 0, 0, 0, 1, 1, 1)  // 0, 1, 2   as 0,0 is top left up = -1 and left is -1
    val jth = intArrayOf(-1, 0, 1, -1, 0, 1, -1, 0, 1)  // 3, 4, 5
    var sum = ""                                        // 6, 7, 9
    for (k in ith.indices) {
        val index_I = i + ith[k]
        val index_J = j + jth[k]
        if (isValidIndex(index_I, index_J, image.size, image[0].size)) {
            sum += image[index_I][index_J]
        } else {
            sum += pixel // out of bounds depends on round, could either be 0,1.
        }
    }
    
    return sum.toInt(2)
}

private fun isValidIndex(i: Int, j: Int, l: Int, k: Int): Boolean {
    if (i < 0 || j < 0 || i >= l || j >= k) return false
    return true
}