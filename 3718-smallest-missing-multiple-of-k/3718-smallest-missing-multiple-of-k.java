import java.util.*;

class Solution {
    public int missingMultiple(int[] nums, int k) {
        Set<Integer> set = new HashSet<>();
        for (int num : nums) set.add(num);

        int x = k;
        while (set.contains(x)) {
            x += k;
        }

        return x;
    }
}