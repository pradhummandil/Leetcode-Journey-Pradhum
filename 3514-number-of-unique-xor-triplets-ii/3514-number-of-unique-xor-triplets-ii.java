import java.util.*;

class Solution {
    public int uniqueXorTriplets(int[] nums) {
        int MAXV = 2048; // values up to 1500 < 2048, xor of up to 3 such values still < 2048
        boolean[] present = new boolean[MAXV];
        for (int x : nums) present[x] = true;

        List<Integer> distinct = new ArrayList<>();
        for (int v = 0; v < MAXV; v++) if (present[v]) distinct.add(v);
        int m = distinct.size();

        boolean[] pairSet = new boolean[MAXV];
        for (int i = 0; i < m; i++) {
            int a = distinct.get(i);
            for (int j = i; j < m; j++) {
                int b = distinct.get(j);
                pairSet[a ^ b] = true;
            }
        }

        boolean[] tripleSet = new boolean[MAXV];
        for (int p = 0; p < MAXV; p++) {
            if (!pairSet[p]) continue;
            for (int idx = 0; idx < m; idx++) {
                int c = distinct.get(idx);
                tripleSet[p ^ c] = true;
            }
        }

        int count = 0;
        for (int v = 0; v < MAXV; v++) if (tripleSet[v]) count++;
        return count;
    }
}