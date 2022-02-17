import java.lang.Math.abs

fun main(args: Array<String>) {
    val input = getResourceAsText("input.txt")
    val inputTest = getResourceAsText("inputExample.txt")
    val inputLines = input.lines()

    val numbers = inputLines[0].split(",").map { it.toInt() }

    val median =numbers.sorted()[numbers.sorted().size/2]
    println(median)
    println("analytical solution part 1 =" + numbers.map { abs(it - median) }.sum())
    val max = numbers.maxOrNull() ?: -1

    val part2 = {n: Int ->  (n * (n + 1)) / 2 }
    val part1 = {n: Int ->  n}
    println("part 1= " + getMinFuel(max, numbers, part1))
    println("part 2= " + getMinFuel(max, numbers, part2))
}

private fun getMinFuel(max: Int, numbers: List<Int>, lmbd: (Int) -> Int): Long {
    var sum = 0L
    var min = 999999999L
    for (j in 1..max) {
        for (element in numbers) {
            val n = abs(j - element)
            sum += lmbd(n)
        }
        if (min > sum) {
            min = sum
        }
        sum = 0
    }
    return min
}
