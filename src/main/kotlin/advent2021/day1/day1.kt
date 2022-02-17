fun main(args: Array<String>) {
    val input = getResourceAsText("input.txt")
    val inputTest = getResourceAsText("inputExample.txt")
    val inputLines = input.lines()
//    print(input.lines())
    
    val intList = inputLines.map { it.toInt() }
    val newList: MutableList<Int> = mutableListOf()
    for (i in 0..intList.size - 3) {
        newList.add(intList[i] + intList[i+1] + intList[i+2])
    }
    println(newList)


    var oldValue = 999
    println(getAllIncreasinSteps(intList, oldValue))
    println(getAllIncreasinSteps(newList, oldValue))
}

private fun getAllIncreasinSteps(
    intList: List<Int>,
    oldValue: Int,
): Int {
    var oldValue1 = oldValue
    var count= 0
    for (item in intList) {
        if (item > oldValue1) {
            count++
        }
        oldValue1 = item
    }
    return count
}


fun getResourceAsText(path: String): String {
    return object {}.javaClass.getResource(path).readText()
}