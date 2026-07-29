import java.util.*;
import java.math.BigInteger;

class Solution {
    public String smallestPalindrome(String s, int k) {
        int n = s.length();
        int[] freq = new int[26];
        for (char c : s.toCharArray()) freq[c - 'a']++;

        int[] half = new int[26];
        int midChar = -1;
        for (int i = 0; i < 26; i++) {
            if (freq[i] % 2 == 1) midChar = i;
            half[i] = freq[i] / 2;
        }

        int m = 0;
        for (int i = 0; i < 26; i++) m += half[i];

        BigInteger[] fact = new BigInteger[m + 1];
        fact[0] = BigInteger.ONE;
        for (int i = 1; i <= m; i++) fact[i] = fact[i - 1].multiply(BigInteger.valueOf(i));

        BigInteger totalPerm = fact[m];
        for (int i = 0; i < 26; i++) {
            if (half[i] > 1) totalPerm = totalPerm.divide(fact[half[i]]);
        }

        BigInteger K = BigInteger.valueOf(k);
        if (totalPerm.compareTo(K) < 0) {
            return "";
        }

        int[] remaining = half.clone();
        int T = m;
        BigInteger permCurrent = totalPerm;
        long kk = k;

        StringBuilder resultHalf = new StringBuilder();

        for (int pos = 0; pos < m; pos++) {
            for (int c = 0; c < 26; c++) {
                if (remaining[c] == 0) continue;
                BigInteger candidateCount;
                if (T == 0) {
                    candidateCount = BigInteger.ZERO;
                } else {
                    candidateCount = permCurrent.multiply(BigInteger.valueOf(remaining[c])).divide(BigInteger.valueOf(T));
                }
                if (candidateCount.compareTo(BigInteger.valueOf(kk)) >= 0) {
                    resultHalf.append((char) ('a' + c));
                    remaining[c]--;
                    T--;
                    permCurrent = candidateCount;
                    break;
                } else {
                    kk -= candidateCount.longValueExact();
                }
            }
        }

        StringBuilder result = new StringBuilder();
        result.append(resultHalf);
        if (midChar != -1) result.append((char) ('a' + midChar));
        result.append(resultHalf.reverse());

        return result.toString();
    }
}