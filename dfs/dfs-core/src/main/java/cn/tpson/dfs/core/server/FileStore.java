package cn.tpson.dfs.core.server;

import cn.tpson.dfs.common.logger.Logger;
import cn.tpson.dfs.common.logger.LoggerFactory;
import cn.tpson.dfs.common.util.Environment;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.util.List;

/**
 * Created by Zhangka in 2018/08/29
 */
public class FileStore {
    private static final Logger log = LoggerFactory.getLogger(FileStore.class);
    public static final int DEFAULT_BUFFER_SIZE = 8192;
    public static final int MAX_FILE_SIZE = 1024 * 1024 * 64;
    public static final int MAX_LENGTH_FILENAME = 118;
    public static final String DEFAULT_CHARSET = "utf-8";

    public static void get(String filename, OutputStream out) {
        List<FileMetaInfo> files = FileMetaInfos.get(filename);
        checkNotNull(files, "files");

        if (files.isEmpty()) {
            return;
        }

        WritableByteChannel outChannel = Channels.newChannel(out);
        for (FileMetaInfo file : files) {
            try (FileInputStream fin = getFileInputStream(file.getFid())) {
                FileChannel inChannel = fin.getChannel();
                inChannel.transferTo(file.getOffset(), file.getLength(), outChannel);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static boolean put(byte[] filename, byte[] data) {
        checkNotNull(data, "data");
        if (data.length == 0) {
            return false;
        }

        ByteArrayInputStream in = new ByteArrayInputStream(data);
        return put(filename, in);
    }

    public static boolean put(byte[] filename, ByteBuffer data) {
        checkNotNull(data, "data");
        byte[] dst = new byte[data.remaining()];
        data.get(dst);
        return put(filename, dst);
    }

    public static boolean put(byte[] filename, InputStream in) {
        checkNotNull(in, "in");
        return put(filename, Channels.newChannel(in));
    }

    public static boolean put(byte[] filename, ReadableByteChannel inChannel) {
        checkNotNull(inChannel, "inChannel");

        ByteBuffer buffer = ByteBuffer.allocate(DEFAULT_BUFFER_SIZE);
        int read = 0;
        do {
            try (FileOutputStream fout = getFileOutputStream()) {
                FileChannel fc = fout.getChannel();
                int fileSize = (int)fc.size();
                int writeSize = MAX_FILE_SIZE - fileSize;
                int count = 0;
                if (buffer.position() > 0) {
                    count += buffer.remaining();
                    write(fc, buffer, buffer.position(), buffer.limit());
                }

                buffer.clear();
                while (writeSize > 0 && (read = inChannel.read(buffer)) > 0) {
                    buffer.flip();
                    if (read > writeSize) {
                        buffer.limit(writeSize);
                        write(fc, buffer, buffer.position(), buffer.limit());
                        count += writeSize;
                        buffer.limit(read);
                    } else {
                        write(fc, buffer, buffer.position(), buffer.limit());
                        count += read;
                        buffer.clear();
                    }
                    writeSize -= read;
                    if (writeSize <= 0) {
                        break;
                    }
                }

                if (count > 0) {
                    writeMeta(count, filename);
                }
                if (writeSize <= 0) {
                    increaseFid();
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } while (read > 0);
        return true;
    }

    protected static int write(FileChannel fc, ByteBuffer buffer, int position, int limit) throws IOException {
        buffer.position(position);
        buffer.limit(limit);
        return fc.write(buffer);
    }

    protected static FileOutputStream getFileOutputStream() throws FileNotFoundException {
        String dataDir = Environment.getProperty("xdfs.data.dir") + File.separator;
        int fid = (int)Environment.get("fid");
        String f = dataDir + String.format("%06d", fid) + ".bin";
        return new FileOutputStream(f, true);
    }

    protected static FileInputStream getFileInputStream(int fid) throws FileNotFoundException {
        String dataDir = Environment.getProperty("xdfs.data.dir") + File.separator;
        String f = dataDir + String.format("%06d", fid) + ".bin";
        return new FileInputStream(f);
    }

    protected static int fileSize(int fid) {
        String dataDir = Environment.getProperty("xdfs.data.dir") + File.separator;
        String f = dataDir + String.format("%06d", fid) + ".bin";
        try (FileInputStream fin = new FileInputStream(f)) {
            return (int)fin.getChannel().size();
        } catch (Exception e) {
            log.error("", e);
        }
        return -1;
    }

    public synchronized static void increaseFid() {
        String f = Environment.getProperty("xdfs.data.dir") + File.separator;
        int fid = (int)Environment.get("fid");
        if (fileSize(fid) < MAX_FILE_SIZE) {
            return;
        }

        try (FileChannel fout = new FileOutputStream(f + "fid").getChannel()) {
            while (true) {
                try {
                    fout.lock();
                    break;
                } catch (Exception e) {
                    log.error("正在获取fid文件锁.", e);
                    Thread.sleep(500);
                }
            }

            ByteBuffer buffer = ByteBuffer.allocate(4);
            ++fid;
            buffer.putInt(fid);
            buffer.flip();
            fout.write(buffer);
            Environment.put("fid", fid);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        increaseFid();
    }

    protected static void writeMeta(int count, byte[] filename) {
        int length = filename.length;
        if (length > MAX_LENGTH_FILENAME) {
            throw new RuntimeException("文件名长度不能大于" + MAX_LENGTH_FILENAME + "字节");
        }

        String file = Environment.getProperty("xdfs.meta.dir") + File.separator + "xdfs.meta";
        int fid = (int)Environment.get("fid");
        try (FileOutputStream fout = new FileOutputStream(file, true)) {
            FileChannel fc = fout.getChannel();
            length = length + 9;
            ByteBuffer buffer = ByteBuffer.allocate(length);
            buffer.put((byte)(length - 1));
            buffer.putInt(count);
            buffer.putInt(fid);
            buffer.put(filename);
            buffer.flip();
            fc.write(buffer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void checkNotNull(Object o, String name) {
        if (o == null)
            throw new NullPointerException("\"" + name + "\" is null!");
    }
}
