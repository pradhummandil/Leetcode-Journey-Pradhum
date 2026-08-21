class Solution {
    public long findKthSmallest(int[] coins, int k) {
        int n = coins.length;
        long[] lcmForMask = new long[1 << n];
        lcmForMask[0] = 1;
        for (int mask = 1; mask < (1 << n); mask++) {
            int lowBit = Integer.numberOfTrailingZeros(mask);
            long prevLcm = lcmForMask[mask ^ (1 << lowBit)];
            lcmForMask[mask] = lcm(prevLcm, coins[lowBit]);
        }

        long lo = 1, hi = (long) 25 * k;
        while (lo < hi) {
            long mid = lo + (hi - lo) / 2;
            if (countAtMost(mid, lcmForMask, n) >= k) {
                hi = mid;
            } else {
                lo = mid + 1;
            }
        }
        return lo;
    }

    private long countAtMost(long value, long[] lcmForMask, int n) {
        long count = 0;
        for (int mask = 1; mask < (1 << n); mask++) {
            int bits = Integer.bitCount(mask);
            long l = lcmForMask[mask];
            if (l > value) continue;
            long term = value / l;
            if (bits % 2 == 1) count += term;
            else count -= term;
        }
        return count;
    }

    private long gcd(long a, long b) {
        while (b != 0) { long t = b; b = a % b; a = t; }
        return a;
    }

    private long lcm(long a, long b) {
        return a / gcd(a, b) * b;
    }
}