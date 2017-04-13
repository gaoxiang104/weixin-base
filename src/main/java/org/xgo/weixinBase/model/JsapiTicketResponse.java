package org.xgo.weixinBase.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 接收JS SDK 获取ticket响应
 * <p>
 * 成功返回如下JSON�?
 * </p>
 * <p>
 * { "errcode":0, "errmsg":"ok", "ticket":"bxLdikRXVbTPdHSM05e5u5sUoXNKd8-41ZO3MhKoyN5OfkWITDGgnr2fwJ0m9E8NYzWKVZvdVtaUgWvsdshFKA", "expires_in":7200 }
 * </p>
 * @author 00277768
 */
@JsonInclude(Include.NON_NULL)
public class JsapiTicketResponse extends WeixinBaseResponse_ {

	private String ticket;

	/** ticket接口调用凭证超时时间，单位（秒） */
	@JsonProperty(value = "expires_in")
	private Long expiresIn;

	public Long getExpiresIn() {
		return expiresIn;
	}

	public String getTicket() {
		return ticket;
	}

	public void setExpiresIn(Long expiresIn) {
		this.expiresIn = expiresIn;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("JsapiTicketResponse [ticket=");
		builder.append(ticket);
		builder.append(", expiresIn=");
		builder.append(expiresIn);
		builder.append(", errcode=");
		builder.append(getErrcode());
		builder.append(", errmsg=");
		builder.append(getErrmsg());
		builder.append("]");
		return builder.toString();
	}

}
