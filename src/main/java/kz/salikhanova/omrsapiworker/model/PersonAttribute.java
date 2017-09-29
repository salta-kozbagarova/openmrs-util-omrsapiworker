package kz.salikhanova.omrsapiworker.model;

public class PersonAttribute {

	private String value;
	
	private PersonAttributeType attributeType;

	public PersonAttribute(String value, PersonAttributeType attributeType) {
		super();
		this.value = value;
		this.attributeType = attributeType;
	}
}
