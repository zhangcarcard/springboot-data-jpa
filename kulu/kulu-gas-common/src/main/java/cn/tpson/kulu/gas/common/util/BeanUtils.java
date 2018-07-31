package cn.tpson.kulu.gas.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 
 * @author zk
 *
 */
public class BeanUtils {
	private static final Logger LOGGER = LoggerFactory.getLogger(BeanUtils.class);
	
	private BeanUtils() {
		throw new AssertionError("No cn.tpson.kulu.gas.common.util.BeanUtils instances for you!");
	}
	
	public static <T> T copyProperties(final Class<T> dest, final Object orig) {
		if (orig == null) {
			return null;
		}
		
		T t;
		try {
			t = dest.newInstance();
			org.apache.commons.beanutils.BeanUtils.copyProperties(t, orig);
		} catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
			LOGGER.error("拷贝对象出错！", e);
			return null;
		}
		
		return t;
	}
	
	public static <T> List<T> copyPropertiesForList(final Class<T> dest, final Iterable<?> origs) {
		if (origs == null) {
			return Collections.emptyList();
		}
		
		List<T> list = new ArrayList<>();
		for (Object orig : origs) {
			T t;
			try {
				t = dest.newInstance();
				org.apache.commons.beanutils.BeanUtils.copyProperties(t, orig);
			} catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
				LOGGER.error("拷贝对象出错！", e);
				return Collections.emptyList();
			}
			list.add(t);
		}
		
		return list;
	}
}
