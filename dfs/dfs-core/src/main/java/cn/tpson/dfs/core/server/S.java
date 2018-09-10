package cn.tpson.dfs.core.server;

import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Zhangka in 2018/08/29
 */
public class S {
    public static void main(String[] args) throws Exception {
        ServerSocket ss = new ServerSocket(8861);
        Socket s = ss.accept();
        InputStream in = s.getInputStream();
        byte[] dst = new byte[16];
        int totalRead = 0;
        while ((totalRead = in.read(dst)) > 0) {
            System.out.println(totalRead);
            System.out.println("===================");
        }
    }
}
