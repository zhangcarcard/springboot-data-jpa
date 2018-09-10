package cn.tpson.dfs.common.logger;

/**
 * @author zk
 */
public class LoggerFactory {
    private LoggerFactory() {
        throw new AssertionError("No LoggerFactory instances for you!");
    }

    public static Logger getLogger(Class<?> clazz) {
        org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(clazz);
        return new Logger(logger);
    }

    public static Logger getLogger(String name) {
        org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(name);
        return new Logger(logger);
    }
}
