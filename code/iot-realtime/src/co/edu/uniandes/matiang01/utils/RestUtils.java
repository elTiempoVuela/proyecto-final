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
			e.printStackTrace();
		}
		return resp;
	}
}

