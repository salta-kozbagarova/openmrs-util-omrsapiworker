package kz.salikhanova.omrsapiworker.model;

public class PersonName {

	private String givenName;
	
	private String familyName;
	
	private String middleName;
	
	private boolean preferred;

	public String getGivenName() {
		return givenName;
	}

	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}

	public String getFamilyName() {
		return familyName;
	}

	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	
	public boolean isPreferred() {
		return preferred;
	}

	public void setPreferred(boolean preferred) {
		this.preferred = preferred;
	}

	public PersonName(String givenName, String familyName, String middleName) {
		super();
		this.givenName = givenName;
		this.familyName = familyName;
		this.middleName = middleName;
	}
}
