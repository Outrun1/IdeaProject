package algorithm.exp_5;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @author outrun
 * @date 2022/6/2
 * @apiNote
 */
public class Bridge {

    static List<List<Integer>> adj; // 邻接表
    static int vertex; // 点的数目
    static List<int[]> res;
    static boolean[] visit;
    static Map<Integer, Integer>[] position;
    static int[] father;
    static void create(int v) {
        vertex = v;
        adj = new ArrayList<>();
        position = new HashMap[vertex];
        father = new int[vertex];
        for (int i = 0; i < vertex; i++) {
            adj.add(new ArrayList<>());
            position[i] = new HashMap<>();
        }
        res = new ArrayList<>();
        visit = new boolean[vertex];

    }

    static void randomCreate(boolean isDouble) {
        int line = vertex;
        if (isDouble)
            line *= vertex;
        HashSet<int[]> set = new HashSet<>();
        while (line-- > 0) {
            int[] num = new int[] {(int)(Math.random() * vertex), (int)(Math.random() * vertex)};
            while (set.contains(num) || num[0] == num[1]) {
                num = new int[] {(int)(Math.random() * vertex), (int)(Math.random() * vertex)};
            }
            set.add(num);
            set.add(new int[] {num[1], num[0]});
            int i = num[0];
            int j = num[1];
            List<Integer> listI = Bridge.adj.get(i);
            List<Integer> listJ = Bridge.adj.get(j);
            position[i].put(j, listI.size());
            listI.add(j);
            position[j].put(i, listJ.size());
            listJ.add(i);
        }
    }

    static void createSet() {
        for (int k = 0; k < vertex; k++) {
            father[k] = k;
        }
        for (int i = 0; i < adj.size(); i++) {
            List<Integer> listI = adj.get(i);
            for (int index = 0; index < listI.size(); index++) {
                int j = listI.get(index);
                if (j < i || father[i] == father[j])
                    continue;
                j = getParent(j);
                father[j] = getParent(i);
            }
        }
    }

    static int getParent(int i) {
        int parent = i;
        while (father[parent] != parent) {
            parent = father[parent];
        }
        while (father[i] != i) {
            int temp = father[i];
            father[i] = parent;
            i = temp;
        }
        return parent;
    }

    static void BFS() {
        for (int i = 0; i < adj.size(); i++) {
            List<Integer> listI = adj.get(i);
            for (int index = 0; index < listI.size(); index++) {
                if (listI.get(index) < i)
                    continue;
                // 通过比较连通分量判断是否为桥
                //int before = calculateConnect();
                //Arrays.fill(visit, false);
                //int j = listI.remove(index);
                //List<Integer> listJ = adj.get(j);
                //listJ.remove((int) position[j].get(i));
                //int after = calculateConnect();
                //Arrays.fill(visit, false);
                //listI.add(index, j);
                //listJ.add(position[j].get(i), i);
                //if (before != after)
                //    res.add(new int[] {i, j});

                // 通过删除边后能否搜索到另一点判断是否为桥
                //int j = listI.remove(index);
                //List<Integer> listJ = adj.get(j);
                //listJ.remove((int) position[j].get(i));
                //DFS(i);
                //listI.add(index, j);
                //listJ.add(position[j].get(i), i);
                //if (!visit[j])
                //    res.add(new int[] {i, j});
                //Arrays.fill(visit, false);

                // 通过使用并查集，判断删除边后是否处于同一集合
                int j = listI.remove(index);
                List<Integer> listJ = adj.get(j);
                listJ.remove((int) position[j].get(i));
                createSet();
                listI.add(index, j);
                listJ.add(position[j].get(i), i);
                if (getParent(i) != getParent(j))
                    res.add(new int[] {i, j});
                Arrays.fill(visit, false);
            }
        }
    }

    static int calculateConnect() {
        int sum = 0;
        for (int i = 0; i < adj.size(); i++) {
            if (visit[i])
                continue;
            visit[i] = true;
            sum++;
            List<Integer> list = adj.get(i);
            for (int j : list) {
                if (!visit[j]) {
                    DFS(j);
                }
            }
        }
        return sum;
    }

    static void DFS(int i) {
        visit[i] = true;
        List<Integer> list = adj.get(i);
        for (int j : list) {
            if (!visit[j]) {
                DFS(j);
            }
        }
    }

    public static void main(String[] args) throws IOException {

        File file1 = new File("C:\\Users\\hp\\Desktop\\课件\\大三下\\算法\\实验5\\largeG.txt");
        File file2 = new File("C:\\Users\\hp\\Desktop\\课件\\大三下\\算法\\实验5\\mediumG.txt");
        File test = new File("C:\\Users\\hp\\Desktop\\课件\\大三下\\算法\\实验5\\test.txt");
        Scanner reader;
        Scanner scanner = new Scanner(System.in);
        int file = scanner.nextInt();
        int t = 20;
        long time = 0;
        while (t-- > 0) {

            long startTime, endTime;

            // 读取文件初始化邻接表
            if (file == 1) {
                reader = new Scanner(file1);
            } else if (file == 2) {
                reader = new Scanner(file2);
            } else {
                reader = new Scanner(test);
            }

            int vertex = reader.nextInt();
            int line = reader.nextInt();
            Bridge.create(vertex);
            while(reader.hasNext()){
                int i = reader.nextInt();
                int j = reader.nextInt();
                List<Integer> listi = Bridge.adj.get(i);
                List<Integer> listj = Bridge.adj.get(j);
                position[i].put(j, listi.size());
                listi.add(j);
                position[j].put(i, listj.size());
                listj.add(i);
            }

            // 随机生成邻接表
            //int vertex = 180;
            //Bridge.create(vertex);
            //Bridge.randomCreate(false);

            // 输出邻接表
            //System.out.println("邻接表如下：");
            //for (int i = 0; i < Bridge.adj.size(); i++) {
            //    List<Integer> temp = Bridge.adj.get(i);
            //    System.out.printf(i + ":");
            //    for (int temp2 : temp) {
            //        System.out.printf(temp2 + " ");
            //    }
            //    System.out.println();
            //}
            startTime = System.nanoTime();
            Bridge.BFS();
            endTime = System.nanoTime();
            time += endTime - startTime;
            System.out.println("运行时间： " + (endTime - startTime) + "ns");

            // 输出桥
            System.out.println("总共有" + Bridge.res.size() + "座桥，分别如下：");
            for (int[] temp : Bridge.res) {
                System.out.println(temp[0] + " " + temp[1]);
            }
        }

        System.out.println("20次运行平均时间： " + time / 20 + "ns");
    }
}
