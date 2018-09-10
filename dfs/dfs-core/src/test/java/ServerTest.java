import cn.tpson.dfs.common.logger.Logger;
import cn.tpson.dfs.common.logger.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.Enumeration;

/**
 * Created by Zhangka in 2018/08/27
 */
public class ServerTest {
    private static final Logger log = LoggerFactory.getLogger(ServerTest.class);

    public static void main(String[] args) throws Exception {
        /*Enumeration<URL> enumeration = ServerTest.class.getClassLoader().getResources("resources/config.properties");
        while (enumeration.hasMoreElements()) {
            URL url = enumeration.nextElement();
            System.out.println(url);
        }*/
        /*for (int i = 0; i < 500000; ++i) {
            File f = new File("f:/test/" + i + ".txt");
            try (FileOutputStream fout = new FileOutputStream(f)) {
                fout.write(i);
            }
        }*/
        try {
            new Server(8861).start();
            log.info("服务器启动在8861端口.");
        } catch (InterruptedException e) {
            log.error("服务器启动出错.");
        }
    }
}
