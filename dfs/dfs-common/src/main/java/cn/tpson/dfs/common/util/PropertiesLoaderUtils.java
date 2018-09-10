package cn.tpson.dfs.common.util;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Properties;

/**
 * Created by Zhangka in 2018/08/31
 */
public class PropertiesLoaderUtils {
    private PropertiesLoaderUtils() {
        throw new AssertionError("no PropertiesLoader instance for you!");
    }

    public static Properties load(ClassLoader classLoader, String filename) {
        Properties properties = new Properties();
        try {
            Enumeration<URL> urls = classLoader != null ? classLoader.getResources(filename) : ClassLoader.getSystemResources(filename);
            while (urls.hasMoreElements()) {
                URL url = urls.nextElement();
                Properties pros = new Properties();
                pros.load(url.openStream());
                properties.putAll(pros);
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("Unable to load factories from location [" + filename + "]", e);
        }

        return properties;
    }
}
