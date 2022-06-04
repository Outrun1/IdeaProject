package algorithm.exp_2;
import java.util.*;

class point {
    double x;
    double y;
    double getDistance(point p) {
        return Math.sqrt((x - p.x) * (x - p.x) + (y - p.y) * (y - p.y));
    }
}

public class DivideAndConquer {
    /**
     * 暴力求解最近点对
     * param points : 点的数组
     * return       : 最近点对距离
     **/
    static double violent(List<point> points)
    {
        double ans = Double.MAX_VALUE;
        for(int i = 0; i < points.size(); i++)
            for(int j = i + 1; j < points.size(); j++)
                ans = Math.min(ans, points.get(i).getDistance(points.get(j)));
        return ans;
    }

    /**
     * 分治求解最近点对，复杂度O(nlog(n)log(n))
     * param points : 点的数组
     * param left   : 点数组左端点
     * param right  : 点数组右端点
     * return       : 最近点对距离
     * explain      : 区间[left, right]左闭右闭
    **/
    static double Divide(List<point> points, int left, int right)
    {
        if(left >= right)
            return Double.MAX_VALUE;
        int mid = (left + right) / 2, le = mid, ri = mid + 1, h = 0;
        double d = Math.min(Divide(points, left, mid), Divide(points, mid + 1, right));
        double ans = d;
        List<point> ll, rr;
        ll = new ArrayList<>();
        rr = new ArrayList<>();
        while(le >= left && disX(points.get(mid), points.get(le)) <= d) {
            ll.add(points.get(le));
            le--;
        }
        while(ri <= right && disX(points.get(mid), points.get(ri)) <= d) {
            rr.add(points.get(ri));
            ri++;
        }
        Collections.sort(ll, (o1, o2) -> (int) (o1.y - o2.y));
        Collections.sort(rr, (o1, o2) -> (int) (o1.y - o2.y));
        for(int i = 0; i < ll.size(); i++) {
            while (h < rr.size() && rr.get(h).y < ll.get(i).y - d)
                h++;
            for (int j = h; j < rr.size() && j < h + 6; j++)
                ans = Math.min(ans, ll.get(i).getDistance(rr.get(j)));
        }
        return Math.min(ans, d);
    }

    /**
     * 分治归并求解最近点对，复杂度O(nlog(n))
     * param points : 点的数组
     * param left   : 点数组左端点
     * param right  : 点数组右端点
     * return       : 最近点对距离
     * explain      : 区间[left, right]左闭右闭
     **/
    static double DivideAndMerge(List<point> points, int left, int right)
    {
        if (left >= right)
            return Double.MAX_VALUE;

        int mid = (left + right) / 2, le = mid, ri = mid + 1, h = 0;
        double d = Math.min(DivideAndMerge(points, left, mid), DivideAndMerge(points, mid + 1, right));
        double ans = d;
        List<point> ll, rr;
        ll = new ArrayList<>();
        rr = new ArrayList<>();
        while(le >= left && disX(points.get(mid), points.get(le)) <= d) {
            ll.add(points.get(le));
            le--;
        }
        while(ri <= right && disX(points.get(mid), points.get(ri)) <= d) {
            rr.add(points.get(ri));
            ri++;
        }
        if (le < left)
            le = left;
        if (ri > right)
            ri = right;
        for(int i = 0; i < ll.size(); i++) {
            while (h < rr.size() && rr.get(h).y < ll.get(i).y - d)
                h++;
            for (int j = h; j < rr.size() && j < h + 6; j++)
                ans = Math.min(ans, ll.get(i).getDistance(rr.get(j)));
        }
        mergeSort(points, le, ri);
        return ans;
    }

    private static void mergeSort(List<point> points, int left, int right) {
        int mid = (left + right) / 2;
        int i = left;
        int j = mid + 1;
        List<point> temp = new ArrayList<>();
        //暂时存储points数组左右俩段中较小部分
        while (i <= mid && j <= right) {
            //直到左右有一个方向遍历完毕
            if (points.get(i).y < points.get(j).y) {
                temp.add(points.get(i++));
            } else {
                temp.add(points.get(j++));
            }
        }
        while (i <= mid) {
            //剩下元素存储进temp中
            temp.add(points.get(i++));
        }
        while (j <= right) {
            //剩下元素存储进temp中
            temp.add(points.get(j++));
        }
        int k = 0;
        while (left <= right) {
            //temp放回points中
            points.set(left++, temp.get(k++));
        }
    }

    /**
     * 求两点水平距离
     * param p1 : 第一个点
     * param p2 : 第二个点
     * return   : 水平距离
     **/
    static double disX(point p1, point p2)
    {
        double ans = p1.x - p2.x;
        if(ans < 0)
            return -ans;
        return ans;
    }

    public static void main(String[] args) {

        long Tviolent = 0;
        long Tdivde = 0;
        long TdivdeMerge = 0;

        for (int i = 0; i < 20; i++) {

            //初始化随机数数组，并做好准备工作
            int n = 500000;
            List<point> points = new ArrayList<>();
            long startTime, endTime;
            List<point> temp;
            for (int j = 0; j < n; j++) {
                point p = new point();
                p.x = Math.random() * 50000000;
                p.y = Math.random() * 50000000;
                points.add(p);
            }
            points.sort((o1, o2) -> {
                if (o1.x != o2.x)
                    return (int) (o1.x - o2.x);
                return (int) (o1.y - o2.y);
            });
            //暴力
            System.out.println("暴力：");
            temp = new ArrayList<>(points);
            startTime = System.nanoTime();
            //获取开始时间
            System.out.println(violent(temp));
            endTime = System.nanoTime();
            //获取结束时间
            System.out.println("运行时间： " + (endTime - startTime) + "ns");
            Tviolent += (endTime - startTime);

            //分治
            System.out.println("分治：");
            temp = new ArrayList<>(points);
            startTime = System.nanoTime();
            //获取开始时间
            System.out.println(Divide(temp, 0, temp.size() - 1));
            endTime = System.nanoTime();
            //获取结束时间
            System.out.println("运行时间： " + (endTime - startTime) + "ns");
            Tdivde += (endTime - startTime);

            //分治+归并
            System.out.println("分治归并：");
            temp = new ArrayList<>(points);
            startTime = System.nanoTime();
            //获取开始时间
            System.out.println(DivideAndMerge(temp, 0, temp.size() - 1));
            endTime = System.nanoTime();
            //获取结束时间
            System.out.println("运行时间： " + (endTime - startTime) + "ns");
            TdivdeMerge += (endTime - startTime);


        }
        Tviolent /= 20;
        Tdivde /= 20;
        TdivdeMerge /= 20;


        System.out.println("暴力平均时间：" + Tviolent + "ns");
        System.out.println("分治平均时间：" + Tdivde + "ns");
        System.out.println("分治归并平均时间：" + TdivdeMerge + "ns");

    }
}
