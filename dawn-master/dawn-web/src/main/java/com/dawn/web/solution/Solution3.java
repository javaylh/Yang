package com.dawn.web.solution;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * ---------------------------
 * 给定一个字符串，请你找出其中不含有重复字符的 最长子串 的长度。
 *
 * 示例 1:
 *
 * 输入: "abcabcbb"
 * 输出: 3
 * 解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
 * 示例 2:
 *
 * 输入: "bbbbb"
 * 输出: 1
 * 解释: 因为无重复字符的最长子串是 "b"，所以其长度为 1。
 * 示例 3:
 *
 * 输入: "pwwkew"
 * 输出: 3
 * 解释: 因为无重复字符的最长子串是 "wke"，所以其长度为 3。
 *      请注意，你的答案必须是 子串 的长度，"pwke" 是一个子序列，不是子串
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/longest-substring-without-repeating-characters
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * ---------------------------
 * @author： YangLiHua
 * 时间： 2019-09-12 15:20:00
 * ---------------------------
 */
@Slf4j
public class Solution3 {
    public int lengthOfLongestSubstring(String s) {
        if (s.length() < 1) {
            return 0;
        }
        Map<Character, Integer> map = new HashMap<>();
        int maxLength = 0;
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            if (map.containsKey(s.charAt(i))) {
                builder.setLength(0);
                map.clear();
            }
            builder.append(s.charAt(i));
            map.put(s.charAt(i), i);
            if (builder.length() > maxLength) {
                maxLength = builder.length();
            }
        }
        return maxLength;
    }

    @Test
    public void test() {
        log.info("不含有重复字符的 最长子串 的长度：{}", lengthOfLongestSubstring("dvdf"));
    }
}
