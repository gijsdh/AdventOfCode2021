import java.lang.Integer.max
import java.lang.Integer.min

fun main(args: Array<String>) {
    val input = getResourceAsText("input.txt")
    val inputTest = getResourceAsText("inputExample.txt")

    val check = getResourceAsText("check.txt")
    val borders = input.drop(inputTest.indexOf("=") + 1).split(", ").map { it.split("..") }.flatten()
        .map { it.removePrefix("y=") }.map { it.toInt() }
    var checkList = check.lines().map { it.split(" ") }.flatten().filter { !it.isEmpty() }.toMutableList()

    val x1 = min(borders[0], borders[1])
    val x2 = max(borders[0], borders[1])
    val y1 = min(borders[2], borders[3])
    val y2 = max(borders[2], borders[3])
    println("x1= " + x1 + " x2= " + x2)
    println("y1= " + y1 + " y2= " + y2)
    
    var sum = 0
    var HighestRecordedPoint = 0
    for (i in 0..x2) { // cannot go faster then x2 in x-> direction.
        for (j in y1..500) { // cannot go faster then y1 in y-> direction.
            var velocity = Pair(i, j)
            var highPoint = 0
            var pos = Pair(0, 0)

            while (pos.second > y1) {
                pos = Pair(pos.first + velocity.first, pos.second + velocity.second)
                if (pos.second > highPoint) { 
                    highPoint = pos.second
                }
                if ((pos.first >= x1 && pos.first <= x2) && (pos.second >= y1 && pos.second <= y2)) {
                    sum += 1
                    if (highPoint > HighestRecordedPoint) {
                        HighestRecordedPoint = highPoint
                    }
                    break
                }
                
                val xVelocity = when {
                    velocity.first > 0 -> velocity.first - 1
                    velocity.first == 0 -> velocity.first
                    else -> velocity.first
                }
                velocity = Pair(xVelocity, velocity.second - 1)
            }
        }
    }
    
    println(HighestRecordedPoint)
    println(sum)
}

