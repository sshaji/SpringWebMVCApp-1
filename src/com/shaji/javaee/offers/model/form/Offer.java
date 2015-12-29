package com.shaji.javaee.offers.model.form;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "offers")
public class Offer {
	@Id
	@GeneratedValue
	@Column(name = "id")
	private int id;

	@Size(min = 5, max = 60, message = "Name must be between 5 and 60 chars")
	@Column(name = "name")
	private String name;

	@Size(min = 5, max = 60, message = "Email must be between 5 and 60 chars")
	@Column(name = "email")
	private String email;

	@Size(min = 5, max = 250, message = "Offer must be between 5 and 250 chars")
	@Column(name = "offerdetails")
	private String offerDetails;

	public Offer() {

	}

	public Offer(int id) {
		this.id = id;
	}

	public Offer(String name, String email, String offerDetails) {
		this.name = name;
		this.email = email;
		this.offerDetails = offerDetails;
	}

	public Offer(int id, String name, String email, String offerDetails) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.offerDetails = offerDetails;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getOfferDetails() {
		return offerDetails;
	}

	public void setOfferDetails(String offerDetails) {
		this.offerDetails = offerDetails;
	}

	@Override
	public String toString() {
		return "Offer [id=" + id + ", name=" + name + ", email=" + email + ", offerDetails=" + offerDetails + "]";
	}

}
