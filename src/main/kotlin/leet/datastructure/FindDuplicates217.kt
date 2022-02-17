package leet.datastructure


fun containsDuplicate(nums: IntArray): Boolean {
    var set = hashSetOf<Int>()
    for (num in nums) {
        if (!set.add(num)) {
            return false
        }
    }
    return true
}

fun main(args: Array<String>) {

}