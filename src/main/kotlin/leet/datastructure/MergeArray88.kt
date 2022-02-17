package leet.datastructure

fun merge(nums1: IntArray, m: Int, nums2: IntArray, n: Int): Unit {
    var i = m - 1
    var j = n - 1
    var index = nums1.size - 1
    while (index >= 0) {
        
        if (i < 0) {
            println()
            nums1[index] = nums2[j]
            index--
            j--
            continue
        } else if (j < 0) {
            nums1[index] = nums1[i]
            index--
            i--
            continue
        }
        if (nums1[i] > nums2[j]) {
            nums1[index] = nums1[i]
            index--
            i--
        } else {
            nums1[index] = nums2[j]
            index--
            j--
        }
    }
    println(nums1.toList())
}

fun main(args: Array<String>) {

    merge(intArrayOf(0), 0, intArrayOf(1), 1)


}