package cn.wooyulin.reportplatform.proxy;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * sockcs5代理主类
 * @author 5yl
 */
public class SocketThread extends Thread {
    //输入socket
    private Socket socketIn;
    private InputStream isIn;
    private OutputStream osIn;
    //
    private Socket socketOut;
    private InputStream isOut;
    private OutputStream osOut;

    public SocketThread(Socket socket) {
        this.socketIn = socket;
    }

    private byte[] buffer = new byte[4096];
    //接受代理请求应答标志
    private static final byte[] VER = { 0x5, 0x0 };
    //用于连接成功，修改后6个元素向客户端发送成功消息
    private static final byte[] CONNECT_OK = { 0x5, 0x0, 0x0, 0x1, 0, 0, 0, 0, 0, 0 };

    /**
     * 如何用代理TCP协议
     *  1.向代理服务器的端口建立tcp连接。
     *  2.向代理服务器发送 05 01 00 （此为16进制码，以下同）
     *  3.如果接到 05 00 则是可以代理
     *  4.发送 05 01 00 01 + 目的地址(4字节） + 目的端口（2字节），目的地址和端口都是16进制码（不是字符串！！）。
     *  例      202.103.190.27 -7201 则发送的信息为：05 01 00 01 CA 67 BE 1B 1C 21 (CA=202 67=103 BE=190 1B=27 1C21=7201)
     *  5.客户端接受代理服务器返回的自身地址和端口，连接完成（在代理服务器正确连接上了目标服务器后，向客户端反馈连接成功）
     *  6.以后操作和直接与目的方进行TCP连接相同。
     */
    public void run() {
        try {
            System.out.println("\n\na client connect " + socketIn.getInetAddress() + ":" + socketIn.getPort());
            isIn = socketIn.getInputStream();
            osIn = socketIn.getOutputStream();
            //2 客户端向代理方发送出请求代理
            int len = isIn.read(buffer);

            System.out.println("< " + bytesToHexString(buffer, 0, len));
            //3、代理服务器向客户端发送可以进行代理的报文
            osIn.write(VER);
            osIn.flush();

            System.out.println("> " + bytesToHexString(VER, 0, VER.length));
            //客户端接收到可以进行代理的响应，正常情况下会再向代理方发送 05 01 00 01 + 目的地址(4字节） + 目的端口（2字节）
            len = isIn.read(buffer);
            System.out.println("< " + bytesToHexString(buffer, 0, len));
            // 解析主机和端口
            String host = findHost(buffer, 4, 7);
            int port = findPort(buffer, 8, 9);
            System.out.println("destination host=" + host + ",port=" + port);

            //向目标服务器发起连接
            socketOut = new Socket(host, port);
            isOut = socketOut.getInputStream();
            osOut = socketOut.getOutputStream();

            for (int i = 4; i <= 9; i++) {
                CONNECT_OK[i] = buffer[i];
            }
            //5.客户端接受代理服务器返回的自身地址和端口，连接完成（在代理服务器正确连接上了目标服务器后，向客户端反馈连接成功）
            osIn.write(CONNECT_OK);
            osIn.flush();
            System.out.println("> " + bytesToHexString(CONNECT_OK, 0, CONNECT_OK.length));

            //6、以后操作和直接与目的方进行TCP连接相同。
            //这里直接使用两个线程进行两端的沟通
            SocketThreadOutput out = new SocketThreadOutput(isIn, osOut);
            out.start();
            SocketThreadInput in = new SocketThreadInput(isOut, osIn);
            in.start();
            out.join();
            in.join();
        } catch (Exception e) {
            System.out.println("a client leave");
        } finally {
            try {
                if (socketIn != null) {
                    socketIn.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("socket close");
    }

    public static String findHost(byte[] bArray, int begin, int end) {
        StringBuffer sb = new StringBuffer();
        for (int i = begin; i <= end; i++) {
            sb.append(Integer.toString(0xFF & bArray[i]));
            sb.append(".");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    public static int findPort(byte[] bArray, int begin, int end) {
        int port = 0;
        for (int i = begin; i <= end; i++) {
            port <<= 16;
            port += bArray[i];
        }
        return port;
    }

    // 4A 7D EB 69
    // 74 125 235 105

    /**
     * 将byte数组转换成16进制数字，并组装成字符串形式返回
     * eg 202.103.190.27 -7201 则发送的信息为：05 01 00 01 CA 67 BE 1B 1C 21 (CA=202 67=103 BE=190 1B=27 1C21=7201)
     * @param bArray
     * @param begin
     * @param end
     * @return
     */
    public static final String bytesToHexString(byte[] bArray, int begin, int end) {
        StringBuffer sb = new StringBuffer(bArray.length);
        String sTemp;
        for (int i = begin; i < end; i++) {
            sTemp = Integer.toHexString(0xFF & bArray[i]);
            if (sTemp.length() < 2)
                sb.append(0);
            sb.append(sTemp.toUpperCase());
            sb.append(" ");
        }
        return sb.toString();
    }
}
