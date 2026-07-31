import java.util.*;

class Solution {
    public int minimumPushes(String word) {
        int[] freq = new int[26];
        for (char c : word.toCharArray()) freq[c - 'a']++;

        Integer[] sortedFreq = new Integer[26];
        for (int i = 0; i < 26; i++) sortedFreq[i] = freq[i];
        Arrays.sort(sortedFreq, Collections.reverseOrder());

        int totalPushes = 0;
        int pushLevel = 1;
        int count = 0;

        for (int i = 0; i < 26; i++) {
            if (sortedFreq[i] == 0) break;
            totalPushes += pushLevel * sortedFreq[i];
            count++;
            if (count == 8) {
                count = 0;
                pushLevel++;
            }
        }

        return totalPushes;
    }
}