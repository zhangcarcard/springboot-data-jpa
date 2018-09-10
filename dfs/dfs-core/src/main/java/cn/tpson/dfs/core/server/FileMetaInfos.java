package cn.tpson.dfs.core.server;

import cn.tpson.dfs.common.util.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Zhangka in 2018/08/29
 */
public class FileMetaInfos {
    private static Map<String, List<FileMetaInfo>> infoMap = new HashMap<>();

    static {
        String file = Environment.getProperty("xdfs.meta.dir") + File.separator + "xdfs.meta";
        try (FileInputStream fin = new FileInputStream(file)) {
            FileChannel fc = fin.getChannel();
            ByteBuffer buffer = ByteBuffer.allocate((int) fc.size());
            int offset = 0;
            int lastFid = 0;
            if (fc.read(buffer) > 0) {
                buffer.flip();
                int frameLen, count, fid, len;
                while (buffer.hasRemaining()) {
                    frameLen = buffer.get();
                    count = buffer.getInt();
                    fid = buffer.getInt();
                    len = frameLen - 8;
                    byte[] array = new byte[len];
                    buffer.get(array, 0, len);
                    if (fid > lastFid) {
                        offset = 0;
                        lastFid = fid;
                    }
                    String filename = new String(array, FileStore.DEFAULT_CHARSET);
                    FileMetaInfo f = new FileMetaInfo(offset, fid, count, filename);

                    List<FileMetaInfo> list = infoMap.get(filename);
                    if (list == null) {
                        list = new ArrayList<>();
                        infoMap.put(filename, list);
                    }
                    list.add(f);
                    offset += count;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<FileMetaInfo> get(String filename) {
        return infoMap.get(filename);
    }
}
