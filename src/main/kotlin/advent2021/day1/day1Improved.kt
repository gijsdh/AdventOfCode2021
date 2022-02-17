fun main() {
    
    val input = getResourceAsText("input.txt").map { it.toInt() }

    fun part1() = input.windowed(2).count { it.get(1) > it.get(0) }

    fun part2() = input.windowed(3).map { it.sum() }.windowed(2).count { it.get(1) > it.get(0) }
    
    println(part1())
    println(part2())
}