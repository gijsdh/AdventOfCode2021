fun main(args: Array<String>) {
    val input = getResourceAsText("input.txt")
    val inputTest = getResourceAsText("inputExample.txt")
    val inputLines = input.lines()

    var caveMap: HashMap<String, Cave> = HashMap();
    inputLines.map { it.split("-") }.forEach { s ->
        //As we can travel both ways add both.
        addCaveToMap(caveMap, s[0], s[1])
        addCaveToMap(caveMap, s[1], s[0])
    }
    
    //Recursively find all paths and count them. 
    val sum1 = findPath(caveMap, caveMap.get("start")!!, "start", true)
    val sum2 = findPath(caveMap, caveMap.get("start")!!, "start", false)
    
    println("Aswer A= " + sum1)
    println("Aswer B= " + sum2)
}

fun findPath(caveMap: HashMap<String, Cave>, cave: Cave, path: String, isPartOne: Boolean): Int {
    var sum = 0;
    if (cave.name.equals("end")) { 
        return 1
    }
    if (!isValidPath(path, isPartOne)) {
        return 0;
    }

    for (con in cave.connections) {
        if (con != "start") {
            sum += findPath(caveMap, caveMap.get(con)!!, path + "-" + con, isPartOne)
        }

    }
    return sum;
}

fun isValidPath(path: String, isPartOne: Boolean): Boolean {
    //Remove start/end/upperCase 
    val path =
        path.split("-")
            .filter { !it.equals("start") || !it.equals("end") }
            .filter { it.toCharArray()[0].toInt() > 97 } //Filter all capital letters.

    //groupBy occurrences, so we can see if a cave is visited multiple times.
    val groupBY = path.groupingBy { it }.eachCount();
    var count = 0
    for (entry in groupBY.entries) {
        if (entry.value == 2) {
            if (isPartOne) {
                return false
            }
            count++
            if (count > 1) {
                return false
            }
        }
        if (entry.value > 2) {
            return false
        }
    }
    return true
}

private fun addCaveToMap(caveMap: HashMap<String, Cave>, s1: String, s2: String) {
    if (caveMap[s1] != null) {
        caveMap[s1]!!.connections.add(s2)
    } else {
        val cave = Cave(s1)
        cave.connections.add(s2)
        caveMap[s1] = cave
    }
}

//cave with name and amount of connections
class Cave(val name: String) {
    val connections: MutableList<String> = mutableListOf()
    override fun toString(): String {
        return "Cave(name='$name',connections=$connections)"
    }
}