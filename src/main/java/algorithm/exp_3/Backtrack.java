package algorithm.exp_3;

import java.io.*;
import java.util.*;


public class Backtrack {
    long ans;
    int sum;
    int vertex; // 点的数目
    int colorNum; // 颜色数量
    boolean[] visit;
    int[] color;
    int[][] colorMatrix;
    List<int[]> list;
//    boolean[][] matrix; // 邻接矩阵
    List<List<Integer>> adj; // 邻接表

    Backtrack(int vertex, int colorNum) {
        this.vertex = vertex;
        this.colorNum = colorNum;
        color = new int[vertex];
        visit = new boolean[vertex];
//        matrix = new boolean[vertex][vertex];
        colorMatrix = new int[vertex][colorNum + 1];
        for (int i = 0; i < vertex; i++) {
            colorMatrix[i][0] = colorNum;
            for (int j = 1; j <= colorNum; j++) {
                colorMatrix[i][j] = 1;
            }
        }
        adj = new ArrayList<>();
        for (int i = 0; i < vertex; i++) {
            adj.add(new ArrayList<>());
        }
        list = new ArrayList<>();
        for (int i = 0; i < vertex; i++) {
            list.add(new int[]{i, 0});
        }
    }

    void dfs(int x) {

        sum++;
        // 如果已经涂完整个地图，即找到可行解
        if (x == vertex) {
            ans++;
            System.out.println(Arrays.toString(color));
            return;
        }
        int xPoint = list.get(x)[0];
        // 对每个点进行涂色
        int i;
        boolean[] same = new boolean[vertex];
        for (i = 1; i <= colorNum; i++) {
            if (colorMatrix[xPoint][i] == 1 && !same[i - 1]) {
                int colorSum = 1;
                boolean first = true;
                if (visit[i - 1]) {
                    first = false;
                }
                color[xPoint] = i;// 模拟涂色
                // 邻接表
                List<Integer> row = adj.get(xPoint);
                int length = row.size();
                boolean[] del = new boolean[length];
                int j;
                for (j = 0; j < length; j++) {
                    int jPoint = row.get(j);
                    if (colorMatrix[jPoint][i] == 1) {
                        colorMatrix[jPoint][i] = 0;
                        colorMatrix[jPoint][0]--;
                        del[j] = true;
                    }
                    if (colorMatrix[jPoint][0] == 0) {
                        break;
                    }
                }
                if (j == length) {
                    for (int k = i + 1; k <= colorNum; k++) {
                        if (colorMatrix[xPoint][k] == 1 && !visit[k - 1]) {
                            colorSum++;
                            same[k - 1] = true;
                        }
                    }
                    visit[i - 1] = true;
                    dfs(x + 1); // 当前解合法，对当前节点进行拓展搜索
                    visit[i - 1] = false;
                }
                color[xPoint] = 0; // 模拟取消颜色
                for (int k = 0; k < j; k++) {
                    int kPoint = row.get(k);
                    if (colorMatrix[kPoint][i] == 0 && del[k]) {
                        colorMatrix[kPoint][i] = 1;
                        colorMatrix[kPoint][0]++;
                    }
                }
                if (first && j == length) {
                    ans *= colorSum;
                }
            }
        }
//        if (x != 0) {

//            for (int i = 1; i <= colorNum; i++) {
//
//                color[xPoint] = i;// 模拟涂色
//                // 进行涂色的合法性检测
//
//                // 邻接表
//                List<Integer> row = adj.get(xPoint);
//                int length = row.size();
//
//                int j;
//                for (j = 0; j < length; j++) {
//                    if (color[xPoint] == color[row.get(j)]) {
//                        break;
//                    }
//                }
//                if (j == length) {
//                    dfs(x + 1); // 当前解合法，对当前节点进行拓展搜索
//                }
//
//                // 邻接矩阵
//                int j;
//                for (j = 0; j < x; j++) {
//                    int jPoint = list.get(j)[0];
//                    if ((matrix[jPoint][xPoint] || matrix[xPoint][jPoint]) && color[xPoint] == color[jPoint]) {
//                        break;//非法，重新染色
//                    }
//                }
//                if (j == x) {
//                    dfs(x + 1);//当前解合法，对当前节点进行拓展搜索
//                }
//                color[xPoint] = 0; // 模拟取消颜色
//            }
//        }
//        else {
//            color[xPoint] = 1;// 模拟涂色
//            List<Integer> row = adj.get(xPoint);
//            for (int j = 0; j < row.size(); j++) {
//                int jPoint = row.get(j);
//                colorMatrix[jPoint][1] = 0;
//                colorMatrix[jPoint][0]--;
//            }
//            dfs(x + 1); // 当前解合法，对当前节点进行拓展搜索
//            ans *= colorNum;
//        }
    }

    public static void main(String[] args) throws IOException {

        File file1 = new File("C:\\Users\\hp\\Desktop\\课件\\大三下\\算法\\le450_5a.txt");
        File file2 = new File("C:\\Users\\hp\\Desktop\\课件\\大三下\\算法\\le450_15b.txt");
        File file3 = new File("C:\\Users\\hp\\Desktop\\课件\\大三下\\算法\\le450_25a.txt");
        File test = new File("C:\\Users\\hp\\Desktop\\课件\\大三下\\算法\\test.txt");
        Scanner reader;
        Scanner scanner = new Scanner(System.in);
        int file = scanner.nextInt();

        int t = 20;
        long time = 0;
        while (t-- > 0) {

            long startTime, endTime;
            if (file == 1) {
                reader = new Scanner(file1);
            } else if (file == 2) {
                reader = new Scanner(file2);
            } else if (file == 3)  {
                reader = new Scanner(file3);
            } else {
                reader = new Scanner(test);
            }

            int vertex = reader.nextInt();
            int colorNum = reader.nextInt();
            int line = reader.nextInt();

            Backtrack backtrack = new Backtrack(vertex, colorNum);

            while(reader.hasNext()){
                int i = reader.nextInt();
                int j = reader.nextInt();
                backtrack.list.get(i - 1)[1]++;
                backtrack.list.get(j - 1)[1]++;
                backtrack.adj.get(i - 1).add(j - 1);
                backtrack.adj.get(j - 1).add(i - 1);
//                backtrack.matrix[i - 1][j - 1] = true;
            }
            Collections.sort(backtrack.list, (o1, o2) -> o2[1] - o1[1]);
//            // 输出排序
//            for (int[] temp:
//                 backtrack.list) {
//                System.out.println(temp[0] + "," + temp[1]);
//            }
//            // 输出邻接表
//            for (int i = 0; i < backtrack.adj.size(); i++) {
//                List<Integer> temp = backtrack.adj.get(i);
//                System.out.printf(i + ":");
//                for (int temp2 : temp) {
//                    System.out.printf(temp2 + " ");
//                }
//                System.out.println();
//            }
            startTime = System.nanoTime();
            backtrack.dfs(0);
            endTime = System.nanoTime();
            time += endTime - startTime;
            System.out.println("运行时间： " + (endTime - startTime) + "ns");
//            System.out.println("解数量： " + backtrack.ans);
//            System.out.println("回溯次数： " + backtrack.sum);
        }

        System.out.println("20次运行平均时间： " + time / 20 + "ns");
    }
}
