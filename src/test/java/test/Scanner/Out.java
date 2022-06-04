package test.Scanner;
import java.util.*;
class Bank implements Runnable
{
    int money;
    Bank()
    {
        money=100000;
    }
    void take(int num)
    {
        Thread thread=Thread.currentThread();
        money-=num;
        System.out.println(thread.getName()+" take "+num);
        System.out.println("Bank has "+money);
    }
    public void run() {
        for(int i=0;i<1000;i++)
            take(30);
    }
}

public class Out {
    public static void main(String[] args)
    {
        Bank bank=new Bank();
        Thread tom,jerry;
        tom=new Thread(bank);
        jerry=new Thread(bank);
        tom.setName("tom");
        jerry.setName("jerry");
        tom.start();
        jerry.start();

    }
}
