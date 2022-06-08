package algorithm.exp_5;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @author DWM
 * @date 2022/6/2
 * @apiNote
 */
public class Bridge {

    static List<List<Integer>> adj; // 邻接表
    static int vertex; // 点的数目
    static List<int[]> res;
    static boolean[] visit;
    static Map<Integer, Integer>[] position; // 一个点在另一个点的哪个位置（该点上的第几条边连接另一点）
    static int[] father; // 并查集
    static int[] deep; // 并查集深度
    static HashSet<List<Integer>> del; // 非桥边
    static List<List<Integer>> tree; // 生成树
    static HashSet<Integer>[] treeSet; // 为后面判断不在生成树的边做准备
    static void create(int v) {
        vertex = v;
        adj = new ArrayList<>();
        tree = new ArrayList<>();
        position = new HashMap[vertex];
        father = new int[vertex];
        deep = new int[vertex];
        del = new HashSet<>();
        treeSet = new HashSet[vertex];
        for (int i = 0; i < vertex; i++) {
            adj.add(new ArrayList<>());
            tree.add(new ArrayList<>());
            position[i] = new HashMap<>();
            treeSet[i] = new HashSet<>();
        }
        res = new ArrayList<>();
        visit = new boolean[vertex];
    }

    // 随机生成邻接表
    static void randomCreate(boolean isDouble) {
        int line = vertex;
        if (isDouble)
            line *= vertex / 10;
        HashSet<List<Integer>> set = new HashSet<>();
        while (line-- > 0) {
            int i = (int)(Math.random() * vertex);
            int j = (int)(Math.random() * vertex);
            while (setContains(set, i, j) || i == j) {
                //System.out.println("contains");
                i = (int)(Math.random() * vertex);
                j = (int)(Math.random() * vertex);
            }
            //System.out.println(i + " " + j);
            setAdd(set, i, j);
            setAdd(set, j, i);
            List<Integer> listI = Bridge.adj.get(i);
            List<Integer> listJ = Bridge.adj.get(j);
            position[i].put(j, listI.size());
            listI.add(j);
            position[j].put(i, listJ.size());
            listJ.add(i);
        }
    }


    // 创建并查集
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

    // 建立生成树
    static void createTree() {
        for (int i = 0; i < adj.size(); i++) {
            if (visit[i])
                continue;
            treeDFS(i);
        }
    }

    private static void treeDFS(int i) {
        visit[i] = true;
        List<Integer> list = adj.get(i);
        for (int index = 0; index < list.size(); index++) {
            int j = list.get(index);
            if (!visit[j]) {
                tree.get(i).add(index);
                treeDFS(j);
            }
        }
    }

    static void treeBFS() {
        for (int i = 0; i < tree.size(); i++) {
            List<Integer> list = tree.get(i);
            List<Integer> listI= adj.get(i);
            for (int index : list) {
                // 通过使用生成树+并查集，减少判断边的次数
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

    private static int getParent(int i) {
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

    static void LCA() {
        for (int i = 0; i < adj.size(); i++) {
            List<Integer> listI = adj.get(i);
            for (int index = 0; index < listI.size(); index++) {
                int j = listI.get(index);
                if (j < i || treeSet[i].contains(j) || treeSet[j].contains(i))
                    continue;
                getCommonAncestor(i, j);
            }
        }
        for (int i = 0; i < adj.size(); i++) {
            List<Integer> listI = adj.get(i);
            for (int index = 0; index < listI.size(); index++) {
                int j = listI.get(index);
                if (setContains(del, i, j) || i > j)
                    continue;
                res.add(new int[] {i, j});
            }
        }
    }

    private static void getCommonAncestor(int i, int j) {
        if (deep[i] < deep[j]) {
            getCommonAncestor(j, i);
            return;
        }
        setAdd(del, i, j);
        setAdd(del, j, i);
        while (deep[i] != deep[j]) {
            setAdd(del, i, father[i]);
            setAdd(del, father[i], i);
            i = father[i];
        }
        while (deep[j] > 0 && i != j) {
            setAdd(del, i, father[i]);
            setAdd(del, father[i], i);
            i = father[i];
            setAdd(del, j, father[j]);
            setAdd(del, father[j], j);
            j = father[j];
        }
    }

    private static void setAdd(HashSet<List<Integer>> set, int i, int j) {
        List<Integer> list = new ArrayList<>();
        list.add(i);
        list.add(j);
        set.add(list);
    }

    private static boolean setContains(HashSet<List<Integer>> set, int i, int j) {
        List<Integer> list = new ArrayList<>();
        list.add(i);
        list.add(j);
        return set.contains(list);
    }

    static void createLCA() {
        for (int k = 0; k < vertex; k++) {
            father[k] = k;
        }
        for (int i = 0; i < adj.size(); i++) {
            if (visit[i])
                continue;
            //lcaTreeDFS(i);
            lcaTreeBFS(i);
        }
    }

    private static void lcaTreeDFS(int i) {
        visit[i] = true;
        List<Integer> list = adj.get(i);
        for (int index = 0; index < list.size(); index++) {
            int j = list.get(index);
            if (!visit[j]) {
                father[j] = i;
                deep[j] = deep[i] + 1;
                tree.get(i).add(index);
                treeSet[i].add(j);
                lcaTreeDFS(j);
            }
        }
    }

    private static void lcaTreeBFS(int k) {
        visit[k] = true;
        Queue<Integer> queue = new LinkedList<>();
        queue.add(k);
        while (!queue.isEmpty()) {
            int i = queue.remove();
            List<Integer> list = adj.get(i);
            for (int index = 0; index < list.size(); index++) {
                int j = list.get(index);
                if (!visit[j]) {
                    queue.add(j);
                    father[j] = i;
                    deep[j] = deep[i] + 1;
                    tree.get(i).add(index);
                    treeSet[i].add(j);
                    visit[j] = true;
                }
            }
        }

    }

    static void BFS() {
        for (int i = 0; i < adj.size(); i++) {
            List<Integer> listI = adj.get(i);
            for (int index = 0; index < listI.size(); index++) {
                if (listI.get(index) < i)
                    continue;
                // 通过比较连通分量判断是否为桥
                int before = calculateConnect();
                Arrays.fill(visit, false);
                int j = listI.remove(index);
                List<Integer> listJ = adj.get(j);
                listJ.remove((int) position[j].get(i));
                int after = calculateConnect();
                Arrays.fill(visit, false);
                listI.add(index, j);
                listJ.add(position[j].get(i), i);
                if (before != after)
                    res.add(new int[] {i, j});

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
                //int j = listI.remove(index);
                //List<Integer> listJ = adj.get(j);
                //listJ.remove((int) position[j].get(i));
                //createSet();
                //listI.add(index, j);
                //listJ.add(position[j].get(i), i);
                //if (getParent(i) != getParent(j))
                //    res.add(new int[] {i, j});
                //Arrays.fill(visit, false);
            }
        }
    }

    private static int calculateConnect() {
        int sum = 0;
        for (int i = 0; i < adj.size(); i++) {
            if (visit[i])
                continue;
            sum++;
            DFS(i);
        }
        return sum;
    }

    private static void DFS(int i) {

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
        for (int k = 0; k < t; k++) {

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
            //int vertex = 100000;
            //Bridge.create(vertex);
            //System.out.println("Bridge.create(vertex)");
            //Bridge.randomCreate(false);
            //System.out.println("Bridge.randomCreate(true)");

            // 生成生成树
            Bridge.createLCA();

            // 输出生成树
            //System.out.println("生成树如下：");
            //for (int i = 0; i < Bridge.tree.size(); i++) {
            //    List<Integer> temp = Bridge.tree.get(i);
            //    System.out.printf(i + ":");
            //    for (int temp2 : temp) {
            //        System.out.printf(temp2 + " ");
            //    }
            //    System.out.println();
            //}

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

            // 运行时间计算
            startTime = System.nanoTime();
            Bridge.LCA();
            endTime = System.nanoTime();
            time += endTime - startTime;
            System.out.println("运行时间： " + (endTime - startTime) + "ns");

            // 输出桥
            System.out.println("总共有" + Bridge.res.size() + "座桥，分别如下：");
            for (int[] temp : Bridge.res) {
                System.out.println(temp[0] + " " + temp[1]);
            }
        }

        System.out.println(t + "次运行平均时间： " + time / t + "ns");
    }
}
