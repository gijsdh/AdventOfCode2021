
import java.lang.Math.abs
import kotlin.collections.HashMap

fun main(args: Array<String>) {
    val input = getResourceAsText("input.txt")
    val inputTest = getResourceAsText("inputExample.txt")

    val scannperMap = input.split(Regex("--- scanner [0-9]{1,2} ---"))
        .filter { it.isNotEmpty() }
        .map { it.trim() }
        .map { it.split("\r\n") }.map{
            it.map { val temp = it.split(",")
                Triple(temp[0].toInt(),temp[1].toInt(),temp[2].toInt())}}

    // Found all rotation online, read the rotation matrices from a file. 
    val rotations = getResourceAsText("rotattions.txt").split("--") //split on --
        .filter { it.isNotEmpty() }
        .map { it.split("\r\n") }  //split new lines
        .map { it.filter { !it.isBlank() } }
        .map { it.map { it.split("\t").map { it.toInt() } } } //split on tabs and transform to Int

    val referenceScanner = scannperMap[0].toMutableSet()
    val scanners = scannperMap.drop(1).toMutableSet()

    val locationScanners = mutableListOf<Triple<Int,Int,Int>>()

    while (scanners.isNotEmpty()) {
        var stop = false
        for (scanner in scanners) {
            for (i in rotations.indices) { // --> try al rotations.
                val rotatedScanner = rotate(scanner, rotations[i])
                val movePoints = sameDistance(referenceScanner, rotatedScanner)
                if (!movePoints.equals(Triple(0, 0, 0))) {
                    //Transform all points to reference scanner axis zero point, add them to points in set.
                    referenceScanner.addAll(rotatedScanner.map {
                        movePoints(it, movePoints)
                    })
                    locationScanners.add(movePoints) // The location of the scanner found
                    scanners.remove(scanner)         // remove scanner as we already checked those points. 
                    stop = true
                    break
                }
            }
            if (stop) break
        }
    }


    var max = 0
    for (scanner in locationScanners) {
        for (referenceScanner in locationScanners) {
            //Part 2: find manhattan distrance between scanners. 
            var value =
                abs(scanner.first - referenceScanner.first) +
                        abs(scanner.second - referenceScanner.second) +
                        abs(scanner.third - referenceScanner.third)

            if (value > max) {
                max = value
            }
        }
    }

    println(max)
    println(referenceScanner.size)
}

private fun movePoints(
    it: Triple<Int, Int, Int>,
    movePoints: Triple<Int, Int, Int>
) = Triple(
    it.first + movePoints.first,
    it.second + movePoints.second,
    it.third + movePoints.third
)

private fun rotate(input: List<Triple<Int, Int, Int>>, rotationMatrix: List<List<Int>>): List<Triple<Int, Int, Int>> {
    val output: MutableList<Triple<Int, Int, Int>> = mutableListOf()

    for (point in input) {
        //maxtrix vector multiplication.
        val xi =
            rotationMatrix[0][0] * point.first + rotationMatrix[0][1] * point.second + rotationMatrix[0][2] * point.third
        val yi =
            rotationMatrix[1][0] * point.first + rotationMatrix[1][1] * point.second + rotationMatrix[1][2] * point.third
        val zi =
            rotationMatrix[2][0] * point.first + rotationMatrix[2][1] * point.second + rotationMatrix[2][2] * point.third
        output.add(Triple(xi, yi, zi))
    }
    return output
}

// if multiple points in the two set we are comparing share the same distance, we can shift those point clouds to overlap. 
private fun sameDistance(referenceScanners: Set<Triple<Int, Int, Int>>, scanner: List<Triple<Int, Int, Int>>): Triple<Int, Int, Int> {
    val output = HashMap<Double, Int>()
    for (referenceScanner in referenceScanners) {
        for (j in scanner.indices) {
            val distance = calcEuclidianDistance(referenceScanner, scanner[j])
            output.merge(distance, 1, { x, y -> x + y }) //Count distance found between points.
            if (output.get(distance)!! > 11) {
                return Triple(
                    referenceScanner.first - scanner[j].first,
                    referenceScanner.second - scanner[j].second,
                    referenceScanner.third - scanner[j].third
                )
            }
        }
    }

    return Triple(0,0,0)
}

private fun calcEuclidianDistance(referenceScanner: Triple<Int, Int, Int>, scanner: Triple<Int, Int, Int>
) = Math.sqrt(
    Math.pow(referenceScanner.first.toDouble() - scanner.first.toDouble(), 2.0) +
            Math.pow(referenceScanner.second.toDouble() - scanner.second.toDouble(), 2.0) +
            Math.pow(referenceScanner.third.toDouble() - scanner.third.toDouble(), 2.0)
)




