package kz.salikhanova.omrsapiworker.model;

import kz.salikhanova.omrsapiworker.util.PersonAttributeTypeUuid;

public class PersonAttributeType {

	private String uuid;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public PersonAttributeType(String uuid) {
		super();
		this.uuid = uuid;
	}
	
	public static PersonAttributeType getInstanceOfIIN(){
		return new PersonAttributeType(PersonAttributeTypeUuid.getIin());
	}
	
	public static PersonAttributeType getInstanceOfTelephoneNumber(){
		return new PersonAttributeType(PersonAttributeTypeUuid.getTelephoneNumber());
	}
	
	public static PersonAttributeType getInstanceOfMobilePhoneNumber(){
		return new PersonAttributeType(PersonAttributeTypeUuid.getMobilePhoneNumber());
	}
}
