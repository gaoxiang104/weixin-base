package org.xgo.weixinBase.util;

import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.Random;

import javax.net.ssl.SSLContext;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.ssl.SSLContexts;


public class WeixinUtil {

	/**
	 * Key store 类型HttpClient
	 */
	public HttpClient createKeyMaterialHttpClient(KeyStore keystore, String keyPassword, String[] supportedProtocols) throws KeyManagementException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException {
		// Trust own CA and all self-signed certs
		SSLContext sslContext = SSLContexts.custom().loadKeyMaterial(keystore, keyPassword.toCharArray()).build();
		SSLConnectionSocketFactory sf = new SSLConnectionSocketFactory(sslContext, supportedProtocols, null, SSLConnectionSocketFactory.getDefaultHostnameVerifier());
		return HttpClientBuilder.create().setSSLSocketFactory(sf).build();
	}

	/**
	 * 取MD5加密
	 */
	public String getEncryptMD5(String origin) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		try {
			byte[] btInput = origin.getBytes("UTF-8");
			// 获得MD5摘要算法�? MessageDigest 对象
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			// 使用指定的字节更新摘�?
			mdInst.update(btInput);
			// 获得密文
			byte[] md = mdInst.digest();
			// 把密文转换成十六进制的字符串形式
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 生成指定位数的随机整数数
	 * @param length 随机数长度
	 */
	public String getRandomNum(int length) {
		String val = "";
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			val += String.valueOf(random.nextInt(10));
		}
		return val;
	}

	/**
	 * 取随机数
	 * @param length 随机数长度
	 */
	public String getRandomNumberByLength(int length) {
		String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}

}
