package kz.salikhanova.omrsapiworker.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import org.apache.commons.lang3.ArrayUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import kz.salikhanova.omrsapiworker.model.AppointmentType;

public class AppointmentTypeImportUtil {

	private static String USER_AGENT;
	
	private static String API_URI;
	
	private static String CREDENTIALS;
	
	private static String APP_DATA;
	
	private String filepath;
	
	private int nameCol;
	
	private int descriptionCol;
	
	private int durationCol;
	
	public AppointmentTypeImportUtil(String filepath, int nameCol, int descriptionCol, int durationCol) throws IOException {
		super();
		this.filepath = filepath;
		this.nameCol = nameCol;
		this.descriptionCol = descriptionCol;
		this.durationCol = durationCol;
		API_URI = ProjectValue.getOpenmrsApiUri();
    	CREDENTIALS = ProjectValue.getCredentials();
    	USER_AGENT = ProjectValue.getUserAgent();
    	APP_DATA = ProjectValue.getApplicationDataFolder();
	}
	
	public void run(){
		String url = API_URI+"appointmentscheduling/appointmenttype";
    	AppointmentType appointmentType = null;
    	try{
    		FileReader fr = new FileReader(filepath);
    		BufferedReader br = new BufferedReader(fr);
    		String line;
    		br.readLine();
    		while((line=br.readLine())!=null){
    			line = line.replaceAll("\"", "");
    			String[] fields = line.split(";");
    			if(fields == null || ArrayUtils.isEmpty(fields)){
    				continue;
    			}
    			appointmentType = new AppointmentType();
    			for (int i = 0; i < fields.length; i++) {
					if(nameCol-1 == i){
						appointmentType.setName(fields[i]);
					}
					if(descriptionCol-1 == i){
						appointmentType.setDescription(fields[i]);
					}
					if(durationCol-1 == i){
						int duration = Integer.valueOf(fields[i]);
						duration = duration/60;
						appointmentType.setDuration(String.valueOf(duration));
					}
				}
    			if(descriptionCol == 0){
    				appointmentType.setDescription("");
    			}
    			sendPostRequestAsJson(url,appointmentType);
    		}
    		br.close();
    		fr.close();
    	} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String sendPostRequestAsJson(String url, Object obj){
    	Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String jsonData = gson.toJson(obj);
		System.out.println("REQUEST JSON DATA");
		System.out.println("==================================================================");
		System.out.println(jsonData);
		System.out.println("==================================================================");
		String uuid=null;
		try{
			URL newUrl = new URL(url);
			HttpURLConnection con = (HttpURLConnection) newUrl.openConnection();
			con.setRequestMethod("POST");
			con.setDoOutput(true);
			con.setRequestProperty("User-Agent", USER_AGENT);
			con.setRequestProperty("Authorization", "Basic " + CREDENTIALS);
			con.setRequestProperty("Content-Type", "application/json");
			con.setRequestProperty("Content-Length", String.valueOf(jsonData.length()));
			OutputStream os = con.getOutputStream();
			os.write(jsonData.toString().getBytes("UTF-8"));
			os.close();
			
			int responseCode = con.getResponseCode();
			System.out.println("Sending 'POST' request to URL : " + url);
			System.out.println("Response Code : " + responseCode);

			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			JsonParser jsonParser = new JsonParser();
			JsonElement jsonElement = jsonParser.parse(br);
			String prettyJson = gson.toJson(jsonElement);
			JsonObject jsonObject = jsonElement.getAsJsonObject();
			uuid = jsonObject.get("uuid").toString();
			br.close();
			
			//print result
			System.out.println("RESPONSE JSON DATA");
			System.out.println("==================================================================");
			System.out.println(prettyJson.toString());
			System.out.println("==================================================================");
		} catch(MalformedURLException e){
    		e.printStackTrace();
    	} catch(ProtocolException e){
    		e.printStackTrace();
    	} catch(IOException e){
    		e.printStackTrace();
    		System.out.println("MESSAGE::");
    		e.getMessage();
    		e.getCause().printStackTrace();
    	}
		return uuid;
    }
}
