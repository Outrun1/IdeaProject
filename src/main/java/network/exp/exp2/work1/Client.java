package network.exp.exp2.work1;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1", 1234);
        System.out.println("创建客户端连接");

        Scanner reader = new Scanner(System.in);
        DataInputStream inputStream = new DataInputStream(socket.getInputStream());
        DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());

        while (true) {
            String str = reader.nextLine();
            outputStream.writeUTF(str);

            String message = inputStream.readUTF();
            System.out.println(message);
            if (message.equals("Bye"))
                break;
        }
        reader.close();
        inputStream.close();
        outputStream.close();
        System.out.println("连接中断");

    }
}
