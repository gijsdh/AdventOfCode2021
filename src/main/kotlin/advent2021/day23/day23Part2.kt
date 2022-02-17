package day23


import java.lang.Integer.max
import java.lang.Integer.min
import java.util.*
import kotlin.math.abs

val costArray: IntArray = intArrayOf(1, 10, 100, 1000)
val typeToCollum: IntArray = intArrayOf(2, 4, 6, 8)
val hallway: IntArray = intArrayOf(0, 1, 3, 5, 7, 9, 10)
val char: CharArray = charArrayOf('A', 'B', 'C', 'D')

fun main(args: Array<String>) {
//  #############    ############################################
//  #...........#    #(0,0).   .  .   . (5,0) .  .   .   .(10,0)#  --> example
//  ###B#C#B#D###    #######   B  #   C  #   B  #   D  ###
//    #A#D#C#A#            #(-2,2)#(-2,4)#(-2,6)#(-2,8)#
//    #########            #############################
//
//  #############
//  #...........#
//  ###D#D#C#B###   --> 
//    #D#C#B#A#
//    #D#B#A#C#
//    #B#A#A#C#
//    #########


    var inital: MutableSet<Amphipod> = mutableSetOf()
    inital.add(Amphipod(2, -4, 1))
    inital.add(Amphipod(2, -1, 3))
    inital.add(Amphipod(4, -4, 0))
    inital.add(Amphipod(4, -1, 3))
    inital.add(Amphipod(6, -4, 0))
    inital.add(Amphipod(6, -1, 2))
    inital.add(Amphipod(8, -4, 2))
    inital.add(Amphipod(8, -1, 1))
    
    inital.add(Amphipod(2, -2, 3))
    inital.add(Amphipod(2, -3, 3))
    inital.add(Amphipod(4, -2, 2))
    inital.add(Amphipod(4, -3, 1))
    inital.add(Amphipod(6, -2, 1))
    inital.add(Amphipod(6, -3, 0))
    inital.add(Amphipod(8, -2, 0))
    inital.add(Amphipod(8, -3, 2))
    
  
//    inital.add(Amphipod(2, -4, 0))
//    inital.add(Amphipod(2, -1, 1))
//    inital.add(Amphipod(4, -4, 3))
//    inital.add(Amphipod(4, -1, 2))
//    inital.add(Amphipod(6, -4, 2))
//    inital.add(Amphipod(6, -1, 1))
//    inital.add(Amphipod(8, -4, 0))
//    inital.add(Amphipod(8, -1, 3))

    printState(State(0, inital))
    //println(shouldNotMove(State(0, inital), Amphipod(2, -1, 1)))

    val comparByCost: Comparator<State> = compareBy { it.cost }
    val costQueue = PriorityQueue<State>(comparByCost)
    var seen : MutableSet<Set<Amphipod>> = mutableSetOf()
    costQueue.add(State(0, inital))
    val currentCosts = mutableMapOf<State, Int>().withDefault { Int.MAX_VALUE }

    //Dijkstra again and suffer through implementation. This was took way to much time.
    var price = 1000
    var count= 0
    while (costQueue.isNotEmpty()) {
        val state = costQueue.remove();
        count++
//        printState(state)
        if (state.cost > price) {
            println(count)
            println(state.cost)
            printState(state)
            price += 1000
        }

        if (seen.contains(state.locations)) {
            continue
        }
        seen.add(state.locations);


        if (completed(state)) {
            printState(state)
            println("Found " + state.cost)
            println(count)
            break
        }
        
        for (newState in findMoves(state)) {
            if (seen.contains(newState.locations)) continue
            if (newState.cost < currentCosts.getValue(newState)) {
                currentCosts[newState] = newState.cost.toInt()
                costQueue.add(newState)
            }
        }
    }
}

private fun findMoves(state: State): List<State> {
    var listOfStates: MutableList<State> = mutableListOf()
    assert(state.locations.size < 17)
    for (amp in state.locations) {
        val otherAmp = state.locations.filter { !it.equals(amp) }.toSet()
        if (!canMove(otherAmp, amp)) return continue
        if (shouldNotMove(otherAmp, amp)) return continue
        if (canMoveToCollumn(otherAmp, amp)) {
            val hor = typeToCollum[amp.type]
            if (!blocked(otherAmp, amp, hor)) {
                val depth = otherAmp.count { it.horizontal == hor } - 4
                listOfStates.add(updateStateWithLocation(otherAmp, amp, hor, depth, state.cost))
                continue
            }
        }
        
        if(amp.depth == 0) continue
        for (hor in hallway) {
            if (!blocked(otherAmp, amp, hor)) {
                listOfStates.add(updateStateWithLocation(otherAmp, amp, hor, 0, state.cost))
                continue
            }
        }
    }
//    if(listOfStates.isEmpty()) printState(state)
    return listOfStates
}

private fun updateStateWithLocation(state: Set<Amphipod>, amp: Amphipod, hor: Int, depth: Int, cost: Long): State {
    var locs = state.toMutableSet()
    var newAmphipod = Amphipod(hor, depth, amp.type)
    locs.add(newAmphipod)

    val newCost = (abs(amp.horizontal - hor) + abs(depth + amp.depth)) * costArray[amp.type]
    return State(newCost + cost, locs)
}

private fun blocked(state: Set<Amphipod>, amp: Amphipod, horizontal: Int): Boolean {
    if (state.any {
            it.depth == 0 && IntRange(
                min(amp.horizontal, horizontal),
                max(amp.horizontal, horizontal)
            ).contains(it.horizontal)
        }) return true
    return false
}

private fun canMove(state: Set<Amphipod>, amp: Amphipod): Boolean {
    if (amp.depth < -1) {
        for (loc in state) {
            if (loc.horizontal == amp.horizontal && loc.depth > amp.depth) return false
        }
    }
    return true
}

private fun canMoveToCollumn(state: Set<Amphipod>, amp: Amphipod): Boolean {
    for (loc in state) {
        if (loc.horizontal == typeToCollum[amp.type] && loc.type != amp.type) return false //Check if there are other amp in target room. 
    }
    return true
}

private fun shouldNotMove(state: Set<Amphipod>, amp: Amphipod): Boolean {
    if (!isInCorrectColumn(amp)) return false
    if (amp.depth == -4) return true
    for (loc in state) {
        if (loc.type != amp.type && loc.horizontal == amp.horizontal && amp.depth > loc.depth) return false
    }
    return true
}

private fun printState(state: State) {
    println("#############")
    var lines = mutableListOf<CharArray>()
    lines.add("#...........#".toCharArray())
    repeat(4) {
        lines.add("###.#.#.#.###".toCharArray())
    }
    for (amp in state.locations) {
        lines[abs(amp.depth)][amp.horizontal + 1] = char[amp.type]
    }
    for (line in lines) {
        println(line.joinToString(""))
    }
    println("#############")
}

private fun completed(state: State): Boolean {
    for (amp in state.locations) {
        if (!isInCorrectColumn(amp)) return false
    }
    return true
}

private fun isInCorrectColumn(amp: Amphipod): Boolean = amp.horizontal == typeToCollum[amp.type]

 data class Amphipod(var horizontal: Int, var depth: Int, val type: Int) {
    init {
        assert(horizontal <= 10 || horizontal > 0 || depth <= 0 || depth >= -2)
    }
}

data class State(val cost: Long, val locations: Set<Amphipod>) {}