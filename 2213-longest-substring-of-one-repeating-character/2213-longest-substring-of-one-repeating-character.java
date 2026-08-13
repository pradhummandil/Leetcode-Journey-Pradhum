import java.util.*;

class Solution {
    public int[] longestRepeating(String s, String queryCharacters, int[] queryIndices) {
        int n = s.length();
        char[] actualChar = s.toCharArray();

        TreeMap<Integer, Integer> segStart = new TreeMap<>(); // start -> end
        TreeMap<Integer, Integer> segEnd = new TreeMap<>();   // end -> start
        TreeMap<Integer, Integer> lengthFreq = new TreeMap<>(); // length -> count

        int i = 0;
        while (i < n) {
            int j = i;
            while (j + 1 < n && actualChar[j + 1] == actualChar[i]) j++;
            addSegment(segStart, segEnd, lengthFreq, i, j);
            i = j + 1;
        }

        int k = queryCharacters.length();
        int[] result = new int[k];

        for (int q = 0; q < k; q++) {
            int idx = queryIndices[q];
            char c = queryCharacters.charAt(q);
            char oldChar = actualChar[idx];

            if (oldChar != c) {
                int start = segStart.floorKey(idx);
                int end = segStart.get(start);
                removeSegment(segStart, segEnd, lengthFreq, start, end);

                if (start <= idx - 1) addSegment(segStart, segEnd, lengthFreq, start, idx - 1);
                if (idx + 1 <= end) addSegment(segStart, segEnd, lengthFreq, idx + 1, end);

                actualChar[idx] = c;
                int newStart = idx, newEnd = idx;

                if (idx - 1 >= 0 && actualChar[idx - 1] == c) {
                    int leftStart = segEnd.get(idx - 1);
                    removeSegment(segStart, segEnd, lengthFreq, leftStart, idx - 1);
                    newStart = leftStart;
                }
                if (idx + 1 < n && actualChar[idx + 1] == c) {
                    int rightEnd = segStart.get(idx + 1);
                    removeSegment(segStart, segEnd, lengthFreq, idx + 1, rightEnd);
                    newEnd = rightEnd;
                }

                addSegment(segStart, segEnd, lengthFreq, newStart, newEnd);
            }

            result[q] = lengthFreq.lastKey();
        }

        return result;
    }

    private void addSegment(TreeMap<Integer,Integer> segStart, TreeMap<Integer,Integer> segEnd,
                             TreeMap<Integer,Integer> lengthFreq, int s, int e) {
        segStart.put(s, e);
        segEnd.put(e, s);
        int len = e - s + 1;
        lengthFreq.merge(len, 1, Integer::sum);
    }

    private void removeSegment(TreeMap<Integer,Integer> segStart, TreeMap<Integer,Integer> segEnd,
                                TreeMap<Integer,Integer> lengthFreq, int s, int e) {
        segStart.remove(s);
        segEnd.remove(e);
        int len = e - s + 1;
        int cnt = lengthFreq.get(len);
        if (cnt == 1) lengthFreq.remove(len);
        else lengthFreq.put(len, cnt - 1);
    }
}