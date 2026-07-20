import java.util.*;

class Solution {
    public List<List<Integer>> shiftGrid(int[][] grid, int k) {
        int m = grid.length;
        int n = grid[0].length;
        int total = m * n;
        k = k % total;

        int[] flat = new int[total];
        int idx = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                flat[idx++] = grid[i][j];
            }
        }

        int[] shifted = new int[total];
        for (int i = 0; i < total; i++) {
            shifted[(i + k) % total] = flat[i];
        }

        List<List<Integer>> result = new ArrayList<>();
        idx = 0;
        for (int i = 0; i < m; i++) {
            List<Integer> row = new ArrayList<>();
            for (int j = 0; j < n; j++) {
                row.add(shifted[idx++]);
            }
            result.add(row);
        }

        return result;
    }
}