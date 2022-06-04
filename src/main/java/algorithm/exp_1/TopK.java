package algorithm.exp_1;

import java.util.Arrays;
import java.util.PriorityQueue;

public class TopK {

    /**
     * 快速搜索TopK，相当于带剪枝的快速排序
     */
    static void quickSortTopK(int[] nums, int left, int right, int k) {
        if(right <= left) {
            return;
        }
        int flag = nums[left];
        int l = left, r = right;
        while (l < r) {
            while (l < r && nums[r] <= flag) {
                r--;
            }
            nums[l] = nums[r];
            while (l < r && nums[l] >= flag) {
                l++;
            }
            nums[r] = nums[l];
        }
        nums[l] = flag;
        if(l > k) {
            quickSortTopK(nums, left, l - 1, k);
        }
        else if(l < k) {
            quickSortTopK(nums, l + 1, right, k);
        }
    }

    /**
     * 搜索数组k遍，每次找出最大的
     */
    static int[] TopK_nk(int[] nums, int k) {
        int[] res = new int[k];
        Arrays.fill(res, Integer.MIN_VALUE);
        for (int i = 0; i < k; i++) {
            int maxIndex = 0;
            for (int j = 0; j < nums.length; j++) {
                if (res[k - i - 1] < nums[j]) {
                    res[k - i - 1] = nums[j];
                    maxIndex = j;
                }
            }
            nums[maxIndex] = Integer.MIN_VALUE;
        }
        return res;
    }

    /**
     * 维护小顶堆以实现TopK
     */
    static int[] TopKPriorityQueue(int[] nums, int k) {
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();
        int[] res = new int[k];
        for (int i = 0; i < nums.length; i++) {
            if (priorityQueue.size() < k) {
                priorityQueue.add(nums[i]);
            }
            else if (nums[i] > priorityQueue.peek()) {
                priorityQueue.add(nums[i]);
                priorityQueue.remove();
            }
        }
        for (int i = 0; i < k; i++) {
            res[i] = priorityQueue.remove();
        }
        return res;
    }

    public static void main(String[] args) {

        long Tsort = 0;
        long Tquick = 0;
        long Tnk = 0;
        long Tqueue = 0;

        for (int i = 0; i < 20; i++) {

            //初始化随机数数组，并做好准备工作
            int n = 500000;
            int k = 20;
            int[] nums = new int[n];
            long startTime, endTime;
            int[] temp;
            for (int j = 0; j < n; j++) {
                nums[j] = (int) (Math.random() * 5000000);
            }
//            System.out.println("排序前" + Arrays.toString(nums));

            //排序
            System.out.println("排序：");
            temp = nums.clone();
            startTime = System.nanoTime();
            //获取开始时间
            Arrays.sort(temp);
            endTime = System.nanoTime();
            //获取结束时间
            System.out.println("排序后" + Arrays.toString(Arrays.copyOfRange(temp, nums.length - k, nums.length)));
            System.out.println("运行时间： " + (endTime - startTime) + "ns");
            Tsort += (endTime - startTime);

            //搜索k次数组
            System.out.println("快速排序并剪枝：");
            temp = nums.clone();
            startTime = System.nanoTime();
            //获取开始时间
            quickSortTopK(temp, 0, nums.length - 1, k - 1);
            endTime = System.nanoTime();
            //获取结束时间
            System.out.println("排序后" + Arrays.toString(Arrays.copyOf(temp, k)));
            System.out.println("运行时间： " + (endTime - startTime) + "ns");
            Tquick += (endTime - startTime);

            //搜索k次数组
            System.out.println("搜索k次数组：");
            temp = nums.clone();
            startTime = System.nanoTime();
            //获取开始时间
            temp = TopK_nk(temp, k);
            endTime = System.nanoTime();
            //获取结束时间
            System.out.println("排序后" + Arrays.toString(temp));
            System.out.println("运行时间： " + (endTime - startTime) + "ns");
            Tnk += (endTime - startTime);

            //小顶堆
            System.out.println("小顶堆实现：");
            temp = nums.clone();
            startTime = System.nanoTime();
            //获取开始时间
            temp = TopKPriorityQueue(temp, k);
            endTime = System.nanoTime();
            //获取结束时间
            System.out.println("排序后" + Arrays.toString(temp));
            System.out.println("运行时间： " + (endTime - startTime) + "ns");
            Tqueue += (endTime - startTime);


        }
        Tsort /= 20;
        Tquick /= 20;
        Tnk /= 20;
        Tqueue /= 20;

        System.out.println("排序平均时间：" + Tsort + "ns");
        System.out.println("快排剪枝平均时间：" + Tquick + "ns");
        System.out.println("全搜索平均时间：" + Tnk + "ns");
        System.out.println("小顶堆平均时间：" + Tqueue + "ns");
    }
}
