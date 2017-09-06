package com.sdwangge.policecloud.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.log4j.Logger;

public class ssoDemo {
	Logger logger = Logger.getLogger(ssoDemo.class);

	public String getReturnData(String urlString)
			throws UnsupportedEncodingException {

		String res = "";
		try {
			URL url = new URL(urlString);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			BufferedReader in = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), "UTF-8"));
			String line;
			while ((line = in.readLine()) != null) {
				res += line + "\n";
			}
			in.close();
		} catch (Exception e) {
			logger.error("error in wapaction,and e is " + e.getMessage());
		}
		return res;
	}

	public static void main(String[] args) throws UnsupportedEncodingException {
		ssoDemo sso = new ssoDemo();
		String url = "http://10.48.12.6:8080/JavaAuthFrame/sso.do?sso=1&sessionid=1C49472E1DC3CB713C70DECC3127B4C0";
		String s = sso.getReturnData(url);
		System.out.println(s);
	}
}
