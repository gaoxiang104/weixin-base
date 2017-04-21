package org.xgo.weixinBase.exception;

/**
 * 找不到weixinConfig.xml中的某项配置
 * @author gaoxiang
 *
 */
public class NoFindWeixinConfigException extends WeixinBaseException {

	static final long serialVersionUID = 1717569872202368983L;

	public NoFindWeixinConfigException() {
		super();
	}

	/**
	 * 详细指出查询的哪项配置不存在
	 * @param weixinConfigName 查询的配置名称
	 */
	public NoFindWeixinConfigException(String weixinConfigName) {
		super("('"+weixinConfigName+"') is not find" );
	}
	
}
