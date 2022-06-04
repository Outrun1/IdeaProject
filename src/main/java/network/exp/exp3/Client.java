package network.exp.exp3;

import java.io.*;
import java.net.*;

public class Client {

    public static void main(String[] args) {

        byte[] buf = new byte[1024];

        Socket s = new Socket();
        try {
            s.connect(new InetSocketAddress("127.0.0.1", 1234), 1000);
            System.out.println("与服务器连接成功");
            InputStream inputStream = s.getInputStream();
            int len = inputStream.read(buf);
            String fileName = new String(buf , 0, len);
            System.out.println("接收到的文件为：" + fileName);
            System.out.println("保存为：" + "1" + fileName);
            FileOutputStream outputStream = new FileOutputStream("C:\\Users\\hp\\Desktop\\课件\\大三下\\计网\\1" + fileName);
            int data;
            while((data = inputStream.read()) != -1) {
                outputStream.write(data);
            }
            inputStream.close();
            s.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}