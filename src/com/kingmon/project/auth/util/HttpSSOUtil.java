package com.kingmon.project.auth.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpSSOUtil {
	public static  String httpClientRquest(String sessionId,String authssoUrl) {
		//authssoUrl: http://10.48.12.6:8080/JavaAuthFrame/sso.do
		String urlx = authssoUrl+"?sso=1&sessionid="+sessionId;
		String res = "";
		try {
			URL url = new URL(urlx);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			String line;
			while ((line = in.readLine()) != null) {
				res += line + "\n";
			}
			in.close();
		} catch (Exception e) {
//			logger.error("error in wapaction,and e is " + e.getMessage());
			return null;
		}
		return res;
}
}
