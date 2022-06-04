package network.exp.exp2.work2;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.*;
import javax.swing.*;

public class WeChat extends JFrame implements ActionListener {

    private final JTextArea text;
    private final JTextField ip_text;
    private final JTextField send_text;

    private final JButton button_exit;
    private final JButton button_clear;
    private final JButton button_send;

    private DatagramSocket socket;

    private final int port_send;
    private final int port_receive;
    private final String sender;
    private final String receiver;

    public WeChat(String sender, String receiver, int port_sender, int port_receiver)
            throws UnknownHostException {
        port_send = port_sender;
        port_receive = port_receiver;
        this.sender = sender;
        this.receiver = receiver;

        setTitle("UDP聊天程序");
        setBounds(500, 400, 600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        InetAddress inetaddress = InetAddress.getLocalHost();
        String local_address = inetaddress.getHostAddress();
        ip_text = new JTextField(local_address);
        JTextField host_text = new JTextField(receiver);
        host_text.setEditable(false);
        host_text.setHorizontalAlignment(JTextField.CENTER);
        add(host_text, BorderLayout.NORTH);

        text = new JTextArea();
        text.setEditable(false);
        JScrollPane textPanel = new JScrollPane(text);
        add(textPanel, BorderLayout.CENTER);

        Box box = Box.createVerticalBox();
        send_text = new JTextField();
        button_send = new JButton("确定");
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(send_text, BorderLayout.CENTER);
        panel.add(button_send, BorderLayout.EAST);
        box.add(panel);

        JPanel select_bar = new JPanel(new FlowLayout());
        button_clear = new JButton("清空");
        button_exit = new JButton("退出");
        select_bar.add(button_clear);
        select_bar.add(button_exit);
        box.add(select_bar);

        add(box, BorderLayout.SOUTH);

        setVisible(true);
        Get_Message();
        button_send.addActionListener(this);
        button_clear.addActionListener(this);
        button_exit.addActionListener(this);
    }

    private void Get_Message() {
        try {
            socket = new DatagramSocket(port_receive);
        } catch (SocketException e) {
            e.printStackTrace();
        }
        // 用buffer存储数据
        byte[] buffer = new byte[1024];
        final DatagramPacket data = new DatagramPacket(buffer, buffer.length);
        Runnable run = new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                    try {
                        socket.receive(data);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //输出接收到的消息
                    String message = new String(data.getData(), 0, data.getLength());
                    text.append(receiver + ":\n  " + message + "\n");
                }
            }
        };
        new Thread(run).start();
    }

    public void Send_Message() throws UnknownHostException {
        String ip = ip_text.getText();
        InetAddress inetaddress;
        inetaddress = InetAddress.getByName(ip);

        byte[] data = send_text.getText().getBytes();
        DatagramPacket data_package = new DatagramPacket(data, data.length, inetaddress, port_send);
        text.append(sender + ":\n  " + send_text.getText() + "\n");
        try {
            socket.send(data_package);
        } catch (IOException e) {
            e.printStackTrace();
        }
        send_text.setText(null);
    }

    public void actionPerformed(ActionEvent ev) {
        if (ev.getSource() == button_send) {
            try {
                Send_Message();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }
        if (ev.getSource() == button_clear) {
            text.setText("");
        }
        if (ev.getSource() == button_exit) {
            System.exit(0);
        }
    }

    public static void main(String[] args) throws UnknownHostException {
        new WeChat("person1", "person2", 1234, 4321).setVisible(true);
        new WeChat("person2", "person1", 4321, 1234).setVisible(true);
    }

}
