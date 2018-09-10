package cn.tpson.dfs.core.server;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;

/**
 * Created by Zhangka in 2018/08/28
 */
public class Application {
    public static void main(String[] args) throws Exception {
        Properties properties = new Properties();
        Enumeration<URL> e = Application.class.getClassLoader().getResources("config.properties");
        while (e.hasMoreElements()) {
            URL url = e.nextElement();
            Properties pros = new Properties();
            pros.load(url.openStream());
            properties.putAll(pros);
            System.out.println(url.toExternalForm());
            System.out.println(url.toString());
            System.out.println(url.toURI());
            File file = new File(url.toURI());
            System.out.println(file.isFile());
        }
//        InputStream in = Application.class.getClassLoader().getResourceAsStream("config.properties");

        properties.putAll(System.getProperties());
        System.setProperties(properties);
        Enumeration<Object> keys = System.getProperties().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            System.out.println(key + "=" + System.getProperties().get(key));
        }

        /*System.out.println("=======================================");
        Map<String, String> env = System.getenv();
        for (String key : env.keySet()) {
            System.out.println(key + "=" + env.get(key));
        }*/
    }
}
