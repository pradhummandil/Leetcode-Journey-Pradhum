class Solution {
    public int numberOfSets(int n, int k) {
        int MOD = 1_000_000_007;
        long[][] f = new long[n][k + 1];
        long[][] g = new long[n][k + 1];

        for (int i = 0; i < n; i++) {
            f[i][0] = 1;
        }
        g[0][0] = 1;
        for (int i = 1; i < n; i++) {
            g[i][0] = (g[i - 1][0] + f[i][0]) % MOD;
        }

        for (int j = 1; j <= k; j++) {
            f[0][j] = 0; // can't form a segment with only point 0
            g[0][j] = f[0][j];
            for (int i = 1; i < n; i++) {
                f[i][j] = (f[i - 1][j] + g[i - 1][j - 1]) % MOD;
                g[i][j] = (g[i - 1][j] + f[i][j]) % MOD;
            }
        }

        return (int) (f[n - 1][k] % MOD);
    }
}