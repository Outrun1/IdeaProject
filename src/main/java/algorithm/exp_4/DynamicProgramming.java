package algorithm.exp_4;

public class DynamicProgramming {

    static long[][] record;
    static long backtrack(int[] nums, int left, int right) {
        if (left == right)
            return nums[left];
        if (left > right)
            return 0;
        if (record[left][right] != 0)
            return record[left][right];
        long leftProfit = Math.min(backtrack(nums, left + 2, right), backtrack(nums, left + 1, right - 1)) + nums[left];
        long rightProfit = Math.min(backtrack(nums, left + 1, right - 1), backtrack(nums, left, right - 2)) + nums[right];
        record[left][right] = Math.max(leftProfit, rightProfit);
        return record[left][right];
    }

    /**
     * 动态规划
     * dp[i][j]为 i 到 j 先手可获得最大收益
     **/
    static long solution(int[] nums) {
        int n = nums.length;
        long[][] dp = new long[n + 4][n + 4];
        for (int len = 0; len < n; len++) {
            for (int i = 2; i < n - len + 2; i++) {
                long leftProfit = Math.min(dp[i + 2][i + len], dp[i + 1][i + len - 1]) + nums[i - 2];
                long rightProfit = Math.min(dp[i][i + len - 2], dp[i + 1][i + len - 1]) + nums[i + len - 2];
                dp[i][i + len] = Math.max(leftProfit, rightProfit);
            }
        }
        return dp[2][n + 1];
    }

    /**
     * 动态规划
     * first[i]为以i为起点，当前长度减2的子数组获取最大收益
     * second[i]为以i为起点，当前长度减1的子数组获取最大收益
     **/
    static long solutionN(int[] nums) {
        int n = nums.length;
        long[] first = new long[n + 2];
        long[] second = new long[n + 2];
        for (int len = 0; len < n; len++) {
            for (int i = 0; i < n - len; i++) {
                long leftProfit = Math.min(first[i + 2], first[i + 1]) + nums[i];
                long rightProfit = Math.min(first[i], first[i + 1]) + nums[i + len];
                first[i] = Math.max(leftProfit, rightProfit);
            }
            long[] temp = first;
            first = second;
            second = temp;
        }
        return second[0];
    }

    public static void main(String[] args) {
        double tBacktrack = 0;
        double tSolution = 0;
        double tSolutionN = 0;

        int t = 20;
        boolean flag = true;
        for (int i = 0; i < t; i++) {
            // 初始化随机数数组，并做好准备工作
            int n = 5000;
            int[] nums = new int[n];
            long sum = 0;
            long startTime, endTime;
            for (int j = 0; j < nums.length; j++) {
                nums[j] = (int) (Math.random() * 500000);
                sum += nums[j];
            }
//            nums = new int[] {6, 1, 4, 9, 8, 5};

            System.out.println("总收益：" + sum);
            // 暴力回溯
            System.out.println("暴力回溯：");
//            System.out.println("数组：" + Arrays.toString(nums));
            DynamicProgramming.record = new long[n][n];
            startTime = System.nanoTime();
            // 获取开始时间
            long aAns = backtrack(nums, 0, nums.length - 1);
            System.out.println("最大收益： " + aAns);
            endTime = System.nanoTime();
            // 获取结束时间
            System.out.println("运行时间： " + (endTime - startTime) / 10e5 + "ms");
            tBacktrack += (endTime - startTime) / 10e5;

            // 动态规划
            System.out.println("动态规划：");
            startTime = System.nanoTime();
            // 获取开始时间
            long bAns = solution(nums);
            System.out.println("最大收益： " + bAns);
            endTime = System.nanoTime();
            // 获取结束时间
            System.out.println("运行时间： " + (endTime - startTime) / 10e5 + "ms");
            tSolution += (endTime - startTime) / 10e5;

            // 动态规划优化
            System.out.println("动态规划优化：");
            startTime = System.nanoTime();
            // 获取开始时间
            long cAns = solutionN(nums);
            System.out.println("最大收益： " + cAns);
            endTime = System.nanoTime();
            // 获取结束时间
            System.out.println("运行时间： " + (endTime - startTime) / 10e5 + "ms");
            tSolutionN += (endTime - startTime) / 10e5;
            if (!(aAns == bAns && bAns == cAns))
                flag = false;
        }
        tBacktrack /= t;
        tSolution /= t;
        tSolutionN /= t;

        System.out.println(flag ? "算法正确" : "算法有误");
        System.out.println("暴力回溯平均时间：" + tBacktrack + "ms");
        System.out.println("动态规划平均时间：" + tSolution + "ms");
        System.out.println("动态规划优化平均时间：" + tSolutionN + "ms");

    }
}
