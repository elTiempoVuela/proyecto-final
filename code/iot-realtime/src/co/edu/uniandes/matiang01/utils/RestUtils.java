package co.edu.uniandes.matiang01.utils;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RestUtils {
	
	public static void main(String[] args) throws IOException {
		
		OkHttpClient client = new OkHttpClient();
		
		MediaType mediaType = MediaType.parse("application/json");
		RequestBody body = RequestBody.create(mediaType, "[[\"1\", \"2\", \"2\", \"2\", \"0\"]]");
		Request request = new Request.Builder()
		.url("http://localhost:9443/api/models/8/predict?percentile=98")
		.post(body)
		.addHeader("content-type", "application/json")
		.addHeader("authorization", "Basic YWRtaW46YWRtaW4=")
		.addHeader("cache-control", "no-cache")
		.addHeader("postman-token", "a96f105d-d2dd-c4b4-78d2-9b166da6b975")
		.build();
		
		Response response = client.newCall(request).execute();
		System.out.println(response.body().string());
	}
}

