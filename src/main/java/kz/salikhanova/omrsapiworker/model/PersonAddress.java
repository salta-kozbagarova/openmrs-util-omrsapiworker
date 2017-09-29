package kz.salikhanova.omrsapiworker.model;

import java.util.Date;

public class PersonAddress {

	private String address1;

	private String address2;

	private String address3;

	private String address4;

	private String address5;

	private String address6;

	private String address7;

	private String address8;

	private String address9;

	private String address10;

	private String address11;

	private String address12;

	private String address13;

	private String address14;

	private String address15;

	private String cityVillage;

	private String countyDistrict;

	private String stateProvince;

	private String country;

	private String postalCode;

	private String latitude;

	private String longitude;
	
	private String startDate;
	
	private String endDate;
	
	private boolean preferred;

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getAddress3() {
		return address3;
	}

	public void setAddress3(String address3) {
		this.address3 = address3;
	}

	public String getAddress4() {
		return address4;
	}

	public void setAddress4(String address4) {
		this.address4 = address4;
	}

	public String getAddress5() {
		return address5;
	}

	public void setAddress5(String address5) {
		this.address5 = address5;
	}

	public String getAddress6() {
		return address6;
	}

	public void setAddress6(String address6) {
		this.address6 = address6;
	}

	public String getAddress7() {
		return address7;
	}

	public void setAddress7(String address7) {
		this.address7 = address7;
	}

	public String getAddress8() {
		return address8;
	}

	public void setAddress8(String address8) {
		this.address8 = address8;
	}

	public String getAddress9() {
		return address9;
	}

	public void setAddress9(String address9) {
		this.address9 = address9;
	}

	public String getAddress10() {
		return address10;
	}

	public void setAddress10(String address10) {
		this.address10 = address10;
	}

	public String getAddress11() {
		return address11;
	}

	public void setAddress11(String address11) {
		this.address11 = address11;
	}

	public String getAddress12() {
		return address12;
	}

	public void setAddress12(String address12) {
		this.address12 = address12;
	}

	public String getAddress13() {
		return address13;
	}

	public void setAddress13(String address13) {
		this.address13 = address13;
	}

	public String getAddress14() {
		return address14;
	}

	public void setAddress14(String address14) {
		this.address14 = address14;
	}

	public String getAddress15() {
		return address15;
	}

	public void setAddress15(String address15) {
		this.address15 = address15;
	}

	public String getCityVillage() {
		return cityVillage;
	}

	public void setCityVillage(String cityVillage) {
		this.cityVillage = cityVillage;
	}

	public String getCountyDistrict() {
		return countyDistrict;
	}

	public void setCountyDistrict(String countyDistrict) {
		this.countyDistrict = countyDistrict;
	}

	public String getStateProvince() {
		return stateProvince;
	}

	public void setStateProvince(String stateProvince) {
		this.stateProvince = stateProvince;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
	public boolean isPreferred() {
		return preferred;
	}

	public void setPreferred(boolean preferred) {
		this.preferred = preferred;
	}

	public PersonAddress(String address1) {
		super();
		this.address1 = address1;
	}
}
