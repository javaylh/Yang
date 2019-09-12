package com.dawn.dawnweb.solution;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.HashMap;

/**
 * ---------------------------
 * 给定一个整数数组 nums 和一个目标值 target，请你在该数组中找出和为目标值的那 两个 整数，并返回他们的数组下标。
 *
 * 你可以假设每种输入只会对应一个答案。但是，你不能重复利用这个数组中同样的元素。
 *
 * 示例:
 *
 * 给定 nums = [2, 7, 11, 15], target = 9
 *
 * 因为 nums[0] + nums[1] = 2 + 7 = 9
 * 所以返回 [0, 1]
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/two-sum
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * ---------------------------
 * @author： YangLiHua
 * 时间： 2019-09-06 15:05:28
 * ---------------------------
 */
@Slf4j
public class Solution1 {
    public int[] twoSum(int[] nums, int target) {
        int[] arr = new int[2];
        HashMap<Integer,Integer> hashMap = new HashMap<Integer,Integer>();
        for (int i = 0; i < nums.length; i++) {
            if (hashMap.containsKey(nums[i])) {
                arr[0] = hashMap.get(nums[i]);
                arr[1] = i;
            }
            // key值存第x个数的补数，value存下标
            hashMap.put(target-nums[i], i);
        }
        return arr;
    }

    @Test
    public void test() {
        int[] nums = new int[]{3, 1, 6, 2};
        int target = 8;
        log.info("答案是：{}", twoSum(nums, target));
    }
}
