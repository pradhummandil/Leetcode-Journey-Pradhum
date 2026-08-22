class Solution {
    public boolean checkDivisibility(int n) {
        int digitSum = 0;
        int digitProduct = 1;
        int temp = n;

        while (temp > 0) {
            int d = temp % 10;
            digitSum += d;
            digitProduct *= d;
            temp /= 10;
        }

        return n % (digitSum + digitProduct) == 0;
    }
}