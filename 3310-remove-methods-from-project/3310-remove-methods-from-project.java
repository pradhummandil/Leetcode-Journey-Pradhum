import java.util.*;

class Solution {
    public List<Integer> remainingMethods(int n, int k, int[][] invocations) {
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
        for (int[] inv : invocations) {
            adj.get(inv[0]).add(inv[1]);
        }

        boolean[] suspicious = new boolean[n];
        Deque<Integer> stack = new ArrayDeque<>();
        stack.push(k);
        suspicious[k] = true;

        while (!stack.isEmpty()) {
            int u = stack.pop();
            for (int v : adj.get(u)) {
                if (!suspicious[v]) {
                    suspicious[v] = true;
                    stack.push(v);
                }
            }
        }

        for (int[] inv : invocations) {
            int a = inv[0], b = inv[1];
            if (!suspicious[a] && suspicious[b]) {
                List<Integer> result = new ArrayList<>();
                for (int i = 0; i < n; i++) result.add(i);
                return result;
            }
        }

        List<Integer> remaining = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (!suspicious[i]) remaining.add(i);
        }

        return remaining;
    }
}