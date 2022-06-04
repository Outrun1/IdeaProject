package test.Scanner;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
class Print implements Runnable
{
    Thread num,charn;
    public Print()
    {
        num=new Thread(this);
        charn=new Thread(this);
    }
    public void run()
    {
        Thread thread=Thread.currentThread();
        for(int i=1;i<=12;i++)
        {
            if(thread==charn)
            {
                try
                {
                    Thread.sleep(1);
                }
                catch(InterruptedException e) {}
                switch(i)
                {
                    case 1:System.out.print("OneJanuary");break;
                    case 2:System.out.print("TwoFebruary");break;
                    case 3:System.out.print("ThreeMarch");break;
                    case 4:System.out.print("FourApril");break;
                    case 5:System.out.print("FiveMay");break;
                    case 6:System.out.print("SixJune");break;
                    case 7:System.out.print("SevenJuly");break;
                    case 8:System.out.print("EightAugust");break;
                    case 9:System.out.print("NightSeptember");break;
                    case 10:System.out.print("TenOctober");break;
                    case 11:System.out.print("ElevenNovember");break;
                    case 12:System.out.print("TwelveDecember");break;
                }
                num.interrupt();
                try
                {
                    Thread.sleep(10000);
                }
                catch(InterruptedException e) {}
            }
            else
            {
                System.out.print(i);
                charn.interrupt();
                try
                {
                    Thread.sleep(10000);
                }
                catch(InterruptedException e) {}
            }
        }
    }
}

public class scanner  {
    public static void main(String[] args) {
        Print print=new Print();
        print.num.start();
        print.charn.start();
    }
}
