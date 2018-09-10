package cn.tpson.dfs.common.util;

import cn.tpson.dfs.common.logger.Logger;
import cn.tpson.dfs.common.logger.LoggerFactory;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Properties;

/**
 * Created by Zhangka in 2018/08/31
 */
public class Environment {
    private static final Logger log = LoggerFactory.getLogger(Environment.class);

    private static Properties pros;
    private Environment() {
        throw new AssertionError("no Environment instance for you!");
    }

    static {
        pros = PropertiesLoaderUtils.load(null, "config.properties");
        loadConfig();
        Properties sys = System.getProperties();
        pros.putAll(sys);
    }

    public static void loadConfig() {
        String dataDir = pros.getProperty("xdfs.data.dir");
        if (StringUtils.isNotBlank(dataDir)) {
            File file = new File(dataDir + File.separator + "fid");
            if (file.exists()) {
                try (FileInputStream fin = new FileInputStream(file)) {
                    byte[] array = new byte[4];
                    int count = fin.read(array);
                    if (count == 4) {
                        int fid = Bits.byte4ToInt(array, 0);
                        pros.put("fid", fid);
                    }
                } catch (Exception e) {
                    log.error("", e);
                }
            } else {
                try (FileOutputStream fout = new FileOutputStream(file)) {
                    byte[] array = new byte[] {0x00, 0x00, 0x00, 0x00};
                    fout.write(array);
                    pros.put("fid", 0);
                } catch (Exception e) {
                    log.error("", e);
                }
            }
        }
    }

    public static Object get(Object key) {
        return pros.get(key);
    }

    public static void put(Object key, Object value) {
        pros.put(key, value);
    }

    public static String getProperty(String key) {
        return pros.getProperty(key);
    }

    public static void put(String key, String value) {
        pros.put(key, value);
    }

    public static String getProperty(String key, String defaultValue) {
        return pros.getProperty(key, defaultValue);
    }

    public static Object getOrDefault(Object key, Object defaultValue) {
        return pros.getOrDefault(key, defaultValue);
    }
}
