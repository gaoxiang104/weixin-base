package org.xgo.weixinBase.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * 微信配置，单例模式
 * 
 * @author gx
 */
public enum WeixinConfig {
	/** 实例化 */
	INSTANCE;

	/** 微信配置文件的路径 */
	private static final String CONFIG_FILE_PATH = "/config/weixinConfig.xml";
	/** 存放所有配置信息 */
	private Map<String, String> configMap = null;

	/**
	 * 实例化时，解析weixinConfig.xml，并将解析结果放入map
	 * 
	 * @throws DocumentException
	 */
	private WeixinConfig() {
		System.out.println("WeixinConfig instance!");
		SAXReader saxReader = new SAXReader();
		Document document;
		try {
			document = saxReader.read(this.getClass().getResourceAsStream(CONFIG_FILE_PATH));
			// 获取根元素
			Element root = document.getRootElement();

			// 获取所有子元素，即所有的配置信息
			@SuppressWarnings("unchecked")
			List<Element> childList = root.elements();

			configMap = new HashMap<String, String>();
			for (Element element : childList) {
				configMap.put(element.getName(), element.getText());
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}

	/** 获取某一个配置 */
	public String get(String key) {
		String result = configMap.get(key);
		if (StringUtils.isBlank(result)) {
			// TODO 添加异常
		}
		return result;
	}

}
