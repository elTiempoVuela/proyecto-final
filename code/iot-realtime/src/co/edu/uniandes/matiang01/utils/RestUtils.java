package co.edu.uniandes.matiang01.utils;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RestUtils {
	

	public static String call(String predict, String params, String auth) {

		String resp = "";
		try {
			OkHttpClient client = new OkHttpClient();
			
			System.out.println(predict);
			
			MediaType mediaType = MediaType.parse("application/json");
			RequestBody body = RequestBody.create(mediaType, params);
			Request request = new Request.Builder()
			.url(predict)
			.post(body)
			.addHeader("content-type", "application/json")
			.addHeader("authorization", auth)
			.addHeader("cache-control", "no-cache")
			.addHeader("postman-token", "a96f105d-d2dd-c4b4-78d2-9b166da6b975")
			.build();
			
			Response response = client.newCall(request).execute();
			resp =  response.body().string();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return resp.replace("[", "").replace("]", "");
	}
	
	public static String call(String url, String msg) {
		String resp = "";
		try {
			System.out.println(url+msg);
			OkHttpClient client = new OkHttpClient();
			Request request = new Request.Builder()
			  .url(url+msg)
			  .get()
			  .addHeader("content-type", "application/json")
			  .addHeader("cache-control", "no-cache")
			  .addHeader("postman-token", "a8128fac-eba9-cbe7-cfbd-b6468e1aae34")
			  .build();
			
			Response response = client.newCall(request).execute();
			resp =  response.body().string();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return resp.replace("[", "").replace("]", "");
	}
	
	
	public static void main(String[] args) {
		//System.out.println(GSonUtils.getTone("{\"tone\":\"Fear\",\"value\":0.999983}").getValue());
		System.out.println(call("http://tone-matiang01.rhcloud.com/api/tone?msg=","anger"));
	}
}

