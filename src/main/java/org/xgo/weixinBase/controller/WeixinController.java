package org.xgo.weixinBase.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xgo.base.Controller_;
import org.xgo.weixinBase.service.WeixinService;

@RestController
@RequestMapping(value = "/", consumes = "application/json", produces = "application/json")
public class WeixinController extends Controller_ {

	@Autowired
	private WeixinService weixinService;

	/**
	 * 获取accessToken
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getAccessToken.ctrl")
	public Map<String, Object> getAccessToken(HttpServletRequest request) {
		String error = null;
		Object object = null;
		if (!request.getLocalAddr().equals(request.getRemoteAddr())) {
			error = "禁止访问";
		}
		else {
			try {
				object = weixinService.getAccessToken();
			}
			catch (IllegalStateException i) {
				error = i.getMessage();
				log.getLogger("weixin_c").warn("WeixinController.getAccessToken() fail: {}", error);
			}
			catch (Exception e) {
				error = e.getMessage();
				log.getLogger("weixin_c").error("WeixinController.getAccessToken(): ");
				log.getLogger("weixin_c").error("StackTrace: ", e);
			}
		}
		return result(error, object);
	}

	/**
	 * 获取JS SDK Config </br>
	 * param.pageUrl 引用JS SDK的页面地址</br>
	 * 
	 * @param param.pageUrl
	 *            页面地址
	 * @return
	 */
	@RequestMapping(value = "/getJsapiConfig.ctrl")
	public Map<String, Object> getJsapiConfig(@RequestBody Map<String, String> param) {
		String error = null;
		Object object = null;
		if (StringUtils.isBlank(param.get("pageUrl"))) {
			error = "页面地址为空";
		}
		else {
			try {
				String pageUrl = param.get("pageUrl"); // 页面URL
				object = weixinService.getJsapiConfig(pageUrl);
				log.getLogger("weixin_c").info("WeixinController.getJsapiConfig({})", object);
			}
			catch (IllegalStateException i) {
				error = i.getMessage();
				log.getLogger("weixin_c").warn("WeixinController.getJsapiConfig() fail: {}", error);
			}
			catch (Exception e) {
				error = e.getMessage();
				log.getLogger("weixin_c").error("WeixinController.getJsapiConfig(): ");
				log.getLogger("weixin_c").error("StackTrace: ", e);
			}
		}
		return result(error, object);
	}

}
