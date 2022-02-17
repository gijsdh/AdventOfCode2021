import java.util.Collections.max
import java.util.Collections.min

fun main(args: Array<String>) {
    val input = getResourceAsText("input.txt")
    val inputTest = getResourceAsText("inputExample.txt")
    val inputLines = input
    
    var number =  ""
    for (el in inputLines) {
        number += toBinaryString(el)

    }
    val sum = Sum(0) 
    println(handleNumber(number, 0, sum, 0).second)
    println(sum.total)
}


fun handleNumber(bits: String, i: Int, sum: Sum, depth: Int): Pair<Int, Long> {
    val version = bits.substring(i, i + 3).toInt(2)
    val id = bits.substring(i + 3, i + 6).toInt(2)
    sum.total += version
    var returnIndex = 0
    
    //Store partial results
    val list = mutableListOf<Long>()

    if (id == 4) {
        var index = i + 6
        var value = ""
        do {
            value += bits.substring(index + 1, index + 5)
            index += 5
        } while (!bits.get(index - 5).equals('0'))
        return Pair(index, value.toLong(2))
    } else {
        val lengthTypeID = bits.substring(i + 6, i + 7).toInt(2)
        //Determine subPackets
        if (lengthTypeID == 0) {
            val lenghtOfPackagesInBits = bits.substring(i + 7, i + 22).toInt(2)
            var start_i = i + 22
            var index = start_i
            do {
                val pair = handleNumber(bits, index, sum, depth + 1);
                list.add(pair.second)
                index = pair.first
            } while (index - start_i != lenghtOfPackagesInBits)
            returnIndex = index
        } else {
            val nPackets = bits.substring(i + 7, i + 18).toInt(2)
            var index = i + 18
            for (i in 0 until nPackets) {
                val pair = handleNumber(bits, index, sum, depth + 1);
                list.add(pair.second)
                index = pair.first
            }
            returnIndex = index
        }
    }
    
    //Calculate result
    when (id) {
        0 -> return Pair(returnIndex, list.sum())
        1 -> return Pair(returnIndex, list.foldRight(1, { i, j -> i * j }))
        2 -> return Pair(returnIndex, min(list))
        3 -> return Pair(returnIndex, max(list))
        5 -> return Pair(returnIndex, if (list[0] > list[1]) 1 else 0)
        6 -> return Pair(returnIndex, if (list[0] < list[1]) 1 else 0)
        7 -> return Pair(returnIndex, if (list[0] == list[1]) 1 else 0)
        else -> assert(false)
    }
    assert(false)  // should be here.
    return Pair(returnIndex, 0)
}

private fun toBinaryString(el: Char): String = el.toString().toInt(16).toString(2).padStart(4, '0')

class Sum(total: Int) {
    var total = 0;
}
