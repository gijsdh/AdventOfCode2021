fun main(args: Array<String>) {
    val input = getResourceAsText("input.txt")
    val inputTest = getResourceAsText("inputExample.txt")
    val inputLines = input.lines()

    var  numbers =   inputLines[0].split(",").map { it.toInt() }.toMutableList()
    var array = LongArray(10)

    
    numbers.forEach { s -> array[s]++ }

    println(getFishies(array, 79).drop(1).sum())
    println(getFishies(array, 255).drop(1).sum())


}

private fun getFishies(array: LongArray, day : Int): LongArray {
    var array1 = array
    for (i in 1..day) {
        var list = array1.drop(1).toMutableList()
        list.add(0)
        array1 = list.toLongArray()
        array1[9] = array1[0]
        array1[7] += array1[0]
    }
    return array1
}

