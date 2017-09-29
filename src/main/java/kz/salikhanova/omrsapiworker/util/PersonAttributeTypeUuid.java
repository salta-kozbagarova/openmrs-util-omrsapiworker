package kz.salikhanova.omrsapiworker.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class PersonAttributeTypeUuid {

	private static String API_URI;
	
	private static String iin;
	
	private static String telephoneNumber;
	
	private static String mobilePhoneNumber;
	
	static{
		try {
			API_URI = ProjectValue.getOpenmrsApiUri();
			if(iin==null || telephoneNumber==null || mobilePhoneNumber==null){
				restoreUuids();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String getIin() {
		return iin;
	}

	public static String getTelephoneNumber() {
		return telephoneNumber;
	}

	public static String getMobilePhoneNumber() {
		return mobilePhoneNumber;
	}

	private static void restoreUuids() throws IOException{
		String url = API_URI + "personattributetype";
		URL newUrl = new URL(url);
		HttpURLConnection con = (HttpURLConnection) newUrl.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent", "Mozilla/5.0");
		con.setRequestProperty("Authorization", "Basic " + ProjectValue.getCredentials());
		con.setRequestProperty("Content-Type", "application/json");
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		int responseCode = con.getResponseCode();
		System.out.println("Sending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);

		BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
		JsonParser jsonParser = new JsonParser();
		JsonElement jsonElement = jsonParser.parse(br);
		String prettyJson = gson.toJson(jsonElement);
		JsonObject jsonObject = jsonElement.getAsJsonObject();
		JsonArray jsonArray = jsonObject.get("results").getAsJsonArray();
		System.out.println("setting uuids");
		for (JsonElement jsonElem : jsonArray) {
			if(jsonElem.getAsJsonObject().get("display").toString().replace("\"", "").contentEquals("IIN")){
				iin = jsonElem.getAsJsonObject().get("uuid").toString().replace("\"", "").trim();
				System.out.println("iin : "+iin);
			}
			if(jsonElem.getAsJsonObject().get("display").toString().replace("\"", "").contentEquals("Telephone Number")){
				telephoneNumber = jsonElem.getAsJsonObject().get("uuid").toString().replace("\"", "").trim();
				System.out.println("telephoneNumber : "+telephoneNumber);
			}
			if(jsonElem.getAsJsonObject().get("display").toString().replace("\"", "").contentEquals("Mobile Phone Number")){
				mobilePhoneNumber = jsonElem.getAsJsonObject().get("uuid").toString().replace("\"", "").trim();
				System.out.println("mobilePhoneNumber : "+mobilePhoneNumber);
			}
		}
		br.close();
	}
}
