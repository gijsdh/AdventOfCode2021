fun main(args: Array<String>) {
    val input = getResourceAsText("input.txt")
    val inputTest = getResourceAsText("inputExample.txt")
    val inputLines = input.lines()

    val results = inputLines.map { it.split("|").drop(1) }.flatten().map { it.trim().split(" ") }
    val numbers = inputLines.map { it.split("|").take(1) }.flatten().map { it.trim().split(" ") }

    println(results.flatten().count { it.length == 2 || it.length == 7 || it.length == 4 || it.length == 3 })
    
    var sum =0L
    var counter =0
    for (element in numbers) {
        val sorted = element.map { it.toCharArray().sorted().joinToString("") }.sortedWith(compareBy { it.length })
        // println(sorted)
        var map: HashMap<Int, String> = HashMap<Int, String>()

        //These are fixed as we sorted the above. 
        map.put(1, sorted[0])
        map.put(8, sorted[9])
        map.put(4, sorted[2])
        map.put(7, sorted[1])


        //6 -> 6,7,8
        //We here search 
        for (j in 6..8) {
            if (contains(sorted[j],map.get(4).toString())) {
                map.put(9, sorted[j])
            } else if (contains(sorted[j],map.get(1).toString())) {
                map.put(0, sorted[j])
            } else {
                map.put(6, sorted[j])
            }
        }

        val nine = map.get(9)!!
        val eight = map.get(8)!!
        var lineE = 'a';
        for (i in 0..6) {
            if (!nine.contains(eight[i])) {
                lineE = eight[i];
            }
        }

        //5 -> 3,4,5
        for (j in 3..5) {
            if (contains(sorted[j], map.get(1).toString())) {
                map.put(3, sorted[j])
            } else if (sorted[j].contains(lineE)) {
                map.put(2, sorted[j])
            } else {
                map.put(5, sorted[j])
            }
        }
        println(map)

        val result = map.entries.associate { (k, v) -> v to k }
        val sorted2 = results[counter].map { it.toCharArray().sorted().joinToString("") }
        var number = ""
        for (el in sorted2) {
            number += result.get(el);
        }
        sum += number.toLong()
        counter++
    }
    println(sum)
}

fun contains(Input: String, check: String): Boolean {
    for (s in check) {
        if (!Input.contains(s)) {
            return false
        }
    }
    return true
}


//0:      1:      2:      3:      4:
//aaaa    ....    aaaa    aaaa    ....
//b    c  .    c  .    c  .    c  b    c
//b    c  .    c  .    c  .    c  b    c
//....    ....    dddd    dddd    dddd
//e    f  .    f  e    .  .    f  .    f
//e    f  .    f  e    .  .    f  .    f
//gggg    ....    gggg    gggg    ....
//
//5:      6:      7:      8:      9:
//aaaa    aaaa    aaaa    aaaa    aaaa
//b    .  b    .  .    c  b    c  b    c
//b    .  b    .  .    c  b    c  b    c
//dddd    dddd    ....    dddd    dddd
//.    f  e    f  .    f  e    f  .    f
//.    f  e    f  .    f  e    f  .    f
//gggg    gggg    ....    gggg    gggg


// 2: ACDEG     (5) --> verschil tussen 8 9 geeft E, 5 heeft E.
// 3: ACDFG     (5) --> via heeft 1
// 5: ABDFG     (5) --> overige 

// 0: ABCEFG    (6) --> overige
// 6: ABDEFG    (6) --> heeft geen 1 in zich
// 9: ABCDFG    (6) --> heeft 4 in zich

// 1: CF        (2) unique
// 8: ABCDEFG   (7) unique 
// 4: BCDF      (4) unique
// 7: ACF       (3) unique


