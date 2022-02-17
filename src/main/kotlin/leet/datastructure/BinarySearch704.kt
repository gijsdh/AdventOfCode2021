package leet.datastructure


public class A {
    fun search(nums: IntArray, target: Int): Int {
        return search(nums, target, 0)
    }

    fun search(nums: IntArray, target: Int, index: Int): Int {
        var half = nums.size / 2
        var number = nums[half]
        if (number == target) return half + index
        if (nums.size == 1) return -1
        if (number > target) return search(nums.sliceArray(IntRange(0, half - 1)), target, index + half)
        return search(nums.sliceArray(IntRange(half, nums.size - 1)), target, index + half)
    }
}

fun main(args: Array<String>) {
    print(A().search(intArrayOf(-1,0,3,5,9,12), 12))

}