package kz.salikhanova.omrsapiworker.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.Properties;

public class ProjectValue {
	
	public static String getApplicationDataFolder(){
		return System.getenv("APPDATA")+File.separator+"omrsapiworker";
	}
	
	public static String getOpenmrsApiUri() throws IOException{
		Properties properties = new Properties();
    	InputStream inputStream = ProjectValue.class.getClassLoader().getResourceAsStream("params.properties");
    	properties.load(inputStream);
    	return properties.getProperty("openmrs.api.uri").toString();
	}
	
	public static String getUserAgent() throws IOException{
		Properties properties = new Properties();
    	InputStream inputStream = ProjectValue.class.getClassLoader().getResourceAsStream("params.properties");
    	properties.load(inputStream);
    	return properties.getProperty("user.agent").toString();
	}
	
	public static String getCredentials() throws IOException{
		Properties properties = new Properties();
    	InputStream inputStream = ProjectValue.class.getClassLoader().getResourceAsStream("params.properties");
    	properties.load(inputStream);
    	return Base64.getEncoder().encodeToString((properties.getProperty("username")+":"+properties.getProperty("password")).getBytes());
	}
	
	public static String getCredentialsAsUrlParam() throws IOException{
		Properties properties = new Properties();
    	InputStream inputStream = ProjectValue.class.getClassLoader().getResourceAsStream("params.properties");
    	properties.load(inputStream);
    	return "username="+properties.getProperty("username")+"&password="+properties.getProperty("password");
	}
}
