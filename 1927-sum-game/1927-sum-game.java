class Solution {
    public boolean sumGame(String num) {
        int n = num.length();
        int cnt1 = 0, cnt2 = 0, sum1 = 0, sum2 = 0;

        for (int i = 0; i < n / 2; i++) {
            if (num.charAt(i) == '?') cnt1++;
            else sum1 += num.charAt(i) - '0';
        }
        for (int i = n / 2; i < n; i++) {
            if (num.charAt(i) == '?') cnt2++;
            else sum2 += num.charAt(i) - '0';
        }

        if ((cnt1 + cnt2) % 2 == 1) return true;

        return (sum1 - sum2) != 9 * (cnt2 - cnt1) / 2;
    }
}