package cn.tpson.dfs.core.server;


import javax.xml.bind.DatatypeConverter;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Calendar;

/**
 * Created by Zhangka in 2018/08/29
 */
public class C {
    public static void main(String[] args) throws Exception {
        SocketChannel sc = SocketChannel.open(new InetSocketAddress("localhost", 7788));
        ByteBuffer buffer = ByteBuffer.allocate(256);
        buffer.put("IWAP00353456789012345#".getBytes());
        buffer.flip();
        sc.write(buffer);
        System.out.println("-------------------------");
        buffer.clear();
        buffer.put("IWAP10180918V0000.0000N00000.0000E000.1201000323.8710000009700000,460,0,22457,13889,01,zh-cn,00,a|d8-15-0d-09-51-58|85&a|70-65-82-5b-32-40|93&a|70-65-82-5b-32-41|93#".getBytes());
        buffer.flip();
        sc.write(buffer);
        System.out.println("-------------------------");
        /*buffer.clear();
        buffer.put("IWAP01ZHANGKA#".getBytes());
        buffer.flip();
        sc.write(buffer);*/
        sc.close();
        Thread.sleep(600000);
        /*byte a = '\r';
        byte b = '\n';
        System.out.println(a + " " + b);*/
/*
        byte[] array = "1".getBytes("unicode");
        System.out.println(array.length);

        System.out.println(DatatypeConverter.printHexBinary("1".getBytes("unicode")));
        System.out.println(new String(DatatypeConverter.parseBase64Binary("6df157335e0253575c71533a53576d7759279053003100300037003953f7"), "unicode"));
        System.out.println((char) Integer.parseInt("0031", 16));
        System.out.println("\u6df1\u5733\u5e02\u5357\u5c71\u533a\u5357\u6d77\u5927\u9053\u0031\u0030\u0037\u0039\u53f7");
        System.out.println("\u0031");

        String s = "abc哈";
        StringBuffer out = new StringBuffer();
        //直接获取字符串的unicode二进制
        byte[] bytes = s.getBytes("unicode");
        //然后将其byte转换成对应的16进制表示即可
        for (int i = 0; i < bytes.length - 1; i += 2) {
            out.append("\\u");
            String str = Integer.toHexString(bytes[i + 1] & 0xff);
            System.out.println(str.length());
            for (int j = str.length(); j <= 2; j++) {
                out.append("0");
            }
            String str1 = Integer.toHexString(bytes[i] & 0xff);
            out.append(str1);
            out.append(str);
        }

        System.out.println(out.toString());
        System.out.println("\ufeff\u0061\u0062\u0063\u54c8");
        */
    }
}
