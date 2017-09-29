package kz.salikhanova.omrsapiworker.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.Properties;

import org.openmrs.Concept;
import org.openmrs.ConceptName;
import org.openmrs.api.ConceptService;
import org.openmrs.api.context.Context;
import org.openmrs.module.ModuleMustStartException;
import org.openmrs.util.OpenmrsUtil;

public class openmrsapitest {

	private static String APP_DATA = System.getenv("APPDATA")+File.separator+"omrsapiworker";
	
	public static void main(){
		openmrsapi();
	}
	
	public static void openmrsapi(){
    	File propsFile = new File(OpenmrsUtil.getApplicationDataDirectory(), "openmrs-runtime.properties");
    	Properties props = new Properties();
    	OpenmrsUtil.loadProperties(props, propsFile);
    	try {
    		//Context.updateDatabase(null);
			Context.startup("jdbc:mysql://localhost:3306/openmrs?autoReconnect=true", "openmrs", "openmrs", props);
		} catch (ModuleMustStartException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		/*} catch (DatabaseUpdateException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InputRequiredException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		*/} catch (Exception e) {
			e.printStackTrace();
		} finally{
			/*System.out.println("we r here");
			Context.openSession();
		    Context.authenticate("admin", "Qzwxec123");
		    List<Patient> patients = Context.getPatientService().getPatients("qw");
		    for (Patient patient : patients) {
		      System.out.println("Found patient with name " + patient.getPersonName() + " and uuid: " + patient.getUuid());
		    }
		    Context.closeSession();*/
		}
    	
    	try{
    		Context.openSession();
		    Context.authenticate("admin", "Qzwxec123");
		    
		    
		    ConceptService conceptService = Context.getConceptService();
		    /*Concept oConcept = conceptService.getConcept(161908);
		    List<Concept> c = conceptService.getConceptsByAnswer(oConcept);
		    System.out.println(c.size() + "is length");
		    for (Concept concept : c) {
				System.out.println(concept.getName()+" : "+concept.getUuid());
			}*/
			Concept oConcept = new Concept();//conceptService.getConceptByUuid("abeeeb18-8a1a-45fe-9565-7865f5a962f9");
			oConcept.setFullySpecifiedName(new ConceptName("test question api 1", Locale.ENGLISH));
			oConcept.setDatatype(conceptService.getConceptDatatypeByUuid("8d4a48b6-c2cc-11de-8d13-0010c6dffd0f"));
			oConcept.setConceptClass(conceptService.getConceptClassByUuid("8d491e50-c2cc-11de-8d13-0010c6dffd0f"));
			
			//oConcept.addAnswer(new ConceptAnswer(conceptService.getConceptByUuid("0a335152-343d-440b-ac42-00deffcfd5a5")));
			//oConcept.addAnswer(new ConceptAnswer(conceptService.getConceptByUuid("49f758e1-4fcf-46e3-b6d9-c2a4f5c3c96b")));
			conceptService.saveConcept(oConcept);
		    Context.closeSession();
    	} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	/*HashSet<ConceptAnswer> oAnswerList = new HashSet<ConceptAnswer>();
		oAnswerList.add(new ConceptAnswer(conceptService.getConceptByUuid("0a335152-343d-440b-ac42-00deffcfd5a5")));
		oAnswerList.add(new ConceptAnswer(conceptService.getConceptByUuid("49f758e1-4fcf-46e3-b6d9-c2a4f5c3c96b")));
		System.out.println("Setting answers--------------------------------");*/
		
    	/*List<Patient> patients = Context.getPatientService().getPatients("qw");
	    for (Patient patient : patients) {
	      System.out.println("Found patient with name " + patient.getPersonName() + " and uuid: " + patient.getUuid());
	    }*/
    }
	
	public void properties(){
		Properties properties = new Properties();
    	try {
    		properties.load(new FileInputStream(APP_DATA+File.separator+"omrsapiworker.properties"));
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    	
    	for(String key : properties.stringPropertyNames()) {
    		String value = properties.getProperty(key);
    		System.out.println(key + " => " + value);
		}
	}
}
