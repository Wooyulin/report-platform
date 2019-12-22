package cn.wooyulin.reportplatform.proxy;

import java.net.ServerSocket;
import java.net.Socket;

public class SocketProxy {

    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(1080);
        while (true) {
            Socket socket = null;
            try {
                System.out.println("start");
                socket = serverSocket.accept();
                new SocketThread(socket).start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
