package kz.salikhanova.omrsapiworker.model;

import java.util.HashSet;
import java.util.Set;

public class Person {

	public static String GENDER_MALE = "M";
	
	public static String GENDER_FEMALE = "F";
	
	private String gender;
	
	private Set<PersonName> names = null;
	
	private Set<PersonAddress> addresses = null;
	
	private Set<PersonAttribute> attributes = null;

	private String birthdate;

	private String birthtime;

	private Boolean birthdateEstimated = false;

	private Boolean deathdateEstimated = false;

	private Boolean dead = false;
	
	private String deathDate;
	
	private Concept causeOfDeath;
	
	private String age;

	public Person() {
		super();
		this.names = new HashSet<PersonName>();
		this.addresses = new HashSet<PersonAddress>();
		this.attributes = new HashSet<PersonAttribute>();
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Set<PersonName> getNames() {
		return names;
	}

	public void setNames(Set<PersonName> names) {
		this.names = names;
	}
	
	public void addNames(Set<PersonName> names) {
		this.names.addAll(names);
	}
	
	public void addName(PersonName name) {
		this.names.add(name);
	}

	public Set<PersonAddress> getAddresses() {
		return addresses;
	}

	public void setAddresses(Set<PersonAddress> addresses) {
		this.addresses = addresses;
	}
	
	public void addAddresses(Set<PersonAddress> addresses) {
		this.addresses.addAll(addresses);
	}
	
	public void addAddress(PersonAddress address) {
		this.addresses.add(address);
	}

	public Set<PersonAttribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(Set<PersonAttribute> attributes) {
		this.attributes = attributes;
	}
	
	public void addAttributes(Set<PersonAttribute> attributes) {
		this.attributes.addAll(attributes);
	}
	
	public void addAttribute(PersonAttribute attribute) {
		this.attributes.add(attribute);
	}

	public String getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}

	public String getBirthtime() {
		return birthtime;
	}

	public void setBirthtime(String birthtime) {
		this.birthtime = birthtime;
	}

	public Boolean getBirthdateEstimated() {
		return birthdateEstimated;
	}

	public void setBirthdateEstimated(Boolean birthdateEstimated) {
		this.birthdateEstimated = birthdateEstimated;
	}

	public Boolean getDeathdateEstimated() {
		return deathdateEstimated;
	}

	public void setDeathdateEstimated(Boolean deathdateEstimated) {
		this.deathdateEstimated = deathdateEstimated;
	}

	public Boolean getDead() {
		return dead;
	}

	public void setDead(Boolean dead) {
		this.dead = dead;
	}

	public String getDeathDate() {
		return deathDate;
	}

	public void setDeathDate(String deathDate) {
		this.deathDate = deathDate;
	}

	public Concept getCauseOfDeath() {
		return causeOfDeath;
	}

	public void setCauseOfDeath(Concept causeOfDeath) {
		this.causeOfDeath = causeOfDeath;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}
}
