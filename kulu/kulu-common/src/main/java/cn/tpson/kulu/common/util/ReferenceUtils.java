package cn.tpson.kulu.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 
 * @author zk
 *
 */
public class ReferenceUtils {
	private static final Logger log = LoggerFactory.getLogger(ReferenceUtils.class);

	private ReferenceUtils() {
		throw new AssertionError("No ReferenceUtils instances for you!");
	}
	
	public static Object get(Object ins, String field) {
	    return get(ins.getClass(), ins, field);
	}

    public static Object get(Class<?> clazz, Object ins, String field) {
        try {
            Field f = getDeclaredField(clazz, field);
            f.setAccessible(true);
            return f.get(ins);
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    public static void set(Object ins, String field, Object value) {
        set(ins.getClass(), ins, field, value);
    }

	public static void set(Class<?> clazz, Object ins, String field, Object value) {
        try {
            Field f = getDeclaredField(clazz, field);
            f.setAccessible(true);
            f.set(ins, value);
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("", e);
            }
        }
    }

    public static <T> T newAndSet(Class<T> clazz, String field, Object value) {
        try {
            T t = clazz.newInstance();
            set(clazz, t, field, value);
            return t;
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    public static Method getMethod(Object ins, String methodName, Class<?>... parameterTypes) {
        return getMethod(ins.getClass(), methodName, parameterTypes);
    }

    public static Method getMethod(Class<?> clazz, String methodName, Class<?>... parameterTypes) {
        try {
            Method method = getDeclaredMethod(clazz, methodName, parameterTypes);
            return method;
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    protected static Field getDeclaredField(Class<?> clazz, String field) {
	    for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
	        try {
                Field f = clazz.getDeclaredField(field);
                if (f != null) {
                    return f;
                }
            } catch (Throwable e) {}
        }
        return null;
    }

    protected static Method getDeclaredMethod(Class<?> clazz, String method, Class<?>... parameterTypes) {
        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                Method m = clazz.getDeclaredMethod(method, parameterTypes);
                if (m != null) {
                    return m;
                }
            } catch (Throwable e) {}
        }
        return null;
    }
}
