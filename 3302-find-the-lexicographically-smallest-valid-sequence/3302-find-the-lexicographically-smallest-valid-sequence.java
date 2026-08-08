class Solution {
    public int[] validSequence(String word1, String word2) {
        int n = word1.length(), m = word2.length();

        // suffixMatch[i] = length of longest suffix of word2 matchable (exact) as subsequence of word1[i:]
        int[] suffixMatch = new int[n + 1];
        int p = m - 1;
        suffixMatch[n] = 0;
        for (int i = n - 1; i >= 0; i--) {
            suffixMatch[i] = suffixMatch[i + 1];
            if (p >= 0 && word1.charAt(i) == word2.charAt(p)) {
                p--;
                suffixMatch[i] = m - 1 - p;
            }
        }

        int[] result = new int[m];
        int i = 0, j = 0;
        boolean usedChange = false;

        while (j < m) {
            if (i >= n) return new int[0];

            if (word1.charAt(i) == word2.charAt(j)) {
                result[j] = i;
                i++;
                j++;
            } else if (!usedChange && suffixMatch[i + 1] >= m - j - 1) {
                result[j] = i;
                usedChange = true;
                i++;
                j++;
            } else {
                i++;
            }
        }

        return result;
    }
}