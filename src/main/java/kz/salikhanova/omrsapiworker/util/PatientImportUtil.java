package kz.salikhanova.omrsapiworker.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
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

import kz.salikhanova.omrsapiworker.model.Patient;
import kz.salikhanova.omrsapiworker.model.PatientIdentifier;
import kz.salikhanova.omrsapiworker.model.Person;
import kz.salikhanova.omrsapiworker.model.PersonAddress;
import kz.salikhanova.omrsapiworker.model.PersonAttribute;
import kz.salikhanova.omrsapiworker.model.PersonAttributeType;
import kz.salikhanova.omrsapiworker.model.PersonName;

public class PatientImportUtil {

	private static String USER_AGENT;
	
	private static String API_URI;
	
	private static String CREDENTIALS;
	
	private static String APP_DATA = System.getenv("APPDATA")+File.separator+"omrsapiworker";
	
	private String filepath;
	
	private int givenNameCol;
	
	private int familyNameCol;
	
	private int middleNameCol;
	
	private int iinCol;
	
	private int birthdateCol;
	
	private int genderCol;
	
	private int addressCol;
	
	private int tphoneCol;
	
	private int mphoneCol;
	
	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}
	
	public int getGivenNameCol() {
		return givenNameCol;
	}

	public void setGivenNameCol(int givenNameCol) {
		this.givenNameCol = givenNameCol;
	}

	public int getFamilyNameCol() {
		return familyNameCol;
	}

	public void setFamilyNameCol(int familyNameCol) {
		this.familyNameCol = familyNameCol;
	}

	public int getMiddleNameCol() {
		return middleNameCol;
	}

	public void setMiddleNameCol(int middleNameCol) {
		this.middleNameCol = middleNameCol;
	}

	public int getIinCol() {
		return iinCol;
	}

	public void setIinCol(int iinCol) {
		this.iinCol = iinCol;
	}

	public int getBirthdateCol() {
		return birthdateCol;
	}

	public void setBirthdateCol(int birthdateCol) {
		this.birthdateCol = birthdateCol;
	}

	public int getGenderCol() {
		return genderCol;
	}

	public void setGenderCol(int genderCol) {
		this.genderCol = genderCol;
	}

	public int getAddressCol() {
		return addressCol;
	}

	public void setAddressCol(int addressCol) {
		this.addressCol = addressCol;
	}

	public int getTphoneCol() {
		return tphoneCol;
	}

	public void setTphoneCol(int tphoneCol) {
		this.tphoneCol = tphoneCol;
	}

	public int getMphoneCol() {
		return mphoneCol;
	}

	public void setMphoneCol(int mphoneCol) {
		this.mphoneCol = mphoneCol;
	}
	
	public PatientImportUtil(String filepath, int givenNameCol, int familyNameCol, int middleNameCol, int iinCol,
			int birthdateCol, int genderCol, int addressCol, int tphoneCol, int mphoneCol) throws IOException {
		super();
		this.filepath = filepath;
		this.givenNameCol = givenNameCol;
		this.familyNameCol = familyNameCol;
		this.middleNameCol = middleNameCol;
		this.iinCol = iinCol;
		this.birthdateCol = birthdateCol;
		this.genderCol = genderCol;
		this.addressCol = addressCol;
		this.tphoneCol = tphoneCol;
		this.mphoneCol = mphoneCol;
		API_URI = ProjectValue.getOpenmrsApiUri();
		CREDENTIALS = ProjectValue.getCredentials();
		USER_AGENT = ProjectValue.getUserAgent();
		APP_DATA = ProjectValue.getApplicationDataFolder();
	}

	public void run(){
    	String url = API_URI+"person";
    	Person person = null;
    	try{
    		FileReader fr = new FileReader(filepath);
    		BufferedReader br = new BufferedReader(fr);
    		String line;
    		br.readLine();
    		while((line=br.readLine())!=null){
    			line = line.replaceAll("\"", "");
    			String[] fields = line.split(";");
    			if(fields == null || ArrayUtils.isEmpty(fields))
    				continue;
    			person = new Person();
    			for (int i = 0; i < fields.length; i++) {
					if(givenNameCol-1 == i){
						person.addName(new PersonName(fields[givenNameCol-1], fields[familyNameCol-1], fields[middleNameCol-1]));
					}
					if(genderCol-1 == i){
						person.setGender(fields[genderCol-1]);
					}
					if(birthdateCol-1 == i){
						String birthdate = fields[birthdateCol-1];
						if(birthdate==null || birthdate.isEmpty())
							continue;
						StringBuilder sb = new StringBuilder();
						sb.append(birthdate.substring(0, 4)).append("-").append(birthdate.substring(4,6)).append("-").append(birthdate.substring(6));
						person.setBirthdate(sb.toString());
					}
					if(addressCol-1 == i){
						person.addAddress(new PersonAddress(fields[addressCol-1]));
					}
					if(iinCol-1 == i){
						person.addAttribute(new PersonAttribute(fields[iinCol-1], PersonAttributeType.getInstanceOfIIN()));
					}
					if(tphoneCol-1 == i){
						person.addAttribute(new PersonAttribute(fields[tphoneCol-1], PersonAttributeType.getInstanceOfTelephoneNumber()));
					}
					if(mphoneCol-1 == i){
						person.addAttribute(new PersonAttribute(fields[mphoneCol-1], PersonAttributeType.getInstanceOfMobilePhoneNumber()));
					}
				}
    			String uuid = sendPostRequestAsJson(url,person);
        		storeUuid(uuid);
    		}
    		br.close();
    		fr.close();
    		
    		Patient patient = null;
    		String uuidArr[] = restoreUuid(true);
    		for (String uuid : uuidArr) {
    			if(uuid == null || uuid.isEmpty())
    				continue;
    			patient = new Patient();
    			patient.addIdentifier(new PatientIdentifier());
    			patient.setPerson(uuid);
    			
    			sendPostRequestAsJson(API_URI+"patient",patient);
    		}
    		
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
    
    public void storeUuid(String uuid){
    	if(uuid != null && !uuid.isEmpty()){
    		try{
        		File appData = new File(APP_DATA);
    			if(!appData.exists()){
    				appData.mkdirs();
    			}
    			File file = new File(APP_DATA+File.separator+"person_uuid.txt");
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
    		String fileToRead = APP_DATA+File.separator+"person_uuid.txt";
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
    			File file = new File(APP_DATA+File.separator+"person_uuid.txt");
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
