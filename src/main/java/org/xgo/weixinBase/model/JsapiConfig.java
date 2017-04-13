package org.xgo.weixinBase.model;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.xgo.weixinBase.util.WeixinUtil;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * JS SDK Config
 * @author 00277768
 */
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE)
@JsonInclude(Include.NON_NULL)
public class JsapiConfig {

	/** 公众号的唯一标识 */
	private String appId;

	/** 生成签名的时间戳 */
	private Long timestamp;

	/** 生成签名的随机串 */
	private String nonceStr;

	/** 签名 */
	private String signature;

	/** JS SDK ticket */
	@JsonIgnore
	private String jsapiTicket;

	/** 签名用的url必须是调用JS接口页面的完整URL */
	@JsonIgnore
	private String url;

	@JsonIgnore
	private WeixinUtil weixinUtil;

	public JsapiConfig() {
		weixinUtil = new WeixinUtil();
		this.appId = WeixinConfig.INSTANCE.get("GZ_APPID");
		this.timestamp = System.currentTimeMillis() / 1000; // 时间戳精确到�?
		setNonceStr(); // 设置随机字符�?
	}

	public String getAppId() {
		return appId;
	}

	/**
	 * 签名设置—�?�搜集数�?
	 * <p>
	 * 使用URL键�?�对的格式（即key1=value1&key2=value2…）拼接成字符串stringA.
	 * </p>
	 * @return
	 */
	private List<String> getDataToStringList() {
		List<String> result = new LinkedList<String>();
		result.add("noncestr=" + this.nonceStr);
		result.add("jsapi_ticket=" + this.jsapiTicket);
		result.add("timestamp=" + this.timestamp);
		result.add("url=" + this.url);

		return result;
	}

	public String getJsapiTicket() {
		return jsapiTicket;
	}

	public String getNonceStr() {
		return nonceStr;
	}

	public String getSignature() {
		return signature;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public String getUrl() {
		return url;
	}

	public WeixinUtil getWeixinUtil() {
		return weixinUtil;
	}

	public void setJsapiTicket(String jsapiTicket) {
		this.jsapiTicket = jsapiTicket;
	}

	private void setNonceStr() {
		this.nonceStr = weixinUtil.getRandomNumberByLength(16);
	}

	/**
	 * 设置签名
	 */
	public void setSignature() {
		String str = null;
		List<String> list = this.getDataToStringList(); // 收集数据

		int size = list.size();
		String[] arrayToSort = list.toArray(new String[size]);
		Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER); // 排序

		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < size; i++) {
			buffer.append(arrayToSort[i]);
			if (i < size - 1) {
				buffer.append("&");
			}
		}
		str = buffer.toString();
		str = DigestUtils.sha1Hex(str);

		this.signature = str;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("JsapiConfig [");
		if (appId != null) {
			builder.append("appId=");
			builder.append(appId);
			builder.append(", ");
		}
		if (timestamp != null) {
			builder.append("timestamp=");
			builder.append(timestamp);
			builder.append(", ");
		}
		if (nonceStr != null) {
			builder.append("nonceStr=");
			builder.append(nonceStr);
			builder.append(", ");
		}
		if (signature != null) {
			builder.append("signature=");
			builder.append(signature);
			builder.append(", ");
		}
		if (jsapiTicket != null) {
			builder.append("jsapiTicket=");
			builder.append(jsapiTicket);
			builder.append(", ");
		}
		if (url != null) {
			builder.append("url=");
			builder.append(url);
		}
		builder.append("]");
		return builder.toString();
	}

}
