package cn.tpson.kulu.common.util;

import java.util.UUID;

/**
 *
 */
public class UUIDUtils {
	private UUIDUtils() {
		throw new AssertionError("No UUIDUtils instances for you!");
	}
	
	public static String uuid() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
}
