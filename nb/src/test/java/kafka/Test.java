package kafka;

import javax.xml.bind.DatatypeConverter;
import java.util.Base64;
import java.util.Date;

/**
 * Created by Zhangka in 2018/08/22
 */
public class Test {
    public static void main(String[] args) {
        /*System.out.println(Integer.MAX_VALUE);
        int sflag = 0xaa;
        System.out.println(sflag);
        byte b = (byte)170;
        byte[] bs = {72, b};
        System.out.println(new String(bs));*/
        String str = ">\u001c";
        System.out.println(str);
        byte[] bs = {0x5b, 0x7e, 0x1d, (byte)0x85};
        System.out.println(DatatypeConverter.printHexBinary(str.getBytes()));
        System.out.println(DatatypeConverter.printHexBinary(Base64.getDecoder().decode("QkYwMQAAAABWMS4wVjEuNDg2MzcwMzAzMzM3NjYzOAA0NjAxMQAAAAcA")));
        System.out.println(new String(bs));
        System.out.println(0x5b7e1d85);
        System.out.println(new Date(1534991749000L));
        System.out.println(new String(DatatypeConverter.parseHexBinary("38363337303330333333373636333800")));
        System.out.println(Integer.MAX_VALUE);

    }
}
