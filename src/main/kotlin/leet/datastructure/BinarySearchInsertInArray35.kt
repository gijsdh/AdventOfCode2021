package leet.datastructure

fun searchInsert(nums: IntArray, target: Int): Int {
    return searchInsert(nums, target, 0)
}

fun searchInsert(nums: IntArray, target: Int, index: Int): Int {
    var half = nums.size / 2
    var number = nums[half]
    
    println(""+ half+ " " + number)
    if (number == target) return half + index
    if (nums.size == 1) if(number > target) return index else return index + 1
    if (number > target) return searchInsert(nums.sliceArray(IntRange(0, half - 1)), target, index)
    return searchInsert(nums.sliceArray(IntRange(half, nums.size - 1)), target, index + half)
}
fun main(args: Array<String>) {

    println(searchInsert(intArrayOf(1,3,5,6),3))


}


fun search(nums: IntArray, target: Int, index: Int): Int {
    var half = nums.size / 2
    var number = nums[half]
    if (number == target) return half + index
    if (nums.size == 1) return -1
    if (number > target) return search(nums.sliceArray(IntRange(0, half - 1)), target, index + half)
    return search(nums.sliceArray(IntRange(half, nums.size - 1)), target, index + half)
}