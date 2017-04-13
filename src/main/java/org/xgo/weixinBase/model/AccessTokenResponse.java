package org.xgo.weixinBase.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * AccessToken接收响应
 * @author 00277768
 */
@JsonInclude(Include.NON_NULL)
public class AccessTokenResponse extends WeixinBaseResponse_ {

	/** 获取到的凭证 */
	@JsonProperty(value = "access_token")
	private String accessToken;

	/** 凭证有效时间，单位：秒 */
	@JsonProperty(value = "expires_in")
	private Long expiresIn;

	public String getAccessToken() {
		return accessToken;
	}

	public Long getExpiresIn() {
		return expiresIn;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public void setExpiresIn(Long expiresIn) {
		this.expiresIn = expiresIn;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AccessTokenResponse [");
		if (accessToken != null) {
			builder.append("accessToken=");
			builder.append(accessToken);
			builder.append(", ");
		}
		if (expiresIn != null) {
			builder.append("expiresIn=");
			builder.append(expiresIn);
			builder.append(", ");
		}
		if (getErrcode() != null) {
			builder.append("errcode=");
			builder.append(getErrcode());
			builder.append(", ");
		}
		if (getErrmsg() != null) {
			builder.append("errmsg=");
			builder.append(getErrmsg());
		}
		builder.append("]");
		return builder.toString();
	}

}
