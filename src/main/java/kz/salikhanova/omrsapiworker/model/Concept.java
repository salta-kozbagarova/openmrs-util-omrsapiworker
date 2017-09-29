package kz.salikhanova.omrsapiworker.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

public class Concept {

	private boolean set;
	
	private Collection<String> answers;
	
	private String descriptions;
	
	private String version;
	
	private ArrayList<ConceptName> names;
	
	private String datatype;
	
	private String conceptClass;
	
	private ArrayList<ConceptMember> setMembers;

	public Concept() {
		super();
		this.answers = new HashSet<String>();
		this.names = new ArrayList<ConceptName>();
		this.setMembers = new ArrayList<ConceptMember>();
	}

	public boolean isSet() {
		return set;
	}

	public void setSet(boolean set) {
		this.set = set;
	}

	public Collection<String> getAnswers() {
		return answers;
	}

	public void setAnswers(Collection<String> answers) {
		this.answers = answers;
	}
	
	public void addAnswer(String conceptAnswer) {
		this.answers.add(conceptAnswer);
	}
	
	public void addAnswers(Collection<String> answers) {
		this.answers.addAll(answers);
	}

	public String getDescriptions() {
		return descriptions;
	}

	public void setDescriptions(String descriptions) {
		this.descriptions = descriptions;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public ArrayList<ConceptName> getNames() {
		return names;
	}

	public void setNames(ArrayList<ConceptName> names) {
		this.names = names;
	}
	
	public void addName(ConceptName conceptName) {
		this.names.add(conceptName);
	}
	
	public void addNames(ArrayList<ConceptName> names) {
		this.names.addAll(names);
	}

	public String getDatatype() {
		return datatype;
	}

	public void setDatatype(String datatype) {
		this.datatype = datatype;
	}

	public String getConceptClass() {
		return conceptClass;
	}

	public void setConceptClass(String conceptClass) {
		this.conceptClass = conceptClass;
	}

	public ArrayList<ConceptMember> getSetMembers() {
		return setMembers;
	}

	public void setSetMembers(ArrayList<ConceptMember> setMembers) {
		this.setMembers = setMembers;
	}
	
	public void addMember(ConceptMember conceptMember) {
		this.setMembers.add(conceptMember);
	}
	
	public void addMembers(ArrayList<ConceptMember> setMembers) {
		this.setMembers.addAll(setMembers);
	}
}
