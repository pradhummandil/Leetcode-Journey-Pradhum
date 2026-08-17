class Solution {
    public int stoneGameV(int[] stoneValue) {
        int n = stoneValue.length;
        int[] prefix = new int[n + 1];
        for (int i = 0; i < n; i++) prefix[i + 1] = prefix[i] + stoneValue[i];

        Integer[][] memo = new Integer[n][n];
        return solve(0, n - 1, stoneValue, prefix, memo);
    }

    private int solve(int lo, int hi, int[] stoneValue, int[] prefix, Integer[][] memo) {
        if (lo == hi) return 0;
        if (memo[lo][hi] != null) return memo[lo][hi];

        int best = 0;
        for (int mid = lo; mid < hi; mid++) {
            int leftSum = prefix[mid + 1] - prefix[lo];
            int rightSum = prefix[hi + 1] - prefix[mid + 1];

            if (leftSum < rightSum) {
                best = Math.max(best, leftSum + solve(lo, mid, stoneValue, prefix, memo));
            } else if (leftSum > rightSum) {
                best = Math.max(best, rightSum + solve(mid + 1, hi, stoneValue, prefix, memo));
            } else {
                best = Math.max(best, leftSum + solve(lo, mid, stoneValue, prefix, memo));
                best = Math.max(best, rightSum + solve(mid + 1, hi, stoneValue, prefix, memo));
            }
        }

        memo[lo][hi] = best;
        return best;
    }
}