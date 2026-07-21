import java.util.*;

class Solution {
    public int maxActiveSectionsAfterTrade(String s) {
        int n = s.length();
        int onesCount = 0;
        for (int i = 0; i < n; i++) if (s.charAt(i) == '1') onesCount++;

        List<int[]> blocks = new ArrayList<>();
        char curChar = '1';
        int curLen = 1;

        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);
            if (c == curChar) {
                curLen++;
            } else {
                blocks.add(new int[]{curChar - '0', curLen});
                curChar = c;
                curLen = 1;
            }
        }
        if (curChar == '1') {
            curLen++;
            blocks.add(new int[]{1, curLen});
        } else {
            blocks.add(new int[]{curChar - '0', curLen});
            blocks.add(new int[]{1, 1});
        }

        int maxGain = 0;
        int m = blocks.size();
        for (int i = 1; i < m - 1; i++) {
            if (blocks.get(i)[0] == 1) {
                int leftZero = blocks.get(i - 1)[1];
                int rightZero = blocks.get(i + 1)[1];
                maxGain = Math.max(maxGain, leftZero + rightZero);
            }
        }

        return onesCount + maxGain;
    }
}