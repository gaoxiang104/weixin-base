package org.xgo.base;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
public abstract class Controller_ {

	@Autowired
	protected Log_ log;

	/**
	 * @param error 若失败则肯定有消息 
	 * @param object 若成功则有对象或者null
	 * @return Map：error 若失败则肯定有消息，object 若成功则有对象或者null。error和object互斥。
	 */
	protected Map<String, Object> result(String error, Object object) {
		if (error != null && object != null) {
			throw new IllegalArgumentException("error和object非互斥");
		}
		Map<String, Object> result = new HashMap<String, Object>(1);
		if (error != null) {
			result.put("error", error);
			return result;
		}
		if (object != null) {
			result.put("object", object);
		}
		return result;
	}

}
