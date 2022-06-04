package test;

import java.util.*;

class ListNode {
    int val;
    ListNode next;
    ListNode(int x) { val = x; }
    protected List<Integer> test() {
        return null;
    }
}

interface interfaceTest {
    int a = 1;
    int test1();
    default int test2() {
        return 1;
    }
}

class list extends ListNode implements interfaceTest {

    list(int x) {
        super(x);
    }

    @Override
    public ArrayList<Integer> test() {
        return null;
    }

    @Override
    public int test1() {
        return 0;
    }
}
public class ListL {
    ListNode root;
    void createList(int[] num) {
//        root = createList(num, 0);
        int i = 0;
        root = new ListNode(num[i++]);
        ListNode node = root;
        while (i < num.length) {
            ListNode temp = new ListNode(num[i++]);
            node.next = temp;
            node = temp;
        }
    }
    private ListNode createList(int[] num, int pos) {
        if (pos >= num.length)
            return null;
        ListNode node = new ListNode(num[pos++]);
        node.next = createList(num, pos);
        return node;
    }
    void printNum() {
        ListNode node = root;
        while (node.next != null) {
            System.out.printf(node.val + " ");
            node = node.next;
        }
        System.out.println(node.val);
    }

    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);
        int n = reader.nextInt();
        int[] num = new int[n];
        for (int i = 0; i < n; i++) {
            num[i] = reader.nextInt();
        }
        ListL l = new ListL();
        l.createList(num);
        l.printNum();
    }
}
