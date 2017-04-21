package org.xgo.weixinBase.service;

import javax.annotation.PostConstruct;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xgo.base.Log_;
import org.xgo.weixinBase.model.AccessTokenResponse;
import org.xgo.weixinBase.model.JsapiConfig;
import org.xgo.weixinBase.model.JsapiTicketResponse;
import org.xgo.weixinBase.model.WeixinConfig;
import org.xgo.weixinBase.model.WeixinConfigStatic;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class WeixinService {

	/** 记录公众平台ACCESS TOKEN生成的时间 */
	private static Long accessTokenTimestamp;

	/** 记录公众平台ACCESS TOKEN，有效时间7200秒 */
	private static AccessTokenResponse accessToken;

	/** 记录公众平台 JS SDK jsapi_ticket，有效时间7200秒 */
	private static JsapiTicketResponse jsapiTicket;

	@Autowired
	protected Log_ log;

	/**
	 * 获取accessToken
	 * 
	 * @return
	 * @throws WeixinException
	 * @throws Exception
	 */
	public String getAccessToken() throws Exception {
		// 判断accessToken是否在有效期内
		if (accessToken == null || accessTokenTimestamp + accessToken.getExpiresIn() < System.currentTimeMillis()) { // 不在有效期内刷新
			refreshTicket(); // 刷新票据
		}
		return accessToken.getAccessToken();
	}

	/**
	 * 获取微信公众平台JS SDK Config
	 * 
	 * @param pageUrl
	 * @return
	 */
	public JsapiConfig getJsapiConfig(String pageUrl) throws Exception {
		// 判断accessToken是否在有效期内
		if (accessToken == null || accessTokenTimestamp + jsapiTicket.getExpiresIn() < System.currentTimeMillis()) { // 不在有效期内刷新
			refreshTicket(); // 刷新票据
		}
		JsapiConfig jsapiConfigChunjieShare = new JsapiConfig();
		jsapiConfigChunjieShare.setJsapiTicket(jsapiTicket.getTicket()); // 设置Ticket
		jsapiConfigChunjieShare.setUrl(pageUrl); // 设置Url
		jsapiConfigChunjieShare.setSignature(); // 生成签名

		return jsapiConfigChunjieShare;
	}

	/**
	 * 新获取公众平台ACCESS TOKEN
	 * 
	 * @throws WeixinException
	 * @throws Exception
	 */
	private void getNewAccessToken() throws Exception {
		accessTokenTimestamp = System.currentTimeMillis(); // 设置accessToken生成时间
		log.getLogger("weixin_s").info("WeixinService.getNewAccessToken.timeStanmp : " + accessTokenTimestamp);
		StringBuffer url = new StringBuffer(); // 请求URL+parm
		url.append(WeixinConfig.INSTANCE.get(WeixinConfigStatic.GZ_URL_GET_ACCESS_TOKEN));
		url.append("?grant_type=client_credential&appid=");
		url.append(WeixinConfig.INSTANCE.get(WeixinConfigStatic.GZ_APPID)); // appid
		url.append("&secret=");
		url.append(WeixinConfig.INSTANCE.get(WeixinConfigStatic.GZ_APPSECRET)); // appid

		HttpGet httpGet = new HttpGet(url.toString());
		httpGet.addHeader("Content-Type", "application/json; encoding=utf-8");

		HttpClient httpClient = HttpClientBuilder.create().build();

		// 记录请求
		log.getLogger("weixin_s").info("WeixinService.getNewAccessToken.requestUrl : " + url);
		// 发送请求
		HttpResponse response = httpClient.execute(httpGet);
		// 解析应答
		HttpEntity entity = response.getEntity();
		String accessTokenResponseString = EntityUtils.toString(entity);
		// 记录应答
		log.getLogger("weixin_s").info("WeixinService.getNewAccessToken.respones : " + accessTokenResponseString);
		ObjectMapper mapper = new ObjectMapper();
		AccessTokenResponse accessTokenResponse = mapper.readValue(accessTokenResponseString, AccessTokenResponse.class);
		if (accessTokenResponse.getErrcode() != null) {
			throw new IllegalStateException(accessTokenResponse.getErrcode() + ": " + accessTokenResponse.getErrmsg());
		}
		accessToken = accessTokenResponse;
	}

	/**
	 * 刷新公众号用于调用微信JS接口的临时票据
	 * 
	 * @param jsapiTicket
	 *            : jsapiTicketShare Or jsapiTicketRp
	 * @throws WeixinException
	 * @throws Exception
	 */
	private void refreshJsapiTicket() throws Exception {
		StringBuffer url = new StringBuffer(); // 请求URL+parm
		url.append(WeixinConfig.INSTANCE.get(WeixinConfigStatic.GZ_URL_GET_JSAPI_TICKET));
		url.append("?access_token=");
		url.append(accessToken.getAccessToken()); // access token
		url.append("&type=jsapi");

		HttpGet httpGet = new HttpGet(url.toString());
		httpGet.addHeader("Content-Type", "application/json; encoding=utf-8");

		HttpClient httpClient = HttpClientBuilder.create().build();

		// 记录请求
		log.getLogger("weixin_s").info("WeixinService.getJsapiTicket.requestUrl : " + url);
		// 发送请求
		HttpResponse response = httpClient.execute(httpGet);
		// 解析应答
		HttpEntity entity = response.getEntity();
		String jsapiTicketResponseString = EntityUtils.toString(entity);
		// 记录应答
		log.getLogger("weixin_s").info("WeixinService.getJsapiTicket.respones : " + jsapiTicketResponseString);

		ObjectMapper mapper = new ObjectMapper();
		JsapiTicketResponse jsapiTicketResponse = mapper.readValue(jsapiTicketResponseString, JsapiTicketResponse.class);
		if (jsapiTicketResponse.getErrcode() != null && jsapiTicketResponse.getErrcode() != 0) { // 获取ticket错误
			throw new IllegalStateException(jsapiTicketResponse.getErrcode() + ": " + jsapiTicketResponse.getErrmsg());
		}
		jsapiTicket = jsapiTicketResponse;
	}

	/**
	 * 刷新公众平台票据</br>
	 * 包含：</br>
	 * 1，accessToken</br>
	 * 2，jsapiTicket，微信JS SDK jsapi_ticket</br>
	 */
	@PostConstruct
	private void refreshTicket() throws Exception {
		getNewAccessToken();
		refreshJsapiTicket();
	}
}
