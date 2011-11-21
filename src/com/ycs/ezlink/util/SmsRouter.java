package com.ycs.ezlink.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

public class SmsRouter {

	static ResourceBundle resbun = ResourceBundle.getBundle("sms"); //sms.properties
	protected static Logger logger = Logger.getLogger(SmsRouter.class);

	public String sendSMS(Map<String, String> request) {
		StringBuffer urlBuffer = new StringBuffer();
		StringBuffer checkSumBuffer = new StringBuffer();
		String result = null;
		String inOutParameter = resbun.getString("in.out.parameters");
		String checkSumName = resbun.getString("checksum.param.name");
		String staticVariableParameters = resbun.getString("static.variable.parameters");
		String uniqueIdName = resbun.getString("uniquieid.name");
		String urlMethodHit = resbun.getString("url.method.hit");
		Boolean isUniqueidRequired = new Boolean(resbun.getString("is.uniqueid.required"));
		Boolean isCheckSumRequired = new Boolean(resbun.getString("is.checksum.required"));
		String splashingUrl = resbun.getString("splashing.url");
		int timeOut = new Integer(resbun.getString("time.out"));

//		logger.debug("in.out.parameters [" + inOutParameter + "]");
//		logger.debug("checksum.param.name [" + checkSumName + "]");
//		logger.debug("uniquieid.name [" + uniqueIdName + "]");
//		logger.debug("url.method.hit [" + urlMethodHit + "]");
//		logger.debug("is.uniqueid.required [" + isUniqueidRequired + "]");
//		logger.debug("is.checksum.required [" + isCheckSumRequired + "]");
//		logger.debug("splashing.url [" + splashingUrl + "]");
//		logger.debug("time.out [" + timeOut + "]");
//		logger.debug("static.variable.parameters [" + staticVariableParameters + "]");

		StringTokenizer stoken = new StringTokenizer(inOutParameter, "#");

		ArrayList<String> inOutArrayList = new ArrayList<String>();

		while (stoken.hasMoreElements()) {
			inOutArrayList.add(stoken.nextToken());
		}

		StringTokenizer extraToken = new StringTokenizer(staticVariableParameters, "#");

		ArrayList<String> staticParametersArrayList = new ArrayList<String>();

		while (extraToken.hasMoreElements()) {
			staticParametersArrayList.add(extraToken.nextToken());
		}

		if ("post".equalsIgnoreCase(urlMethodHit)) {
			Hashtable<String, String> keyValue = new Hashtable<String, String>();

			if (isUniqueidRequired) {
				String reqId = String.valueOf(System.currentTimeMillis());
				reqId = reqId.substring(8, reqId.length());
				keyValue.put(uniqueIdName, reqId);
				checkSumBuffer.append(reqId);
			}

			for (int i = 0; i < inOutArrayList.size(); i++) {
				keyValue.put(inOutArrayList.get(i).split("\\|")[1], request.get(inOutArrayList.get(i).split("\\|")[0]));
			}
			if (isCheckSumRequired) {
				for (int i = 0; i < inOutArrayList.size(); i++) {
					checkSumBuffer.append(inOutArrayList.get(i).split("\\|")[0]);
				}
				keyValue.put(checkSumName, makeCheckSum(checkSumBuffer.toString()));
			}

			for (int i = 0; i < staticParametersArrayList.size(); i++) {
				keyValue.put(staticParametersArrayList.get(i).split("\\|")[0], staticParametersArrayList.get(i).split("\\|")[1]);
			}
			try {
				result = HttpClientUtility.sendParameterAsRequest(splashingUrl, keyValue, timeOut);

				logger.debug("return Value [" + result + "]");

			} catch (Exception e) {
				e.printStackTrace();
			}
			logger.debug("Data Moving " + keyValue);
			logger.debug("post zone!!!!");

		} else if ("getwithoutHC".equalsIgnoreCase(urlMethodHit)) {

			urlBuffer.append(splashingUrl).append("?");
			if (isUniqueidRequired) {
				String reqId = String.valueOf(System.currentTimeMillis());
				uniqueIdName = uniqueIdName + "=" + reqId;
				// urlBuffer.append(uniqueIdName);
				reqId = reqId.substring(8, reqId.length());
				checkSumBuffer.append(reqId);
			}

			for (int i = 0; i < inOutArrayList.size(); i++) {
				try {
					String value = request.get(inOutArrayList.get(i).split("\\|")[0]);
					urlBuffer.append(inOutArrayList.get(i).split("\\|")[1]).append("=").append(URLEncoder.encode(value, "UTF-8")).append("&");
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
			for (int i = 0; i < staticParametersArrayList.size(); i++) {
				try {
					String value = staticParametersArrayList.get(i).split("\\|")[1];
					urlBuffer.append(staticParametersArrayList.get(i).split("\\|")[0]).append("=").append(URLEncoder.encode(value, "UTF-8")).append("&");
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
			logger.debug(urlBuffer.length());
			logger.debug("before>>" + urlBuffer.toString());
			urlBuffer.deleteCharAt(urlBuffer.length() - 1);
			logger.debug("After>>>>" + urlBuffer.toString());

			if (isCheckSumRequired) {
				for (int i = 0; i < inOutArrayList.size(); i++) {
					checkSumBuffer.append(inOutArrayList.get(i).split("\\|")[0]);
				}
				try {
					String value = makeCheckSum(checkSumBuffer.toString());
					urlBuffer.append("&").append(checkSumName).append("=").append(URLEncoder.encode(value, "UTF-8"));
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
			try {
				URL url2 = new URL(urlBuffer.toString());
				HttpURLConnection connection = (HttpURLConnection) url2.openConnection();
				connection.setDoOutput(false);
				connection.setDoInput(true);
				logger.debug("Opened Con->" + connection);
				result = connection.getResponseMessage();
				logger.debug("Get Resp ->" + result);
				int code = connection.getResponseCode();
				if (code == HttpURLConnection.HTTP_OK) {
					connection.disconnect();
				}
			} catch (IOException e) {
				logger.debug("unable to create new url" + e.getMessage());
			}

		} else {

			urlBuffer.append(splashingUrl).append("?");
			if (isUniqueidRequired) {
				String reqId = String.valueOf(System.currentTimeMillis());
				uniqueIdName = uniqueIdName + "=" + reqId;
				urlBuffer.append(uniqueIdName).append("&");
				reqId = reqId.substring(8, reqId.length());
				checkSumBuffer.append(reqId);
			}

			for (int i = 0; i < inOutArrayList.size(); i++) {
				urlBuffer.append(inOutArrayList.get(i).split("\\|")[1]).append("=").append(request.get(inOutArrayList.get(i).split("\\|")[0])).append("&");
			}
			for (int i = 0; i < staticParametersArrayList.size(); i++) {
				urlBuffer.append(staticParametersArrayList.get(i).split("\\|")[0]).append("=").append(staticParametersArrayList.get(i).split("\\|")[1]).append("&");
			}
			logger.debug(urlBuffer.length());

			urlBuffer.deleteCharAt(urlBuffer.length() - 1);
			logger.debug("After>>>>" + urlBuffer.toString());

			if (isCheckSumRequired) {
				for (int i = 0; i < inOutArrayList.size(); i++) {
					checkSumBuffer.append(inOutArrayList.get(i).split("\\|")[0]);
				}
				urlBuffer.append("&").append(checkSumName).append("=").append(makeCheckSum(checkSumBuffer.toString()));
			}

			URL url = null;
			try {
				String finalString = urlBuffer.toString();
				// URLEncoder.encode(finalString);
				logger.debug(">>>>>>>>>>" + finalString);
				url = new URL(finalString);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				URLConnection urlConnection = url.openConnection();
				urlConnection.setDoInput(true);
				urlConnection.setDoOutput(true);
				urlConnection.connect();
				InputStream in = urlConnection.getInputStream();
				int ch;
				
				StringBuffer res = new StringBuffer();
				while ((ch = in.read()) != -1) {
					res.append((char)ch);
				}
				result   = res.toString().trim();
				
//				HttpGet httpget = new HttpGet(url.toString());
//				HttpClient client = new DefaultHttpClient();
//				 ResponseHandler<String>  responseHandler = new BasicResponseHandler();
//		          String responseBody = client.execute(httpget, responseHandler);
//		          System.out.println(responseBody.trim());
			} catch (Exception e) {
//				 //TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return result;
	}

	private String makeCheckSum(String value) {
		MessageDigest md;
		byte[] md5hash = new byte[32];
		try {
			md = MessageDigest.getInstance("MD5");
			md.update(value.getBytes("iso-8859-1"), 0, value.length());
			md5hash = md.digest();
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (NoSuchAlgorithmException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return convertToHex(md5hash);
	}

	private static String convertToHex(byte[] data) {
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < data.length; i++) {
			int halfbyte = (data[i] >>> 4) & 0x0F;
			int two_halfs = 0;
			do {
				if ((0 <= halfbyte) && (halfbyte <= 9))
					buf.append((char) ('0' + halfbyte));
				else
					buf.append((char) ('a' + (halfbyte - 10)));
				halfbyte = data[i] & 0x0F;
			} while (two_halfs++ < 1);
		}
		return buf.toString();
	}

	public static void main(String[] args) throws UnsupportedEncodingException {
		Map<String, String> request = new HashMap<String, String>();
		request.put("username", "extfevoagt");
		request.put("password", "3xtf3v0agt");
		request.put("to", "92409553");
//		request.put("text",URLEncoder.encode("Test SMS from Activate Program3", "UTF-8") );
		request.put("text","Test SMS from Activate Program3");

		new SmsRouter().sendSMS(request);

	}
}