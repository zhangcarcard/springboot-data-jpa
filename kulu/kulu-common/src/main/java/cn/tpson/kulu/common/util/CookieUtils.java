package cn.tpson.kulu.common.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

public class CookieUtils {
	public static final int MAXAGE = 24 * 3600 * 30;	// 30天
	private CookieUtils() {
		throw new AssertionError("No CookieUtils instances for you!");
	}

	/**
	 * 获取cookie.
	 *
	 * @param request
	 * @param cookieName
	 * @return
	 */
	public static String getCookie(HttpServletRequest request, String cookieName) {
		String cookieValue = null;
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (Objects.equals(cookie.getName(), cookieName)) {
					cookieValue = cookie.getValue();
					break;
				}
			}
		}

		return cookieValue;
	}

	/**
	 * 设置cookie.
	 *
	 * @param name
	 * @param value
	 */
	public static void setCookie(HttpServletResponse response, String name, String value) {
		setCookie(response, name, value, MAXAGE);
	}

	/**
	 *
	 * @param response
	 * @param name
	 * @param value
	 */
	public static void setCookie(HttpServletResponse response, String name, String value, int maxAge) {
		Cookie cookie = new Cookie(name, value);
		cookie.setMaxAge(maxAge);
		cookie.setPath("/");
		response.addCookie(cookie);
	}

	public static void delCookie(HttpServletResponse response, String name) {
		setCookie(response, name, null, 0);
	}
}
