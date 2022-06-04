package test;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class SuperExample {
    protected int x;
    protected int y;
    public SuperExample(int x, int y) {
        this.x = x;
        this.y = y;
        System.out.println("带参构造方法");
    }
    public SuperExample() {
        System.out.println("默认构造方法");
    }
    public void func() {
        System.out.println("SuperExample.func()");
    }
}
