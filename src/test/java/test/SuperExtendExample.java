package test;

import java.util.*;

public class SuperExtendExample extends SuperExample {
    static int[][] dp;
    static void test(List<List<Integer>> res){
        List<Integer> temo = new LinkedList<>();
        temo.add(1);
        temo.add(2);
        temo.add(3);
        res.add(temo);
    }
    private int z;
    public SuperExtendExample(int x, int y, int z) {
        super(x, y);
        this.z = z;
    }
    @Override
    public void func() {
        super.func();
        System.out.println("SuperExtendExample.func()");
    }
    public static void main(String[] args) {
//        SuperExample e = new SuperExtendExample(1, 2, 3);
//        e.func();
//        SuperExample.func();
//        SuperExtendExample.func();
//        Scanner reader = new Scanner(System.in);
//        reader.next();

//        HashMap<Integer, Boolean> map = new HashMap<>();
//		map.containsKey(1);
//		for(Integer i : map.keySet()){
//			map.get(i);
//            map.
//		}
//		for(Map.Entry<Integer, Boolean> entry : map.entrySet()){
//			if(entry.getValue()){
//				entry.getKey();
//			}
//		}
        List<String> temp = new ArrayList<>();
        temp.add("123456");
        temp.add("2");
        temp.add("3");
        temp.add("4");

        StringBuilder a = new StringBuilder(temp.remove(0));
        a.setCharAt(0,'a');
        System.out.println(a);
        a.append(123);
        System.out.println(a);
        System.out.println(temp);
        int []res;
        res = temp.stream().mapToInt(Integer::valueOf).toArray();
        char[] carry = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        String string = String.valueOf(carry).substring(1);
        System.out.println(string);
        res[0] = Integer.parseInt(string);
        res[0] = Integer.valueOf(string);
        System.out.println(Arrays.toString(res));
    }
}
