class Solution {
    private static final int[][] FACTOR = {
        {0,0,0,0}, 
        {0,0,0,0}, 
        {1,0,0,0}, 
        {0,1,0,0},
        {2,0,0,0},
        {0,0,1,0}, 
        {1,1,0,0}, 
        {0,0,0,1}, 
        {3,0,0,0}, 
        {0,2,0,0}, 
    };

    public String smallestNumber(String num, long t) {
        int[] primeCount = new int[4];
        long tt = t;
        int[] primes = {2,3,5,7};
        for (int p = 0; p < 4; p++) {
            while (tt % primes[p] == 0) {
                tt /= primes[p];
                primeCount[p]++;
            }
        }
        if (tt != 1) return "-1";

        int[] factorCount = getFactorCount(primeCount);
        int minLen = sumValues(factorCount);
        if (minLen > num.length()) {
            return construct(factorCount);
        }

        int n = num.length();
        int[] primeCountPrefix = getPrimeCountOfString(num);
        int firstZeroIndex = num.indexOf('0');
        if (firstZeroIndex == -1) {
            firstZeroIndex = n;
            if (isSubset(primeCount, primeCountPrefix)) {
                return num;
            }
        }

        for (int i = n - 1; i >= 0; i--) {
            int d = num.charAt(i) - '0';
            for (int p = 0; p < 4; p++) {
                primeCountPrefix[p] -= FACTOR[d][p];
            }
            int spaceAfterThisDigit = n - 1 - i;
            if (i > firstZeroIndex) continue;

            for (int biggerDigit = d + 1; biggerDigit < 10; biggerDigit++) {
                int[] needed = new int[4];
                for (int p = 0; p < 4; p++) {
                    int v = primeCount[p] - primeCountPrefix[p] - FACTOR[biggerDigit][p];
                    needed[p] = Math.max(0, v);
                }
                int[] factorsAfterReplacement = getFactorCount(needed);
                int need = sumValues(factorsAfterReplacement);
                if (need <= spaceAfterThisDigit) {
                    int fillOnes = spaceAfterThisDigit - need;
                    StringBuilder sb = new StringBuilder();
                    sb.append(num, 0, i);
                    sb.append((char) ('0' + biggerDigit));
                    for (int k = 0; k < fillOnes; k++) sb.append('1');
                    sb.append(construct(factorsAfterReplacement));
                    return sb.toString();
                }
            }
        }

        int[] factorsAfterExtension = getFactorCount(primeCount);
        int need = sumValues(factorsAfterExtension);
        int fillOnes = n + 1 - need;
        StringBuilder sb = new StringBuilder();
        for (int k = 0; k < fillOnes; k++) sb.append('1');
        sb.append(construct(factorsAfterExtension));
        return sb.toString();
    }

    private int[] getPrimeCountOfString(String num) {
        int[] count = new int[4];
        for (int i = 0; i < num.length(); i++) {
            int d = num.charAt(i) - '0';
            for (int p = 0; p < 4; p++) count[p] += FACTOR[d][p];
        }
        return count;
    }

    private int[] getFactorCount(int[] count) {
        int count8 = count[0] / 3;
        int remaining2 = count[0] % 3;
        int count9 = count[1] / 2;
        int count3 = count[1] % 2;
        int count4 = remaining2 / 2;
        int count2 = remaining2 % 2;
        int count6 = 0;
        if (count2 == 1 && count3 == 1) {
            count2 = 0;
            count3 = 0;
            count6 = 1;
        }
        if (count3 == 1 && count4 == 1) {
            count2 = 1;
            count6 = 1;
            count3 = 0;
            count4 = 0;
        }

        int[] res = new int[10];
        res[2] = count2;
        res[3] = count3;
        res[4] = count4;
        res[5] = count[2];
        res[6] = count6;
        res[7] = count[3];
        res[8] = count8;
        res[9] = count9;
        return res;
    }

    private String construct(int[] factors) {
        StringBuilder sb = new StringBuilder();
        for (int digit = 2; digit < 10; digit++) {
            for (int k = 0; k < factors[digit]; k++) sb.append((char) ('0' + digit));
        }
        return sb.toString();
    }

    private boolean isSubset(int[] a, int[] b) {
        for (int p = 0; p < 4; p++) if (b[p] < a[p]) return false;
        return true;
    }

    private int sumValues(int[] factors) {
        int s = 0;
        for (int digit = 2; digit < 10; digit++) s += factors[digit];
        return s;
    }
}