fun main(args: Array<String>) {
    val input = getResourceAsText("input.txt")
    val inputTest = getResourceAsText("inputExample.txt")
    val inputLines = input.lines()
    val length =  inputLines.size

    val  numbers =   inputLines[0].split(",").map { it.toInt() }
    val inputmap = inputLines.drop(2)
    
    //Create lis of bingo maps
    var puzzelsHor = inputmap.filter { !it.isEmpty() }.map { it.split(" ").filter { !it.isEmpty() }.map { it.toInt() }}.chunked(5)

    //Rotate the bingo maps, vertical lines are now horizontal. 
    var puzzelsVer = puzzelsHor.map { rotateMatrixRight(it) }

    var count = 0
    val amountOfBoards = puzzelsHor.size

    for (i in 0 until numbers.size) {
        val number = numbers[i]
        
        //filter out the number
        puzzelsHor = puzzelsHor.map { it.map { it.filter { it != number } } }
        puzzelsVer = puzzelsVer.map { it.map { it.filter { it != number } } }
        
        //check for empty line:
        val puzzelBingoHor = returnPuzzleIfEmptyLineInPuzzle(puzzelsHor)
        val puzzelBingoVer = returnPuzzleIfEmptyLineInPuzzle(puzzelsVer)
        
        if (99999 != puzzelBingoHor) {
            count++
            if (count == amountOfBoards) {
                println("Answer B:" + puzzelsHor[puzzelBingoHor].flatten().sum() * number)
                break
            }
            if(count == 1) {
                println("Answer A:" + puzzelsHor[puzzelBingoHor].flatten().sum() * number)
            }
            //filter bingo after won
            puzzelsHor = puzzelsHor.filterIndexed { index, i -> puzzelBingoHor != index }
            puzzelsVer = puzzelsVer.filterIndexed { index, i -> puzzelBingoHor != index }
        }
        
        if (99999 != puzzelBingoVer) {
            count++
            if (count == amountOfBoards) {
                println("Answer B:" + puzzelsVer[puzzelBingoVer].flatten().sum() * number)
                break
            }
            if(count == 1) {
                println("Answer A:" + puzzelsVer[puzzelBingoVer].flatten().sum() * number)
            }
            
            //filter bingo after won
            puzzelsHor = puzzelsHor.filterIndexed { index, i -> puzzelBingoVer != index }
            puzzelsVer = puzzelsVer.filterIndexed { index, i -> puzzelBingoVer != index }
        }
    }
}

fun returnPuzzleIfEmptyLineInPuzzle(puzzelsHor: List<List<List<Int>>>): Int {
    val w = puzzelsHor.size
    val h: Int = puzzelsHor[0].size
    for (i in 0 until w) {
        for (j in 0 until h) {
            if (puzzelsHor[i][j].isEmpty()) {
                return i
            }
        }
    }
    return 99999
}

fun rotateMatrixRight(matrix: List<List<Int>>): List<List<Int>> {
    /* W and H are already swapped */
    val w = matrix.size
    val h: Int = matrix[0].size
    val ret = Array(h) { IntArray(w) }
    for (i in 0 until h) {
        for (j in 0 until w) {
            ret[i][j] = matrix[w - j - 1][i]
        }
    }
    return ret.map { it.toList() }.toList()
}

