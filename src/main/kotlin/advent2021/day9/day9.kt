fun main(args: Array<String>) {
    val input = getResourceAsText("input.txt")
    val inputTest = getResourceAsText("inputExample.txt")
    val inputLines = input.lines()

    val numbers = inputLines.map { it.trim().split("").filter { !it.isEmpty() }.map { it.toInt() } }
    println(numbers)

    
    var results =  mutableListOf<Long>()
    var sum = 0L
    for (i in 0 until numbers.size) {
        for (j in 0 until numbers[0].size) {
            if (checkNeighbours(numbers, i, j)) {
                sum += numbers[i][j] + 1
                var set: MutableSet<Pair<Int,Int>> = mutableSetOf()
                val sizeBasin = findbasinSize(numbers, i, j,set).toLong()
                results.add(sizeBasin)
            }
        }
    }

    println(sum)
    print(results.sorted().reversed().take(3).reduce{i, j -> i*j})
    
}

// Recursively find neighbours with higher values, the set is to make sure we do not count places twice. 
private fun findbasinSize(numbers: List<List<Int>>, i: Int, j: Int, set: MutableSet<Pair<Int, Int>>): Int {
    val ith = intArrayOf(0, 1, -1, 0)
    val jth = intArrayOf(1, 0, 0, -1)
    val number = numbers[i][j]
    var sum = 1
    for (k in 0 until 4) {
        val index_I = i + ith[k]
        val index_J = j + jth[k]
        if (isValidIndex(index_I, index_J, numbers.size, numbers[0].size) && numbers[index_I][index_J] != 9 && !set.contains(Pair(index_I, index_J))) {
            if (number < numbers[index_I][index_J] ) {
                set.add(Pair(index_I, index_J))
                sum += findbasinSize(numbers, index_I, index_J, set)
            }
        }
    }
    return sum
}

//Check if neighbours are all bigger. 
private fun checkNeighbours(numbers: List<List<Int>>, i: Int, j: Int): Boolean {
    val ith = intArrayOf(0, 1, -1, 0)
    val jth = intArrayOf(1, 0, 0, -1)
    val number = numbers[i][j]
    for (k in 0 until 4) {
        val index_I = i + ith[k]
        val index_J = j + jth[k]
        if (isValidIndex(index_I, index_J, numbers.size, numbers[0].size)) {
            //   println("" + index_I + " " + index_J + " " + number)
            if (number >= numbers[index_I][index_J]) {
                return false
            }
        }
    }
    return true
}


private fun isValidIndex(i: Int, j: Int, l: Int, k: Int): Boolean {
    if (i < 0 || j < 0 || i >= l || j >= k) return false
    return true
}