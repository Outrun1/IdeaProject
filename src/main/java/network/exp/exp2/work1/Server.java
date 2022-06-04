package network.exp.exp2.work1;

import java.net.*;
import java.io.*;
import java.util.Date;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(1234);
        Socket socket = serverSocket.accept();
        System.out.println("创建服务端连接");

        DataInputStream inputStream = new DataInputStream(socket.getInputStream());
        DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());

        while (true) {
            String str = inputStream.readUTF();
            System.out.println(str);
            if (str.equals("Time")) {
                Date date = new Date();
                outputStream.writeUTF( "服务器当前时间为："+ date);
                System.out.println("服务器当前时间为："+ date);
            } else if (str.equals("Exit")) {
                outputStream.writeUTF("Bye");
                break;
            }
        }
        inputStream.close();
        outputStream.close();
        System.out.println("连接中断");
    }
}
