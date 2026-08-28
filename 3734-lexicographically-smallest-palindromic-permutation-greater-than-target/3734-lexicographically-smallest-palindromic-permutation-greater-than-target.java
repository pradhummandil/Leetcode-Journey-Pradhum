class Solution {
    public String lexPalindromicPermutation(String s, String target) {
        int n = s.length();
        int[] freq = new int[26];
        for (char c : s.toCharArray()) freq[c - 'a']++;

        int oddCount = 0, oddChar = -1;
        for (int c = 0; c < 26; c++) {
            if (freq[c] % 2 == 1) { oddCount++; oddChar = c; }
        }
        boolean nOdd = (n % 2 == 1);
        if (nOdd && oddCount != 1) return "";
        if (!nOdd && oddCount != 0) return "";

        int m = n / 2;
        int[] halfFreq = new int[26];
        for (int c = 0; c < 26; c++) halfFreq[c] = freq[c] / 2;

        int[] work = halfFreq.clone();
        int matchLen = 0;
        while (matchLen < m && work[target.charAt(matchLen) - 'a'] > 0) {
            work[target.charAt(matchLen) - 'a']--;
            matchLen++;
        }

        int startI;
        if (matchLen == m) {
            String candidate = buildCandidate(target.substring(0, m), nOdd, oddChar);
            if (candidate.compareTo(target) > 0) return candidate;
            startI = m - 1;
        } else {
            startI = matchLen;
        }

        for (int i = startI; i >= 0; i--) {
            if (i < matchLen) {
                work[target.charAt(i) - 'a']++;
            }

            int chosen = -1;
            for (int c = target.charAt(i) - 'a' + 1; c < 26; c++) {
                if (work[c] > 0) { chosen = c; break; }
            }

            if (chosen != -1) {
                StringBuilder half = new StringBuilder();
                half.append(target, 0, i);
                half.append((char) ('a' + chosen));
                work[chosen]--;
                for (int c = 0; c < 26; c++) {
                    for (int k = 0; k < work[c]; k++) half.append((char) ('a' + c));
                }
                return buildCandidate(half.toString(), nOdd, oddChar);
            }
        }

        return "";
    }

    private String buildCandidate(String half, boolean nOdd, int oddChar) {
        StringBuilder sb = new StringBuilder();
        sb.append(half);
        if (nOdd) sb.append((char) ('a' + oddChar));
        sb.append(new StringBuilder(half).reverse());
        return sb.toString();
    }
}