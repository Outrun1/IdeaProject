package compilation.exp_3;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class TreeNode {
    char val;
    TreeNode[] children;
    TreeNode(char x) { val = x; }
}

class Solution {
    Map<Character, List<String>> map;
    TreeNode root;
    StringBuilder res;
    StringBuilder leaf;
    void createTree(Map<Character, List<String>> map, Character start) {
        this.map = map;
        res = new StringBuilder();
        leaf = new StringBuilder();
        root = new TreeNode(start);
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            TreeNode cur = queue.remove();
            List<String> list = map.getOrDefault(cur.val, new ArrayList<>());
            if (list.isEmpty())
                continue;
            String str = list.remove(0);
            cur.children = new TreeNode[str.length()];
            for (int i = 0; i < str.length(); i++) {
                cur.children[i] = new TreeNode(str.charAt(i));
                queue.add(cur.children[i]);
            }
        }
        preOrder(root);
    }

    public void preOrder(TreeNode node) {
        if (node == null) return;
        res.append(node.val);
        if (node.children == null) {
            leaf.append(node.val);
            return;
        }
        res.append("(");
        for (int i = 0; i < node.children.length; i++) {
            preOrder(node.children[i]);
        }
        res.append(")");
    }

    public static void main(String[] args) throws FileNotFoundException {
        Scanner reader = new Scanner(new FileReader("C:\\Users\\hp\\Desktop\\课件\\大三下\\编译原理\\exp3.txt"));
        Map<Character, List<String>> map = new HashMap<>();
        Character start = null;
        while (reader.hasNext()) {
            String str = reader.nextLine();
            System.out.println(str);
            String[] res = str.split("->");
            List<String> value = map.getOrDefault(res[0].charAt(0), new ArrayList<>());
            value.add(res[1]);
            map.put(res[0].charAt(0), value);
            if (start == null) {
                start = res[0].charAt(0);
            }
        }
        Solution solution = new Solution();
        solution.createTree(map, start);
        System.out.println(solution.res);
        System.out.println(solution.leaf);
        System.out.println("E(T(F(i)T')E'(+T(F(i)T'(*F(i)T'))E'))");
    }
}