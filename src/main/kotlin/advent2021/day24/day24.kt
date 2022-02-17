package day24

import getResourceAsText

fun main(args: Array<String>) {
    val input = getResourceAsText("input.txt")
    val inputTest = getResourceAsText("inputExample.txt")
    val orders = inputTest.lines().map { it.split(" ") }.map { it.map { it.trim() } }
    
    
//////////////////???????????????ARGGGGGGGGGGGGGGGGGGGG

//    println(orders)
//    
//    println(numberIsInCorrect(9,orders))

    var number = 9L
//    var list = mutableListOf<Long>()
//    while (number > 0) {
//        list.add(numberIsInCorrect(number, orders, 0))
//        number--
//    }
//    println(list)


    val list = mutableListOf<Long>(15, 14, 13, 12, 11, 10, 9, 8, 7)
    number = 9L
    var list2 = mutableListOf<Long>()
    while (number > 0) {
        for (num in list) list2.add(numberIsInCorrect(num, orders, num))
        number--
    }
//
    println(list2)
//    number = 9L
//    var list3 = mutableListOf<Long>()
//    while (number > 0) {
//        for (num in list2) list3.add(numberIsInCorrect(number, orders, num))
//        number--
//    }
//    println(list3)
//
//    number = 9L
//    var list4 = mutableListOf<Long>()
//    while (number > 0) {
//        for (num in list3) list4.add(numberIsInCorrect(number, orders, num))
//        number--
//    }
//    println(list4)


//    inp a - Read an input value and write it to variable a.
//    add a b - Add the value of a to the value of b, then store the result in variable a.
//    mul a b - Multiply the value of a by the value of b, then store the result in variable a.
//    div a b - Divide the value of a by the value of b, truncate the result to an integer, then store the result in variable a. (Here, "truncate" means to round the value toward zero.)
//    mod a b - Divide the value of a by the value of b, then store the remainder in variable a. (This is also called the modulo operation.)
//    eql a b - If the value of a and b are equal, then store the value 1 in variable a. Otherwise, store the value 0 in variable a.
    
    
}

fun numberIsInCorrect(number: Long, orders: List<List<String>>, z: Long): Long {
    val map: HashMap<String, Long> = hashMapOf("w" to 0L, "x" to 0L, "y" to 0L, "z" to z)

   
    val value = number.toString();
    var count = 0
    for (order in orders) {
        val valueOne = map[order[1]]!!.toLong()
        if (order[0] == "inp") {
            map[order[1]] = value[count].toString().toLong()
            count++
            continue
        }

        val valueTwo = map[order[2]] ?: order[2].toLong()
        if (order[0] == "add") {
            map[order[1]] = valueOne + valueTwo
        } else if (order[0] == "mul") {
            map[order[1]] = valueOne * valueTwo
        } else if (order[0] == "div") {
            map[order[1]] =
                if (valueTwo != 0L) valueOne / valueTwo else continue
        } else if (order[0] == "mod") {
            if (valueOne <= 0 || valueTwo < 0) continue
            map[order[1]] = valueOne % valueTwo
        } else if (order[0] == "eql") {
            map[order[1]] = if (valueOne == valueTwo) 1 else 0
        } else {
            println(order[0])
            assert(false)
        }
    }

    return map["z"]!!
}
