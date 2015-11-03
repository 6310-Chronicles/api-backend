package com.cs6310.backend.modelObjects;

public class AddressObject {
	
    private int id;
	private String plotNumber;
	private String postalAddress;
	private String zip;
	private String town;
	private String city;
	private String country;
	private String county;
	private String subCounty;
	private String constituency;
	private String ward;
	private String zone;
	private boolean asalArea;
	private String residence;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getPlotNumber() {
		return plotNumber;
	}
	
	public void setPlotNumber(String plotNumber) {
		this.plotNumber = plotNumber;
	}

	public String getPostalAddress() {
		return postalAddress;
	}
	
	public void setPostalAddress(String postalAddress) {
		this.postalAddress = postalAddress;
	}
	
	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}
	
	public String getTown() {
		return town;
	}
	
	public void setTown(String town) {
		this.town = town;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	
	public String getCounty() {
		return county;
	}
	
	public void setCounty(String county) {
		this.county = county;
	}

	public String getSubCounty() {
		return subCounty;
	}

	public void setSubCounty(String subCounty) {
		this.subCounty = subCounty;
	}
	
	public String getConstituency() {
		return constituency;
	}
	
	public void setConstituency(String constituency) {
		this.constituency = constituency;
	}

	public String getWard() {
		return ward;
	}
	
	public void setWard(String ward) {
		this.ward = ward;
	}
	
	public String getZone() {
		return zone;
	}
	
	public void setZone(String zone) {
		this.zone = zone;
	}

	public void setAsalArea(boolean asalArea) {
		this.asalArea = asalArea;
	}
	
	public String getResidence() {
		return residence;
	}
	
	public void setResidence(String residence) {
		this.residence = residence;
	}

}
