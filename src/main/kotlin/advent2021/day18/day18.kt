package day18

import getResourceAsText
import kotlin.math.ceil
import kotlin.math.floor

// Met gestolen data formaat van Reddit. 
fun main(args: Array<String>) {
    val input = getResourceAsText("input.txt")
    val test = getResourceAsText("rotattions.txt")
    val inputTest = getResourceAsText("inputExample.txt")
    val inputLines = input.lines()

    println(inputLines.map { parsIt(it) }.reduce { a, b -> reduceNumber(a + b) }.let { magnitude(it) })

    var max = 0L
    for (i in 0 until inputLines.size) {
        for (j in 0 until inputLines.size) {
            if (i == j) continue
            var value = reduceNumber(parsIt(inputLines[i]) + (parsIt(inputLines[j]))).let { magnitude(it) }
            //   println(value)
            if (value > max) {
                max = value
            }
        }
    }
    println(max)
}

private fun magnitude(fish: SnailFish): Long {
    return when (fish) {
        is SnailFish.Regular -> fish.value.toLong()
        is SnailFish.SubClass -> 3 * magnitude(fish.left) + 2 * magnitude(fish.right)
    }
}

private fun reduceNumber(fish: SnailFish): SnailFish {
    while (true) {
        var hasExplosion = hasExplosion(fish, 1)
        if (hasExplosion != null) {
            explosion(hasExplosion);
            continue
        }
       
        var hasGreatNumber = hasGreatNumber(fish)
        if (hasGreatNumber != null) {
            splitThing(hasGreatNumber);
            continue
        }
        break
    }
    return fish
}

fun splitThing(fish: SnailFish.Regular) {
    val l = floor(fish.value / 2.0).toInt()
    val r = ceil(fish.value / 2.0).toInt()
    val newNum = SnailFish.SubClass(SnailFish.Regular(l), SnailFish.Regular(r))
    fish.parent?.replace(fish, newNum);
}

fun hasGreatNumber(fish: SnailFish): SnailFish.Regular? {
    return when (fish) {
        is SnailFish.Regular -> if (fish.value > 9) fish else null
        is SnailFish.SubClass -> hasGreatNumber(fish.left) ?: hasGreatNumber(fish.right)
    }
}

private fun hasExplosion(fish: SnailFish, depth: Int): SnailFish.SubClass? {
    return when (fish) {
        is SnailFish.Regular -> null
        is SnailFish.SubClass -> {
            if (depth == 5) {
                return fish
            } else {
                return hasExplosion(fish.left, depth + 1) ?: hasExplosion(fish.right, depth + 1)
            }
        }
    }
}

private fun explosion(fish: SnailFish.SubClass) {
    val findRight = findRight(fish) 
    val findLeft = findLeft(fish)

    findRight?.let { left(it).value += (fish.right as SnailFish.Regular).value }
    findLeft?.let { right(it).value += (fish.left as SnailFish.Regular).value }

    fish.parent?.replace(fish, SnailFish.Regular(0))
}


fun right(fish: SnailFish): SnailFish.Regular {
    return when (fish) {
        is SnailFish.Regular -> fish
        is SnailFish.SubClass -> right(fish.right)
    }
}

fun left(fish: SnailFish): SnailFish.Regular {
    return when (fish) {
        is SnailFish.Regular -> fish
        is SnailFish.SubClass -> left(fish.left)
    }
}

private fun findRight(fish: SnailFish.SubClass): SnailFish? {
    var current = fish;
    while (current.parent != null) {
        if (current!!.parent!!.left === current) { // Here the pointer must be pointing to the same object. Equals does not work. 
            return current!!.parent!!.right
        } else {
            current = current.parent!!
        }
    }
    return null
}

private fun findLeft(fish: SnailFish.SubClass): SnailFish? {
    var current = fish;
    while (current.parent != null) {
        if (current!!.parent!!.right === current) { // Here the pointer must be pointing to the same object. Equals does not work. 
            return current!!.parent!!.left
        } else {
            current = current.parent!!
        }
    }
    return null
}

private fun parsIt(test: String): SnailFish {
    if (test.startsWith("[")) {
        var counter = -1 // start with -1 so it does not count first '['
        var loc = 0
        for ((i, char) in test.withIndex()) {
            if (char == ']') counter--
            if (char == '[') counter++
            if (char == ',' && counter == 0) {
                loc = i
                break
            }
        }
        return SnailFish.SubClass(parsIt(test.substring(1, loc)), parsIt(test.substring(loc + 1, test.length - 1)))
    } else {
        return SnailFish.Regular(test.toInt())
    }
}

//data structure stolen. 
sealed class SnailFish(var parent: SubClass? = null) {

    operator fun plus(other: SnailFish): SnailFish {
        return SubClass(this, other);
    }
    
    data class SubClass(var left: SnailFish, var right: SnailFish) : SnailFish() {
        init {
            left.parent = this
            right.parent = this
        }

        fun replace(old: SnailFish, new: SnailFish) {
            if (left === old) {
                left = new
            } else {
                right = new
            }
            new.parent = this
        }

        override fun toString(): String {
            return super.toString()
        }
    }

    data class Regular(var value: Int) : SnailFish() {
        override fun toString(): String {
            return super.toString()
        }
    }

    override fun toString(): String {
        return when (this) {
            is Regular -> "" + value
            is SubClass -> "[$left,$right]"
        }
    }
}

