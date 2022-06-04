package test;

import java.util.*;

public class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode(int x) { val = x; }
}

class Solution {
    TreeNode root;
    void createTree(String str) {
        str = str.substring(1, str.length() - 1);
        String[] strTree = str.split(",");
        if (strTree[0].equals("null"))
            return;
        int i = 0;
        root = new TreeNode(Integer.parseInt(strTree[i++]));
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (i < strTree.length && !queue.isEmpty()) {
            TreeNode cur = queue.remove();
            if (!strTree[i].equals("null")) {
                cur.left = new TreeNode(Integer.parseInt(strTree[i]));
                queue.add(cur.left);
            }
            i++;
            if (!strTree[i].equals("null")) {
                cur.right = new TreeNode(Integer.parseInt(strTree[i]));
                queue.add(cur.right);
            }
            i++;
        }

    }

    public List<List<Integer>> levelOrder() {
        if (root == null) return null;
        Queue<TreeNode> queue = new LinkedList<TreeNode>(){{ add(root); }};
        List<List<Integer>> ans = new ArrayList<>();
        while (!queue.isEmpty()) {
            List<Integer> row = new ArrayList<>();
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.remove();
                row.add(node.val);
                if (node.left != null)
                    queue.add(node.left);
                if (node.right != null)
                    queue.add(node.right);
            }
            ans.add(row);

        }
        return ans;
    }

    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);
        String str = reader.nextLine();
        Solution solution = new Solution();
        solution.createTree(str);
        List<List<Integer>> num = solution.levelOrder();
        for (int i = 0; i < num.size(); i++) {
            for (int j = 0; j < num.get(i).size(); j++) {
                System.out.printf(num.get(i).get(j) + " ");
            }
            System.out.println();
        }
    }
}