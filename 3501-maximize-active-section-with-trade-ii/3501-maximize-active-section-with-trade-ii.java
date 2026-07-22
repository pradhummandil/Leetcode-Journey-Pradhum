import java.util.*;

class Solution {
    public List<Integer> maxActiveSectionsAfterTrade(String s, int[][] queries) {
        int n = s.length();
        int ones = 0;
        for (int i = 0; i < n; i++) if (s.charAt(i) == '1') ones++;

        List<int[]> zeroGroups = new ArrayList<>(); // {start, length}
        int[] zeroGroupIndex = new int[n];
        for (int i = 0; i < n; i++) {
            if (s.charAt(i) == '0') {
                if (i > 0 && s.charAt(i - 1) == '0') {
                    zeroGroups.get(zeroGroups.size() - 1)[1]++;
                } else {
                    zeroGroups.add(new int[]{i, 1});
                }
            }
            zeroGroupIndex[i] = zeroGroups.size() - 1;
        }

        int q = queries.length;
        List<Integer> ans = new ArrayList<>();

        if (zeroGroups.isEmpty()) {
            for (int i = 0; i < q; i++) ans.add(ones);
            return ans;
        }

        int g = zeroGroups.size();
        int[] mergeLengths = new int[Math.max(g - 1, 0)];
        for (int i = 0; i < g - 1; i++) {
            mergeLengths[i] = zeroGroups.get(i)[1] + zeroGroups.get(i + 1)[1];
        }

        SparseTable st = (mergeLengths.length > 0) ? new SparseTable(mergeLengths) : null;

        for (int qi = 0; qi < q; qi++) {
            int l = queries[qi][0], r = queries[qi][1];
            int lgi = zeroGroupIndex[l];
            int rgi = zeroGroupIndex[r];
            int left = (lgi == -1) ? -1 : zeroGroups.get(lgi)[1] - (l - zeroGroups.get(lgi)[0]);
            int right = (rgi == -1) ? -1 : (r - zeroGroups.get(rgi)[0] + 1);

            int startAdj = lgi + 1;
            int endAdj = (s.charAt(r) == '1' ? rgi : rgi - 1) - 1;

            int activeSections = ones;

            if (s.charAt(l) == '0' && s.charAt(r) == '0' && lgi + 1 == rgi) {
                activeSections = Math.max(activeSections, ones + left + right);
            } else if (startAdj <= endAdj && st != null) {
                activeSections = Math.max(activeSections, ones + st.query(startAdj, endAdj));
            }

            int rBoundGi = (s.charAt(r) == '1') ? rgi : rgi - 1;
            if (s.charAt(l) == '0' && lgi + 1 <= rBoundGi) {
                activeSections = Math.max(activeSections, ones + left + zeroGroups.get(lgi + 1)[1]);
            }
            if (s.charAt(r) == '0' && lgi < rgi - 1) {
                activeSections = Math.max(activeSections, ones + right + zeroGroups.get(rgi - 1)[1]);
            }

            ans.add(activeSections);
        }

        return ans;
    }

    static class SparseTable {
        int[][] st;
        int n;

        SparseTable(int[] nums) {
            n = nums.length;
            int logn = 32 - Integer.numberOfLeadingZeros(Math.max(n, 1));
            st = new int[logn + 1][n];
            for (int i = 0; i < n; i++) st[0][i] = nums[i];
            for (int k = 1; k <= logn; k++) {
                for (int i = 0; i + (1 << k) <= n; i++) {
                    st[k][i] = Math.max(st[k - 1][i], st[k - 1][i + (1 << (k - 1))]);
                }
            }
        }

        int query(int l, int r) {
            int len = r - l + 1;
            int k = 31 - Integer.numberOfLeadingZeros(len);
            return Math.max(st[k][l], st[k][r - (1 << k) + 1]);
        }
    }
}