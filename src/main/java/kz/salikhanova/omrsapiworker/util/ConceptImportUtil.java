package kz.salikhanova.omrsapiworker.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Base64;
import java.util.Properties;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import kz.salikhanova.omrsapiworker.App;
import kz.salikhanova.omrsapiworker.model.Concept;
import kz.salikhanova.omrsapiworker.model.ConceptName;

public class ConceptImportUtil {
	
	private static String USER_AGENT;
	
	private static String API_URI;
	
	private static String CREDENTIALS;
	
	private static String APP_DATA;
	
	private String filepath;
	
	private String conceptName;
	
	/*
	 * Number of a column
	 * Starts with 1
	 */
	private int columnNum;
	
	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	public String getConceptName() {
		return conceptName;
	}

	public void setConceptName(String conceptName) {
		this.conceptName = conceptName;
	}

	public int getColumnNum() {
		return columnNum;
	}

	public void setColumnNum(int columnNum) {
		this.columnNum = columnNum;
	}
	
	public void setParams(String filepath, String conceptName, int columnNum){
		this.filepath = filepath;
		this.conceptName = conceptName;
		this.columnNum = columnNum;
	}

	public ConceptImportUtil(String filepath, String conceptName, int columnNum) throws IOException {
		super();
		this.filepath = filepath;
		this.conceptName = conceptName;
		this.columnNum = columnNum;
		API_URI = ProjectValue.getOpenmrsApiUri();
    	CREDENTIALS = ProjectValue.getCredentials();
    	USER_AGENT = ProjectValue.getUserAgent();
    	APP_DATA = ProjectValue.getApplicationDataFolder();
	}

	public void run(){
		String url = API_URI+"concept";
    	Concept concept = null;
    	try{
    		columnNum--;
    		FileReader fr = new FileReader(filepath);
    		BufferedReader br = new BufferedReader(fr);
    		String line;
    		br.readLine();
    		while((line=br.readLine())!=null){
    			line = line.replaceAll("\"", "");
    			String answerName = line.split(";")[columnNum];
    			if(answerName == null || answerName.isEmpty())
    				continue;
    			concept = new Concept();
        		concept.addName(new ConceptName(answerName));
        		concept.setDatatype("8d4a4c94-c2cc-11de-8d13-0010c6dffd0f"); // N/A
        		concept.setConceptClass("8d492774-c2cc-11de-8d13-0010c6dffd0f"); // Misc
        		
        		String uuid = sendPostRequestAsJson(url,concept);
        		storeUuid(uuid);
    		}
    		br.close();
    		fr.close();
    		
    		concept = new Concept();
    		concept.addName(new ConceptName(conceptName));
    		concept.setDatatype("8d4a48b6-c2cc-11de-8d13-0010c6dffd0f"); // Coded
    		concept.setConceptClass("8d491e50-c2cc-11de-8d13-0010c6dffd0f"); // Question
    		String uuidArr[] = restoreUuid(true);
    		for (String uuid : uuidArr) {
    			if(uuid == null || uuid.isEmpty())
    				continue;
    			concept.addAnswer(uuid);
    		}
    		
    		sendPostRequestAsJson(url,concept);
    	} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private String sendPostRequestAsJson(String url, Object obj){
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
    
	private void storeUuid(String uuid){
    	if(uuid != null && !uuid.isEmpty()){
    		try{
        		File appData = new File(APP_DATA);
    			if(!appData.exists()){
    				appData.mkdirs();
    			}
    			File file = new File(APP_DATA+File.separator+"concept_uuid.txt");
    			FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);
    	        BufferedWriter bw = new BufferedWriter(fw);
    	        bw.write(uuid+";");
    	        bw.close();
    	        fw.close();
    		} catch (FileNotFoundException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		} catch (IOException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    	}
    }
    
    public String[] restoreUuid(boolean remove){
    	String uuid[]=null;
    	try{
    		String fileToRead = APP_DATA+File.separator+"concept_uuid.txt";
    		FileReader fr = new FileReader(fileToRead);
    		BufferedReader br = new BufferedReader(fr);
    		String uuidLine;
    		if((uuidLine=br.readLine())!=null){
    			uuidLine = uuidLine.substring(0, uuidLine.length()-1);
    			uuidLine = uuidLine.replaceAll("\"", "");
    			uuid = uuidLine.split(";");
    		}
    		br.close();
    		fr.close();
    		if(remove){
    			File file = new File(APP_DATA+File.separator+"concept_uuid.txt");
        		if(file.delete()){
        			System.out.println("INFO: " + file.getName() + " is succesfully deleted.");
        		}
    		}
    	} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return uuid;
    }
}
