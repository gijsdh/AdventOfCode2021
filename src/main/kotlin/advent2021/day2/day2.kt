fun main(args: Array<String>) {
    val input = getResourceAsText("input.txt")
    val inputTest = getResourceAsText("inputExample.txt")
    val inputLines = input.lines()

    val inputmap = inputLines.map{it.split(" ")}.map{(first, second)-> Pair(first,second.toInt())}

    var horizontal = 0
    var vertical = 0
    var aim = 0
    
    inputmap.forEach { (operation, number) ->
        when (operation) {
            "forward" -> horizontal += number;
            "up" -> vertical -= number;
            "down" -> vertical += number;
        }
    }
    println(horizontal*vertical)

    val pair = pair(inputmap, 0, 0, 0)
    println(pair.first*pair.second)
    
}

private fun pair(
    inputmap: List<Pair<String, Int>>,
    horizontal: Int,
    vertical: Int,
    aim: Int
): Pair<Int, Int> {
    var horizontal1 = horizontal
    var vertical1 = vertical
    var aim1 = aim
    inputmap.forEach { (operation, number) ->
        when (operation) {
            "forward" -> {
                horizontal1 += number;
                vertical1 += number * aim1
            }
            "up" -> aim1 -= number;
            "down" -> aim1 += number;
        }
    }
    return Pair(horizontal1, vertical1)
}
