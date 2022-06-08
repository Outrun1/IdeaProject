package algorithm.exp_1;

public class Sort {

    /**
     * 数组内元素做交换,避免后面重复写交换
     */
    static void swap(int[] nums, int i, int j){
        int temp = nums[j];
        nums[j] = nums[i];
        nums[i] = temp;
    }

    /**
     * 选择排序
     */
    static void selectSort(int[] nums) {
        for(int i = 0; i < nums.length - 1; i++) {
            int min = nums[i];
            int position = i;
            //存储最小元素及其所在的位置
            for(int j = i + 1; j < nums.length; j++) {
                if(nums[j] < min) {
                    min = nums[j];
                    position = j;
                }
            }
            swap(nums, i, position);
        }
    }

    /**
     * 冒泡排序
     */
    static void bubblingSort(int[] nums) {
        for(int i = 0; i < nums.length - 1; i++) {
            for(int j = 1; j < nums.length - i; j++) {
                if(nums[j - 1] > nums[j]) {
                    swap(nums, j - 1, j);
                }
            }
        }
    }

    /**
     * 二路归并排序
     */
    static void mergeSort(int[] nums, int left, int right) {
        if(left < right) {
            int mid = (left + right) / 2;
            mergeSort(nums, left, mid);
            mergeSort(nums, mid + 1, right);
            selectSort(nums, left, right);
        }
    }

    /**
     * 二路归并排序实现，相当于对指定长度的一段做选择排序
     */
    private static void selectSort(int[] nums, int left, int right) {
        int mid = (left + right) / 2;
        int i = left;
        int j = mid + 1;
        int[] temp = new int[right - left + 1];
        //暂时存储nums数组左右俩段中较小部分
        int k = 0;
        while (i <= mid && j <= right) {
            //直到左右有一个方向遍历完毕
            if (nums[i] < nums[j]) {
                temp[k++] = nums[i++];
            }
            else {
                temp[k++] = nums[j++];
            }
        }
        while (i <= mid) {
            //剩下元素存储进temp中
            temp[k++] = nums[i++];
        }
        while (j <= right) {
            //剩下元素存储进temp中
            temp[k++] = nums[j++];
        }
        k = 0;
        while (left <= right) {
            //temp放回nums中
            nums[left++] = temp[k++];
        }
    }

    /**
     * 快速排序
     */
    static void quickSort(int[] nums, int left, int right) {
        if(right <= left) {
            return;
        }
        int flag = nums[left];
        int l = left, r = right;
        while (l < r) {
            while (l < r && nums[r] >= flag) {
                r--;
            }
            nums[l] = nums[r];
            while (l < r && nums[l] <= flag) {
                l++;
            }
            nums[r] = nums[l];
        }
        nums[l] = flag;
        quickSort(nums, left, l - 1);
        quickSort(nums, l + 1, right);
    }

    /**
     * 插入排序
     */
    static void insertSort(int[] nums) {
        for (int i = 1; i < nums.length; i++) {
            int j = i, num = nums[j];
            while(j > 0 && nums[j - 1] > num) {
                nums[j] = nums[j - 1];
                j--;
            }
            nums[j] = num;
        }
    }

    public static void main(String[] args) {

        long Tselect = 0;
        long Tbubbling = 0;
        long Tmerge = 0;
        long Tquick = 0;
        long Tinsert = 0;

        for (int i = 0; i < 20; i++) {

            //初始化随机数数组，并做好准备工作
            int n = 100000;
            int[] nums = new int[n];
            long startTime, endTime;
            int[] temp;
            for (int j = 0; j < n; j++) {
                nums[j] = (int) (Math.random() * 5000000);
            }

            //选择排序
            System.out.println("选择排序：");
            temp = nums.clone();
//            System.out.println("排序前" + Arrays.toString(temp));
            startTime = System.nanoTime();
            //获取开始时间
            selectSort(temp);
            endTime = System.nanoTime();
            //获取结束时间
//            System.out.println("排序后" + Arrays.toString(temp));
            System.out.println("运行时间： " + (endTime - startTime) + "ns");
            Tselect += (endTime - startTime);

            //冒泡排序
            System.out.println("冒泡排序：");
            temp = nums.clone();
//            System.out.println("排序前" + Arrays.toString(temp));
            startTime = System.nanoTime();
            //获取开始时间
            bubblingSort(temp);
            endTime = System.nanoTime();
            //获取结束时间
//            System.out.println("排序后" + Arrays.toString(temp));
            System.out.println("运行时间： " + (endTime - startTime) + "ns");
            Tbubbling += (endTime - startTime);

            //归并排序
            System.out.println("归并排序：");
            temp = nums.clone();
//            System.out.println("排序前" + Arrays.toString(temp));
            startTime = System.nanoTime();
            //获取开始时间
            mergeSort(temp, 0, temp.length - 1);
            endTime = System.nanoTime();
            //获取结束时间
//            System.out.println("排序后" + Arrays.toString(temp));
            System.out.println("运行时间： " + (endTime - startTime) + "ns");
            Tmerge += (endTime - startTime);

            //快速排序
            System.out.println("快速排序：");
            temp = nums.clone();
//            System.out.println("排序前" + Arrays.toString(temp));
            startTime = System.nanoTime();
            //获取开始时间
            quickSort(temp, 0, temp.length - 1);
            endTime = System.nanoTime();
            //获取结束时间
//            System.out.println("排序后" + Arrays.toString(temp));
            System.out.println("运行时间： " + (endTime - startTime) + "ns");
            Tquick += (endTime - startTime);

            //插入排序
            System.out.println("插入排序：");
            temp = nums.clone();
//            System.out.println("排序前" + Arrays.toString(temp));
            startTime = System.nanoTime();
            //获取开始时间
            insertSort(temp);
            endTime = System.nanoTime();
            //获取结束时间
//            System.out.println("排序后" + Arrays.toString(temp));
            System.out.println("运行时间： " + (endTime - startTime) + "ns");
            Tinsert += (endTime - startTime);

        }
        Tselect /= 20;
        Tbubbling /= 20;
        Tmerge /= 20;
        Tquick /= 20;
        Tinsert /= 20;

        System.out.println("选择排序平均时间：" + Tselect + "ns");
        System.out.println("冒泡排序平均时间：" + Tbubbling + "ns");
        System.out.println("归并排序平均时间：" + Tmerge + "ns");
        System.out.println("快速排序平均时间：" + Tquick + "ns");
        System.out.println("插入排序平均时间：" + Tinsert + "ns");
    }

}
