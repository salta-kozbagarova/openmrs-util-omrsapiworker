package kz.salikhanova.omrsapiworker;


import java.io.IOException;

import org.apache.commons.lang3.ArrayUtils;

import kz.salikhanova.omrsapiworker.util.AppointmentTypeImportUtil;
import kz.salikhanova.omrsapiworker.util.ConceptImportUtil;
import kz.salikhanova.omrsapiworker.util.PatientImportUtil;

/**
 * App class
 *
 */
public class App 
{
	
    public static void main( String[] args )
    {
    	// for console running
    	//
    	/*if(!ArrayUtils.isEmpty(args) && args.length!=0){
    		if(args[0].contentEquals("concept")){
    			concept(args[1],args[2],Short.parseShort(args[3]));
    		} else if(args[0].contentEquals("patient")){
    			patient(args[1]);
    		} else if(args[0].contentEquals("appointmenttype")){
    			appointmenttype(args[1], Integer.valueOf(args[2]), Integer.valueOf(args[3]), Integer.valueOf(args[4]));
    		}		
    	}*/
    	//appointmenttype("C:\\Users\\User\\Desktop\\openemr-files\\services.csv", 1, 0, 6);
    	concept("C:\\Users\\User\\Desktop\\openemr-files\\Registration_dictionaries_csv\\social_status.csv","Социальный статус", Short.parseShort("3"));
    }
    
    public static void appointmenttype(String filepath, int nameCol, int descriptionCol, int durationCol){
    	AppointmentTypeImportUtil appointmentTypeImportUtil;
    	
    	try {
			appointmentTypeImportUtil = new AppointmentTypeImportUtil(filepath, nameCol, descriptionCol, durationCol);
			appointmentTypeImportUtil.run();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public static void concept(String filepath, String conceptName, short columnNum){
		ConceptImportUtil conceptImportUtil;
		try {
			conceptImportUtil = new ConceptImportUtil(filepath, conceptName, columnNum);
			conceptImportUtil.run();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public static void patient(String filepath){
    	int givenNameCol = 4;
    	int familyNameCol = 5;
    	int middleNameCol = 6;
    	int iinCol = 7;
    	int birthdateCol = 8;
    	int genderCol = 9;
    	int addressCol = 10;
    	int tphoneCol = 11;
    	int mphoneCol = 12;
    	
    	PatientImportUtil patientImportUtil;
		try {
			patientImportUtil = new PatientImportUtil(filepath, givenNameCol, familyNameCol, middleNameCol, iinCol, birthdateCol, genderCol, addressCol, tphoneCol, mphoneCol);
			patientImportUtil.run();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
