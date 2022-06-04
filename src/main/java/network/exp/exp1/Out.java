package network.exp.exp1;

import java.net.*;
import java.io.*;

public class Out {
    public static void main(String[] args) throws IOException {
        URL url = new URL("https://www.szu.edu.cn");
        URLConnection urlConnection = url.openConnection();
        InputStream inputStream = urlConnection.getInputStream();
        FileOutputStream outputStream = new FileOutputStream("C:\\Users\\hp\\Desktop\\课件\\大三下\\计网\\szu.html");
        int a = 0;
        while(a > -1)
        {
            a = inputStream.read();
            outputStream.write(a);
        }
        outputStream.close();
        File file = new File("C:\\Users\\hp\\Desktop\\课件\\大三下\\计网\\szu.html");
        System.out.println("文件大小：" + file.length() / 1024.0 + "KB");
    }
}
