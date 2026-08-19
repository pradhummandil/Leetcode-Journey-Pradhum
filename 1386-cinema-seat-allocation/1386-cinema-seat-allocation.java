import java.util.*;

class Solution {
    public int maxNumberOfFamilies(int n, int[][] reservedSeats) {
        Map<Integer, Integer> rowMask = new HashMap<>();

        for (int[] r : reservedSeats) {
            int row = r[0], seat = r[1];
            if (seat < 2 || seat > 9) continue; // seats 1 and 10 don't affect any block
            int bit = 1 << (seat - 2); // bits 0..7 correspond to seats 2..9
            rowMask.merge(row, bit, (a, b) -> a | b);
        }

        int leftBlock = 0b00001111;  // seats 2,3,4,5
        int midBlock = 0b00111100;   // seats 4,5,6,7
        int rightBlock = 0b11110000; // seats 6,7,8,9

        int totalRows = rowMask.size();
        int result = (n - totalRows) * 2;

        for (int mask : rowMask.values()) {
            boolean leftFree = (mask & leftBlock) == 0;
            boolean midFree = (mask & midBlock) == 0;
            boolean rightFree = (mask & rightBlock) == 0;

            if (leftFree && rightFree) {
                result += 2;
            } else if (leftFree || midFree || rightFree) {
                result += 1;
            }
        }

        return result;
    }
}