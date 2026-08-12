import java.util.*;

class Solution {
    public int maxSubarrayLength(int[] nums, int k) {
        Map<Integer, Integer> count = new HashMap<>();
        int left = 0;
        int maxLen = 0;

        for (int right = 0; right < nums.length; right++) {
            count.merge(nums[right], 1, Integer::sum);

            while (count.get(nums[right]) > k) {
                count.merge(nums[left], -1, Integer::sum);
                left++;
            }

            maxLen = Math.max(maxLen, right - left + 1);
        }

        return maxLen;
    }
}