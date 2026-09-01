import java.util.*;

class Solution {
    public int minMoves(String[] classroom, int energy) {
        int m = classroom.length;
        int n = classroom[0].length();
        char[][] grid = new char[m][n];
        for (int i = 0; i < m; i++) {
            grid[i] = classroom[i].toCharArray();
        }

        int sr = -1, sc = -1;
        List<int[]> litters = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                char ch = grid[i][j];
                if (ch == 'S') {
                    sr = i; sc = j;
                } else if (ch == 'L') {
                    litters.add(new int[]{i, j});
                }
            }
        }

        int k = litters.size();
        if (k == 0) return 0;

        // map each litter position -> bit index
        Map<Integer, Integer> litterIndex = new HashMap<>();
        for (int idx = 0; idx < k; idx++) {
            int[] pos = litters.get(idx);
            litterIndex.put(pos[0] * n + pos[1], idx);
        }

        int fullMask = (1 << k) - 1;
        int ER = energy + 1;      // energy values 0..energy
        int maskN = 1 << k;

        // encode (r, c, e, mask) into a single int index
        // index = ((r * n + c) * ER + e) * maskN + mask
        boolean[] visited = new boolean[m * n * ER * maskN];

        int startMask = 0; // 'S' cell is never also 'L'
        if (startMask == fullMask) return 0;

        ArrayDeque<int[]> queue = new ArrayDeque<>();
        // state: [r, c, e, mask, dist]
        int startCode = encode(sr, sc, energy, startMask, n, ER, maskN);
        visited[startCode] = true;
        queue.offer(new int[]{sr, sc, energy, startMask, 0});

        int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            int r = cur[0], c = cur[1], e = cur[2], mask = cur[3], dist = cur[4];

            if (e <= 0) continue;

            for (int[] d : dirs) {
                int nr = r + d[0], nc = c + d[1];
                if (nr < 0 || nr >= m || nc < 0 || nc >= n) continue;
                char cell = grid[nr][nc];
                if (cell == 'X') continue;

                int ne = (cell == 'R') ? energy : e - 1;
                int nmask = mask;
                if (cell == 'L') {
                    Integer bit = litterIndex.get(nr * n + nc);
                    if (bit != null) {
                        nmask |= (1 << bit);
                    }
                }

                int code = encode(nr, nc, ne, nmask, n, ER, maskN);
                if (!visited[code]) {
                    visited[code] = true;
                    int ndist = dist + 1;
                    if (nmask == fullMask) {
                        return ndist;
                    }
                    queue.offer(new int[]{nr, nc, ne, nmask, ndist});
                }
            }
        }

        return -1;
    }

    private int encode(int r, int c, int e, int mask, int n, int ER, int maskN) {
        return ((r * n + c) * ER + e) * maskN + mask;
    }
}