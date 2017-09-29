package kz.salikhanova.omrsapiworker.model;

import kz.salikhanova.omrsapiworker.util.IdentifierGenerator;

public class PatientIdentifier {

	public static String TYPE_OPENMRS_ID = "05a29f94-c0ed-11e2-94be-8c13b969e334";
	
	public static String TYPE_OLD_ID_NUM = "8d79403a-c2cc-11de-8d13-0010c6dffd0f";
	
	public static String TYPE_OPENMRS_ID_NUM = "8d793bee-c2cc-11de-8d13-0010c6dffd0f";
	
	public static String LOCATION_REGISTRATION_DESK = "6351fcf4-e311-4a19-90f9-35667d99a8af";
	
	private String identifier;
	
	private String identifierType = TYPE_OPENMRS_ID;
	
	private String location = LOCATION_REGISTRATION_DESK;
	
	private boolean preferred;

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getIdentifierType() {
		return identifierType;
	}

	public void setIdentifierType(String identifierType) {
		this.identifierType = identifierType;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public boolean isPreferred() {
		return preferred;
	}

	public void setPreferred(boolean preferred) {
		this.preferred = preferred;
	}

	public PatientIdentifier() {
		super();
		this.identifier = IdentifierGenerator.generate();
	}
}
