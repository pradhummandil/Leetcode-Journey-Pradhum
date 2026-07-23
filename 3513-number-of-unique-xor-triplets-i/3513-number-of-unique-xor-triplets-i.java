class Solution {
    public int uniqueXorTriplets(int[] nums) {
        int n = nums.length;
        if (n < 3) return n;
        int x = 31 - Integer.numberOfLeadingZeros(n);
        return 1 << (x + 1);
    }
}