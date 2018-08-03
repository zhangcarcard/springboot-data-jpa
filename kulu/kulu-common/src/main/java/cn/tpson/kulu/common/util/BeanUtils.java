package cn.tpson.kulu.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 
 * @author zk
 *
 */
public class BeanUtils {
	private static final Logger log = LoggerFactory.getLogger(BeanUtils.class);
	
	private BeanUtils() {
		throw new AssertionError("No BeanUtils instances for you!");
	}

	public static void copyProperties(Object dest, Object source, String... properties) {
	    org.springframework.beans.BeanUtils.copyProperties(source, dest, properties);
    }

	public static <T> T newAndCopyProperties(final Class<T> dest, final Object source) {
		if (source == null) {
			return null;
		}
		
		try {
			T t = dest.newInstance();
			copyProperties(t, source);
			return t;
		} catch (IllegalAccessException | InstantiationException e) {
			log.error("拷贝对象出错！", e);
			return null;
		}
	}
	
	public static <T> List<T> newAndCopyPropertiesForList(final Class<T> dest, final Iterable<?> sources) {
		if (sources == null) {
			return Collections.emptyList();
		}
		
		List<T> list = new ArrayList<>();
		for (Object source : sources) {
			T t;
			try {
				t = dest.newInstance();
				copyProperties(t, source);
			} catch (InstantiationException | IllegalAccessException e) {
				log.error("拷贝列表对象出错！", e);
				return Collections.emptyList();
			}
			list.add(t);
		}
		
		return list;
	}
}
