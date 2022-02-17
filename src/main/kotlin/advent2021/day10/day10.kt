fun main(args: Array<String>) {
    val input = getResourceAsText("input.txt")
    val inputTest = getResourceAsText("inputExample.txt")
    val inputLines = input.lines()
    val list = inputLines.map { it.split("").filter { it.isNotEmpty() }.map { it }.toMutableList() }.toMutableList()
    
    var points: HashMap<String, Long> = HashMap<String, Long>()
    points.put(")", 3L)
    points.put("]", 57L)
    points.put("}", 1197L)
    points.put(">", 25137L)

    var points2: HashMap<String, Long> = HashMap<String, Long>()
    points2.put("(", 1L)
    points2.put("[", 2L)
    points2.put("{", 3L)
    points2.put("<", 4L)

    var CloseToOpening: HashMap<String, String> = HashMap<String, String>()
    CloseToOpening.put(")", "(")
    CloseToOpening.put("]", "[")
    CloseToOpening.put("}", "{")
    CloseToOpening.put(">", "<")
    
    var sum = 0L
    var results: MutableList<Long> = mutableListOf<Long>();
    for (el in list) {
        var index = findClosingCharIndex(el)
        var calcClosingCharacters = true
        while (index != -1) {
            val char = el[index]
            if (el[index - 1].equals(CloseToOpening.get(char))) {
                el.removeAt(index)
                el.removeAt(index - 1)
            } else {
                sum += points.get(char) ?: 0L
                calcClosingCharacters = false
                break
            }
            index = findClosingCharIndex(el)
        }

        if (calcClosingCharacters) {
            var index2 = findOpeningCharIndex(el)
            var sum2 = 0L
            while (index2 != -1) {
                val char = el[index2]
                sum2 = sum2 * 5 + (points2.get(char) ?: 0L)
                el.removeAt(index2)
                index2 = findOpeningCharIndex(el)
            }
            results.add(sum2)
        }
    }
    println(sum)
    println(results.sorted().get(results.size / 2))
}

fun findOpeningCharIndex(list: MutableList<String>): Int {
    val charSetClose = setOf<String>("(", "[", "{", "<")
    for (i in list.size - 1 downTo 0) {
        if (charSetClose.contains(list[i])) {
            return i
        }
    }
    return -1
}

fun findClosingCharIndex(list: MutableList<String>): Int {
    val charSetClose = setOf<String>(")", "]", "}", ">")
    for (i in 0 until list.size) {
        if (charSetClose.contains(list[i])) {
            return i
        }
    }
    return -1
}
