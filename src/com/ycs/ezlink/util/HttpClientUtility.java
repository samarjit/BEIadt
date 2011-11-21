package com.ycs.ezlink.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Hashtable;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpConnection;
import org.apache.commons.httpclient.HttpState;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpConnectionParams;

public class HttpClientUtility {
	
	public static String result="";
	@SuppressWarnings("deprecation")
	public static String sendXMLAsRequest(String pgUrl,String requestDoc ,int sockettimeoutPeriod ) throws MalformedURLException,IOException 
	{

		String resp=null;
		URL url = null;
		try {
			url = new URL(pgUrl);

		} catch (MalformedURLException mUE) {
			throw new MalformedURLException("URL is invalid" + pgUrl);
		}

		try
		{
			HttpConnectionParams params = new HttpConnectionParams();
			params.setConnectionTimeout(sockettimeoutPeriod);
			params.setSoTimeout(sockettimeoutPeriod);
			System.out.println("[URL]" + url.getHost() + "[Prot]" + url.getPort());
			HttpConnection connection = new HttpConnection(url.getHost(), url.getPort());
			connection.setParams(params);
			connection.open();

			System.out.println("sendQueryDrRequest sending Request XML " + requestDoc);

			if (!connection.isOpen()) {
				throw new IOException("Failed to open connection!");
			}
			PostMethod method = new PostMethod(url.getPath());
			method.setRequestEntity(new StringRequestEntity(requestDoc));
			resp=receiveResponse(method, connection);
		}

		finally 
		{

		}
		return resp;
	}


	public static String sendParameterAsRequest(String pgUrl,Hashtable<String, String> keyValue ,int sockettimeoutPeriod ) throws MalformedURLException,IOException 
	{
		HttpClient client = new HttpClient();
		String readLine="";
		BufferedReader br = null;
		PostMethod method = new PostMethod(pgUrl);
		method.addParameters(qualifyRequest(keyValue));
		String result1="";

		try{
			int returnCode = client.executeMethod(method);
			if(returnCode == HttpStatus.SC_NOT_IMPLEMENTED) {
				method.getResponseBodyAsString();
			} else {
				br = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream()));
				result1 = br.readLine();
				System.out.println("result ["+result1+"]");
			}
		} catch (Exception e) {
			System.err.println(e);
		} finally {
			method.releaseConnection();
			if(br != null) try { br.close(); } catch (Exception fe) {}
		}
		return result1;
	}

	private static NameValuePair[] qualifyRequest(Hashtable<String, String> keyValue)
	{
		
		 NameValuePair[] keyVal=new NameValuePair[keyValue.size()];
		 int count=0;
		Enumeration<String> keys=keyValue.keys();
		while(keys.hasMoreElements())
		{
			String key=keys.nextElement();
			NameValuePair nameValuObject= new NameValuePair();
			nameValuObject.setName(key);
			nameValuObject.setValue(keyValue.get(key));
			keyVal[count]=nameValuObject;
			count++;
		}
		 
		 return keyVal;
}
	private static String receiveResponse(PostMethod method ,HttpConnection connection)
	{
		String resp="";
		method.setRequestHeader("Content-Type", "text/xml");
		try {
			int httpReturnCode = method.execute(new HttpState(),connection);

			if (httpReturnCode != HttpStatus.SC_OK) 
			{
				System.out.println("*** Msg-Gw return code " + httpReturnCode+ " . Message hasn't been sent out.");
				throw new IOException("HTTP response is ["
						+ httpReturnCode + "/"
						+ HttpStatus.getStatusText(httpReturnCode)
						+ "]!");
			}
			byte[] response = method.getResponseBody();
			System.out.println("Response obtained from servlet is " + response);
			resp=new String(response);
			System.out.println("Returning response from sendRequest" + resp);
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			if(connection!=null)
			{
				connection.close();
			}
		}

		return resp;
	}

}
