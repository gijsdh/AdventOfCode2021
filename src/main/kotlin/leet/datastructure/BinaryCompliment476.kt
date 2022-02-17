package leet.datastructure

fun findComplement(num: Int): Int {
    var temp = ""
    println(num.toString(radix = 2))
    for (byte in num.toString(radix = 2)) {
        temp += if (byte == '0') '1' else '0'
    }
    println(temp)
    println( num.inv().toString(2))
    return Integer.parseInt(temp, 2)
}

fun main(args: Array<String>) {
    print(findComplement(123))
}