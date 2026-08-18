import java.util.*;

class Solution {
    public int largestInteger(int[] nums, int k) {
        int n = nums.length;
        Set<Integer> values = new TreeSet<>();
        for (int num : nums) values.add(num);

        int best = -1;
        for (int x : values) {
            int count = 0;
            for (int start = 0; start + k <= n; start++) {
                boolean present = false;
                for (int i = start; i < start + k; i++) {
                    if (nums[i] == x) { present = true; break; }
                }
                if (present) count++;
            }
            if (count == 1) {
                best = Math.max(best, x);
            }
        }

        return best;
    }
}