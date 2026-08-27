class Solution {
    public String lexGreaterPermutation(String s, String target) {
        int n = s.length();
        int[] freq = new int[26];
        for (char c : s.toCharArray()) freq[c - 'a']++;

        int[] work = freq.clone();
        int matchLen = 0;
        while (matchLen < n && work[target.charAt(matchLen) - 'a'] > 0) {
            work[target.charAt(matchLen) - 'a']--;
            matchLen++;
        }

        for (int i = matchLen; i >= 0; i--) {
            if (i < matchLen) {
                work[target.charAt(i) - 'a']++;
            }
            if (i == n) continue; // no target[i] to compare against

            int chosen = -1;
            for (int c = target.charAt(i) - 'a' + 1; c < 26; c++) {
                if (work[c] > 0) { chosen = c; break; }
            }

            if (chosen != -1) {
                StringBuilder sb = new StringBuilder();
                sb.append(target, 0, i);
                sb.append((char) ('a' + chosen));
                work[chosen]--;
                for (int c = 0; c < 26; c++) {
                    for (int k = 0; k < work[c]; k++) sb.append((char) ('a' + c));
                }
                return sb.toString();
            }
        }

        return "";
    }
}