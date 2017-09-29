package kz.salikhanova.omrsapiworker.model;

public class ConceptName {
	
	public static String LOCALE_EN = "en";
	
	public static String LOCALE_RU = "ru";

	private String name;
	
	private String locale = LOCALE_RU;
	
	private String conceptNameType = "FULLY_SPECIFIED";
	
	public ConceptName(String name) {
		super();
		this.name = name;
	}

	public ConceptName(String name, String locale, String conceptNameType) {
		super();
		this.name = name;
		this.locale = locale;
		this.conceptNameType = conceptNameType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public String getConceptNameType() {
		return conceptNameType;
	}

	public void setConceptNameType(String conceptNameType) {
		this.conceptNameType = conceptNameType;
	}
	
	
}
