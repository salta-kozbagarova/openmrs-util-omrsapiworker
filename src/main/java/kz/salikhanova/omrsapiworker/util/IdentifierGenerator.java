package kz.salikhanova.omrsapiworker.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Base64;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class IdentifierGenerator {

	private static String IDGEN_URL = "http://localhost:8085/openmrs/module/idgen/generateIdentifier.form?source=1&";
	
	private static String USER_AGENT;
	
	static{
		try {
			IDGEN_URL += ProjectValue.getCredentialsAsUrlParam();
			USER_AGENT = ProjectValue.getUserAgent();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*
	 * Sends request to http://localhost:8085/openmrs/module/idgen/generateIdentifier.form?source=1
	 * and receives JSON object with an identifier with the OpenMRS ID identifier type
	 * 
	 * JSON object that is sent as the response:
	 * 
	 * {
	 * 		"identifiers" : [
	 * 			"10005K"
	 * 		]
	 * }
	 * 
	 * @return String
	 */
	public static String generate(){
		String identifier = null;
		try{
			URL newUrl = new URL(IDGEN_URL);
			HttpURLConnection con = (HttpURLConnection) newUrl.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("User-Agent", USER_AGENT);
			con.setRequestProperty("Content-Type", "application/json");
			
			int responseCode = con.getResponseCode();
			System.out.println("Sending 'GET' request to URL : " + IDGEN_URL);
			System.out.println("Response Code : " + responseCode);

			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			JsonParser jsonParser = new JsonParser();
			JsonElement jsonElement = jsonParser.parse(br);
			JsonObject jsonObject = jsonElement.getAsJsonObject();
			JsonArray identifiers = jsonObject.getAsJsonArray("identifiers");
			identifier = identifiers.get(0).toString();
			identifier = identifier.replaceAll("\"", "");
			br.close();
			System.out.println("Generated Identifier : "+identifier);
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return identifier;
	}
}
