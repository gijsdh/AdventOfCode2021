

fun main(args: Array<String>) {
    val input = getResourceAsText("input.txt")
    val inputTest = getResourceAsText("inputExample.txt")
    val inputLines = input.lines()
    val length = inputLines.size

    val inputmap = inputLines.map{it.trim().split("")}.map { it.filter { !it.isEmpty() } }.map { it.map { it.toInt() } }
    
    val result = inputmap.fold(List(inputmap[0].size) { 0 }) { x, y -> {}
        x.zip(y, Int::plus)
    }

    val gama = Integer.parseInt(result.map { if (it > length / 2) 1 else 0 }.joinToString(""), 2)
    val epsilon = Integer.parseInt(result.map { if (it < length / 2) 1 else 0 }.joinToString(""), 2) 

    println("aswer1 =" + gama*epsilon)
    
    
    //part 2
    var workMap = inputmap
    var index = 0
    while(workMap.size > 1) {
        val sumArray = workMap.fold(List(inputmap[0].size) { 0 }) { x, y -> {}
            x.zip(y, Int::plus)
        }
        val indexArray = sumArray.map {
            getIndexOxygen(it, workMap)
        }
        workMap = workMap.filter { it[index].equals(indexArray[index])}
        index++
    }

    var workMap2 = inputmap
    var index2 = 0
    while(workMap2.size > 1) {
        val sumArray = workMap2.fold(List(inputmap[0].size) { 0 }) { x, y -> {}
            x.zip(y, Int::plus)
        }
        val indexArray = sumArray.map {
            getIndexCO2(it, workMap2)
        }
        workMap2 = workMap2.filter { it[index2].equals(indexArray[index2])}
        index2++
    }
    
    val oxygen = Integer.parseInt(workMap.flatten().joinToString(""), 2)
    val co2 = Integer.parseInt(workMap2.flatten().joinToString(""), 2)

    println("aswer2 =" + oxygen*co2)
}

private fun getIndexOxygen(
    it: Int,
    workMap: List<List<Int>>
) = if (it > workMap.size / 2) {
    1;
} else if (workMap.size % 2 == 0 && it == workMap.size / 2) {
    1
} else {
    0
}

private fun getIndexCO2(
    it: Int,
    workMap: List<List<Int>>
) = if (it < workMap.size / 2.0) {
    1;
} else if (workMap.size % 2 == 0 && it == workMap.size / 2) {
    0
} else {
    0
}
