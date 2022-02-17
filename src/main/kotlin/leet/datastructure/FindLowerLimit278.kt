package leet.datastructure


fun isBadVersion(version: Int): Boolean = version >= 88

fun firstBadVersion(n: Int): Int {
    var lower = 1
    var upper = n
    var pos = 0
    while (lower <= upper) {
        var newNumber = lower + (upper - lower) / 2
        if (isBadVersion(newNumber)) {
            pos = newNumber
            upper = newNumber - 1
        } else {
            lower = newNumber + 1
        }
    }
    return pos
}

fun main(args: Array<String>) {
    println(firstBadVersion(100))
}

