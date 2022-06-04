package test;

import java.util.*;

public class Docker {
    public static void main(String[] args){

        System.out.println("queue");
        Queue<Character> q = new LinkedList<>();
        q.add('a');
        q.add('b');
        q.add('c');
        System.out.println(q.peek());
        System.out.println(q.poll());
        System.out.println(q.remove());

        System.out.println("stack");
        Deque<Character> s = new LinkedList<>();
        s.addFirst('a');
        s.push('b');
        s.push('c');
        System.out.println(s.peek());
        System.out.println(s.removeFirst());
        System.out.println(s.remove());
        System.out.println(s.pop());

        System.out.println("优先队列-小顶堆");
        Queue<Integer> minHead = new PriorityQueue<>();
        minHead.add(1);
        minHead.add(3);
        minHead.add(2);
        minHead.add(0);
        System.out.println(minHead.remove());
        System.out.println(minHead.peek());
        System.out.println(minHead.poll());
        System.out.println(minHead.poll());

        System.out.println("优先队列-大顶堆");
        Queue<Integer> maxHead = new PriorityQueue<>((x, y) -> (y - x));
//        Queue<Integer> maxHead = new PriorityQueue<>(new Comparator<>() {
//            @Override
//            public int compare(Integer o1, Integer o2) {
//                return o2 - o1;
//            }
//        });
        maxHead.add(1);
        maxHead.add(3);
        maxHead.add(2);
        maxHead.add(0);
        System.out.println(maxHead.remove());
        System.out.println(maxHead.peek());
        System.out.println(maxHead.poll());
        System.out.println(maxHead.poll());

        System.out.println("双端队列");
        Deque<Integer> deque = new LinkedList<>();
        deque.addFirst(1);
        deque.addLast(2);
        deque.addLast(3);
        deque.addFirst(0);
        deque.peekFirst();
        deque.isEmpty();
        deque.removeFirst();

        Deque<Integer> mono_stack = new ArrayDeque<>();
        mono_stack.addFirst(1);
        mono_stack.addLast(2);
        mono_stack.addLast(3);
        mono_stack.addFirst(0);
        mono_stack.peekFirst();
        mono_stack.isEmpty();
        mono_stack.removeFirst();
//        HashSet<Integer> hashSet = new HashSet<>();
//        HashMap<Integer, Integer> hashMap = new HashMap<>();
//        TreeSet<Integer> treeSet = new TreeSet<>();
//        TreeMap<Integer, Integer> treeMap = new TreeMap<>();
//        hashSet.add(1);
//        System.out.println(hashSet.contains(1));
//        hashMap.put(1, 0);
//        System.out.println(hashMap.get(1));
//        treeSet.add(2);
//        treeSet.add(1);
//        treeSet.add(3);
//        System.out.println(treeSet.pollLast());
//        System.out.println(treeSet.pollFirst());
//        System.out.println(treeSet.pollFirst());
//        treeMap.put(2, 4);
//        treeMap.put(1, 3);
//        treeMap.put(3, 5);
//        System.out.println(treeMap.pollFirstEntry().getKey());
//        System.out.println(treeMap.pollFirstEntry().getKey());
//        System.out.println(treeMap.pollFirstEntry().getKey());

    }
}
