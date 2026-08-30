class Solution {
    public int minimumDeletions(int[] nums) {
        int n = nums.length;
        int minIdx = 0, maxIdx = 0;

        for (int i = 0; i < n; i++) {
            if (nums[i] < nums[minIdx]) minIdx = i;
            if (nums[i] > nums[maxIdx]) maxIdx = i;
        }

        int lo = Math.min(minIdx, maxIdx);
        int hi = Math.max(minIdx, maxIdx);

        int option1 = hi + 1; // remove all from front through hi
        int option2 = n - lo; // remove all from back through lo
        int option3 = (lo + 1) + (n - hi); // remove front through lo, back through hi

        return Math.min(option1, Math.min(option2, option3));
    }
}