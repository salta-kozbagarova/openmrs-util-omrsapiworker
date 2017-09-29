package kz.salikhanova.omrsapiworker.model;

import java.util.HashSet;
import java.util.Set;

public class Patient {

	private String person;
	
	private Set<PatientIdentifier> identifiers;
	
	public Patient() {
		super();
		this.identifiers = new HashSet<PatientIdentifier>();
	}

	public String getPerson() {
		return person;
	}

	public void setPerson(String person) {
		this.person = person;
	}

	public Set<PatientIdentifier> getIdentifiers() {
		return identifiers;
	}

	public void setIdentifiers(Set<PatientIdentifier> identifiers) {
		this.identifiers = identifiers;
	}
	
	public void addIdentifiers(Set<PatientIdentifier> identifiers) {
		this.identifiers.addAll(identifiers);
	}
	
	public void addIdentifier(PatientIdentifier identifier) {
		this.identifiers.add(identifier);
	}
}
