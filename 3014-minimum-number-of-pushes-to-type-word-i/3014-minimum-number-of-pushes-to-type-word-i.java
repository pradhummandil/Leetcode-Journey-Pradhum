class Solution {
    public int minimumPushes(String word) {
        int n = word.length();
        int totalPushes = 0;
        int pushLevel = 1;
        int count = 0;

        for (int i = 0; i < n; i++) {
            totalPushes += pushLevel;
            count++;
            if (count == 8) {
                count = 0;
                pushLevel++;
            }
        }

        return totalPushes;
    }
}