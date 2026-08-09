class Solution {
    public int stoneGameII(int[] piles) {
        int n = piles.length;
        int[] suffixSum = new int[n + 1];
        for (int i = n - 1; i >= 0; i--) {
            suffixSum[i] = suffixSum[i + 1] + piles[i];
        }

        Integer[][] memo = new Integer[n][n + 1];
        return solve(0, 1, piles, suffixSum, memo, n);
    }

    private int solve(int i, int M, int[] piles, int[] suffixSum, Integer[][] memo, int n) {
        if (i >= n) return 0;
        if (2 * M >= n - i) {
            return suffixSum[i];
        }
        if (memo[i][M] != null) return memo[i][M];

        int best = 0;
        int taken = 0;
        for (int x = 1; x <= 2 * M; x++) {
            if (i + x > n) break;
            taken += piles[i + x - 1];
            int opponent = solve(i + x, Math.max(M, x), piles, suffixSum, memo, n);
            int mine = taken + (suffixSum[i] - taken - opponent);
            best = Math.max(best, mine);
        }

        memo[i][M] = best;
        return best;
    }
}