package test;

public class EightQueen {
    //统计解决方案的个数
    static int count = 0;
    //定义max,表示一共有多少个皇后
    int max = 8;
    //定义数组,用于存储八皇后的解法
    int[] array = new int[max];

    public static void main(String[] args) {
        //获取1970-1-1 00:00:00 000到现在的毫秒数
        long nowTime = System.currentTimeMillis();
        long begin = System.currentTimeMillis();//记录程序开始时间

        EightQueen bhh = new EightQueen();
        bhh.check(0);
        System.out.println("八皇后问题的解决方法有:"+count+"种");

        long end = System.currentTimeMillis();//记录程序结束时间
        System.out.println("程序运行的毫秒数是:"+(end-begin)+"毫秒");
    }

    //编写一个方法放置第n+1个皇后
    private void check(int n){
        if (n == max){//如果n=8的话就说明最后一个皇后已经放置完成了,因为数组是从下标0开始的
            print();
            return;
        }
        //依次放入皇后，并判断是否冲突
        for (int i=0;i<max;i++){
            array[n] = i;//先把这个皇后n放在该行的第i列,也就是先放在第一个位置开始
            if (conflict(n)){//如果与上个皇后不冲突
                //接着开始放置第i+1个皇后
                check(n+1);
            }
            //如果冲突，就把这个这个皇后放在本行的后面一个位置上,就会继续执行array[n] = i;因为i是要先++的
        }
    }

    /**
     * 放置第n个皇后,就去检测皇后是否和前面已经摆放的皇后冲突
     * @param n 表示是第几个皇后
     * @return
     */
    private boolean conflict(int n){
        for (int i=0;i<n;i++){
            //array[i] == array[n] :判断第n个皇后和前面一个皇后是否在同一列
            //Math.abs(n-i) == Math.abs(array[n] - array[i])：判断第n个皇后和前面一个皇后是否在同一斜线
            //Math.abs(1-0) 等于 {Math.abs(array[1] - array[0])}==(1 - 0 = 1)
            if (array[i] == array[n] || Math.abs(n-i) == Math.abs(array[n] - array[i])){
                return false;
            }
        }
        return true;
    }

    //定义打印输出皇后的摆放位置
    public void print(){
        count++;
        for (int i=0;i<array.length;i++){
            System.out.print(array[i]+" ");
        }
        System.out.println();
    }
}
