package others;

public class PrimeCounter {
    public static void main(String[] args) {
        int n = 100; // 假设我们要找出100以内的素数个数
        int count = countPrimes(n);
        System.out.println("The number of primes less than or equal to " + n + " is: " + count);
    }

    /**
     * 计算小于或等于n的素数个数
     *
     * @param n 整数，计算范围的上限
     * @return 素数的个数
     */
    public static int countPrimes(int n) {
        boolean[] isPrime = new boolean[n + 1]; // 创建一个布尔数组，初始假设所有数都是素数

        // 初始化所有索引为true，除了0和1
        for (int i = 2; i <= n; i++) {
            isPrime[i] = true;
        }

        // 埃拉托斯特尼筛法
        for (int p = 2; p * p <= n; p++) {
            // 如果isPrime[p]未改变，那么它是一个素数
            if (isPrime[p]) {
                // 更新所有p的倍数为非素数
                for (int i = p * p; i <= n; i += p) {
                    isPrime[i] = false;
                }
            }
        }

        // 计算素数个数
        int count = 0;
        for (int i = 2; i <= n; i++) {
            if (isPrime[i]) {
                count++;
            }
        }

        return count;
    }
}