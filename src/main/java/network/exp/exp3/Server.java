package network.exp.exp3;

import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(1234);
            while(true) {
                Socket socket = serverSocket.accept();
                System.out.println("服务器的线程1启动,与客户端1连接成功");
                String fileName = "test.txt";
                try (
                        OutputStream os = socket.getOutputStream();
                        FileInputStream fins = new FileInputStream("C:\\Users\\hp\\Desktop\\课件\\大三下\\计网\\test.txt");
                ) {
                    System.out.println("要传输的文件为: " + fileName);
                    os.write(fileName.getBytes());
                    System.out.println("开始传输文件");
                    int data;
                    while((data = fins.read()) != -1) {
                        os.write(data);
                    }
                    System.out.println("文件传输结束");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
