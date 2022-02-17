import java.util.Collections.max
import java.util.Collections.min

fun main(args: Array<String>) {
    val input = getResourceAsText("input.txt")
    val inputTest = getResourceAsText("inputExample.txt")
    val inputLines = input.lines()

    var inputText = inputLines[0]
    val operations = inputLines.drop(2).map { it.trim().split("->").map { it.trim() } }

    println(inputText)
    println(operations)

    for (i in 0 until 10) {
        var workArray = CharArray(inputText.length)
        for (operation in operations) {
            var index = inputText.indexOf(operation[0]);
            while (index != -1) {
                workArray[index] = operation[1].toCharArray()[0]
                index = inputText.indexOf(operation[0], index + 1);
            }
        }
        var count = 0
        // Concatenating the new string
        for (i in workArray.indices) {
            if (workArray[i].toInt() != 0) {
                val index = i + count + 1
                inputText = inputText.substring(0, index) + workArray[i] + inputText.substring(index, inputText.length)
                count++
            }
        }
    }
    val entries = inputText.trim().split("").filter { it.isNotEmpty() }.groupingBy { it }.eachCount().values
    println(max(entries) - min(entries))
    //------------------------------------------------------------------ Part 2, trying to be smart
   
    inputText = inputLines[0]
    val sum: (x: Long, y: Long) -> Long = { x, y -> x + y }

    //Initial pairs in start string
    var letterPairMap: HashMap<String, Long> = HashMap();
    for (i in 0 until inputText.length - 1) {
        letterPairMap.merge(inputText[i] + inputText[i + 1].toString(), 1L, sum)
    }
    println(letterPairMap)


    for (i in 0 until 40) {
        var workMap: HashMap<String, Long> = HashMap();
        for (operation in operations) {
            //if the current map has the operation -> operation = ab -> c  then   map{ac} = oldmap[operation] + map{ac} and  map{cb} = oldmap[operation] + map{cb}
            if (letterPairMap.contains(operation[0])) {
                workMap.merge(operation[0][0] + operation[1][0].toString(), letterPairMap.get(operation[0])!!, sum)
                workMap.merge(operation[1][0] + operation[0][1].toString(), letterPairMap.get(operation[0])!!, sum)
            }
        }
        letterPairMap = workMap;
        println(workMap)
    }

    var letterMap: HashMap<String, Long> = HashMap();
    //Count letters in letter pairs always take the first as it is present in second one.
    for (entry in letterPairMap.entries) {
        letterMap.merge(entry.key[0].toString(), entry.value, { x, y -> x + y })
    }
    //add last letter.
    letterMap.merge(inputText[inputText.length - 1].toString(), 1, { x, y -> x + y })

    println(max(letterMap.values) - min(letterMap.values))
}




