class Solution {
    public int longestSubsequence(int[] nums) {
        int totalXor = 0;
        boolean hasNonZero = false;
        for (int num : nums) {
            totalXor ^= num;
            if (num != 0) hasNonZero = true;
        }

        int n = nums.length;
        if (totalXor != 0) return n;
        if (hasNonZero) return n - 1;
        return 0;
    }
}