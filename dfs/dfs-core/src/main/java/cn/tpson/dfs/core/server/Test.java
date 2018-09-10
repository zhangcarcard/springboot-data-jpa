package cn.tpson.dfs.core.server;

import cn.tpson.dfs.common.util.Environment;

import java.io.*;
import java.util.logging.Logger;

/**
 * Created by Zhangka in 2018/08/29
 */
public class Test {
    public static void main(String[] args) throws IOException {
        /*Message message = new Message();
        message.setMessageId(456L);
        message.setFilename("abc.txt");
        message.setData(new byte[]{'1', '2', '3'});
        String s = JSON.toJSONString(message);
        System.out.println(s);
        Message m = JSON.parseObject(s, Message.class);
        System.out.println(new String(m.getData()));*/

        /*byte[] array = new byte[]{'1', '2', '3', '4', '5', '6', '1', '2', '3', '4', '5', '6', '1', '2', '3', '4', '5', '6', '1', '2', '3', '4', '5', '6', '1', '2', '3', '4', '5', '6', '1', '2', '3', '4', '5', '6'};
         */
        /*ByteArrayOutputStream out = new ByteArrayOutputStream(32);
        SnappyCompressorOutputStream snappy = new SnappyCompressorOutputStream(out, 12);
        snappy.write(array);
        snappy.finish();
        System.out.println(out.toByteArray().length);
        System.out.println(out.size());

        ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
        SnappyCompressorInputStream snappyIn = new SnappyCompressorInputStream(in);
        System.out.println(snappyIn.read());
        System.out.println(snappyIn.getSize());
        System.out.println(snappyIn.getUncompressedCount());*/

        /*System.out.println(Snappy.maxCompressedLength(36));
        System.out.println(Snappy.compress(array).length);
        byte[] com = Snappy.compress(array);
        System.out.println(new String(com));
        System.out.println(new String(Snappy.uncompress(com)));*/

//        byte[] array = new byte[2];
//        Output output = new Output(4096, Integer.MAX_VALUE);
//        output.setBuffer(array);
        /*ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
        Kryo kryo = new Kryo();
        kryo.setReferences(false);
        kryo.setRegistrationRequired(false);
        kryo.setInstantiatorStrategy(new StdInstantiatorStrategy());
        output.setOutputStream(byteArray);
        kryo.writeObjectOrNull(output, message, Message.class);
        output.flush();
        output.close();
        for (byte b : byteArray.toByteArray()) {
            System.out.print(b + " ");
        }

        System.out.println();
        Input input = new Input(byteArray.toByteArray());
        Message m1 = kryo.readObjectOrNull(input, Message.class);
        input.close();
        System.out.println(JSON.toJSONString(m1));*/

        /*File f = new cn.tpson.dfs.core.server.File();
        f.setFilename("123.txt");
        f.setData("杭州".getBytes("utf-8"));
        FileStore.put(f);*/
        /*File f = FileStore.get("aaa");
        System.out.println(new String(f.getData(), "utf-8"));*/
/*        long b = System.currentTimeMillis();
        try (FileInputStream fin = new FileInputStream("f:/soft/apache-activemq-5.15.3-bin.zip")) {
            FileStore.put("apache-activemq-5.15.3-bin.zip", fin);
        }
        System.out.println(System.currentTimeMillis() - b);
*/
/*
        long b2 = System.currentTimeMillis();
        try (FileOutputStream fout = new FileOutputStream(new java.io.File("f:/apache-activemq-5.15.3-bin.zip"))) {
            FileStore.get("apache-activemq-5.15.3-bin.zip", fout);
        }
        System.out.println(System.currentTimeMillis() - b2);
*/
/*
        for (int i = 0; i < 100; ++i) {
            FileStore.put("/2018-09-06".getBytes(FileStore.DEFAULT_CHARSET), "杭州市滨江区怀德街1号王道公园5号楼3层\n".getBytes(FileStore.DEFAULT_CHARSET));
        }

        long b = System.currentTimeMillis();
        try (FileOutputStream fout = new FileOutputStream(new File("f:/test/2018-09-06"))) {
            FileStore.get("/2018-09-06", fout);
        }
        System.out.println(System.currentTimeMillis() - b);*/
        /*FileOutputStream fout = new FileOutputStream("f:/test.txt", true);
        fout.write(0x31);
        fout.flush();
        fout.close();

        FramedSnappyCompressorInputStream*/
//        System.out.println(InetAddress.getLocalHost().getHostAddress());

        /*byte[] out = new byte[] {0x36, 0x37, 0x38};
        FileOutputStream fout = new FileOutputStream("f:/test.snappy", true);
        FramedSnappyCompressorOutputStream snappy = new FramedSnappyCompressorOutputStream(fout);
        snappy.write(out);
        snappy.finish();
        snappy.flush();
        snappy.close();
*/
        /*FileInputStream fin = new FileInputStream("f:/test.snappy");
        FramedSnappyCompressorInputStream snappyIn = new FramedSnappyCompressorInputStream(fin);
        System.out.println(snappyIn.getCompressedCount());
        System.out.println(snappyIn.getUncompressedCount());
        System.out.println(snappyIn.available());
        byte[] in = new byte[1024];
        while (snappyIn.read(in, 0, in.length) != -1) {
            System.out.println(new String(in));
        }*/
        /*int a = 25;
        FileOutputStream fout = new FileOutputStream("f:/test.txt");
        byte[] array = new byte[4];
        array[3] = (byte)(a & 0xFF);
        array[2] = (byte)(a >> 8 & 0xFF);
        array[1] = (byte)(a >> 16 & 0xFF);
        array[0] = (byte)(a >> 24 & 0xFF);

        int i;
        i = ((array[0] & 0xFF) << 24) | ((array[1] & 0xFF) << 16) | ((array[2] & 0XFF) << 8) | (array[3] & 0XFF);
        System.out.println(i);*/

        /*FileOutputStream fout = new FileOutputStream("f:/test.snappy", true);
        SnappyCompressorOutputStream snappy = new SnappyCompressorOutputStream(fout, 2);
        byte[] array = new byte[] {'4', '5', '6', '7', '8', '9'};
        snappy.write(array, 0, array.length);
        snappy.finish();
        snappy.flush();
        snappy.close();*/
    }
}
