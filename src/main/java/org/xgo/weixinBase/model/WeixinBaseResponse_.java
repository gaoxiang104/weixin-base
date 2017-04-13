package org.xgo.weixinBase.model;

/**
 * 公众平台返回基础类
 * @author 00277768
 */
public abstract class WeixinBaseResponse_ {
	/** 错误编号 */
	private Integer errcode;

	/** 错误信息 */
	private String errmsg;

	public Integer getErrcode() {
		return errcode;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrcode(Integer errcode) {
		this.errcode = errcode;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

}
