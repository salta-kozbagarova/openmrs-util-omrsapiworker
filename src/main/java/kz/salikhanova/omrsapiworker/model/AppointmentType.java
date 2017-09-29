package kz.salikhanova.omrsapiworker.model;

public class AppointmentType {

	private String duration;
	
	private String name;
	
	private String description;

	public AppointmentType() {
		super();
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
