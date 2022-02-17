package leet.datastructure

import java.lang.Integer.max

fun maxSubArray(nums: IntArray): Int {
    var max_so_far = nums[0]
    var cur_max = nums[0]
    for (i in 1 until nums.size) {
        println(""+nums[i]+"   "+ (cur_max + nums[i]) + "   " + cur_max)
        cur_max = max(nums[i], cur_max + nums[i])
        println(cur_max)
        if (cur_max > max_so_far) max_so_far = cur_max
    }
    return max_so_far
}

fun main(args: Array<String>) {

    maxSubArray(intArrayOf(-2,1,-3,4,-1,2,1,-5,4))
    
    
}
