package com.shaji.javaee.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "offers")
public class Offer {
	@Id
	@GeneratedValue
	@Column(name = "id")
	private int id;

	@ManyToOne
	@JoinColumn(name = "username")
	private User user;

	@Size(min = 5, max = 250, message = "Offer must be between 5 and 250 chars")
	@Column(name = "offerdetails")
	private String offerDetails;

	public Offer() {
		// Added to allow setting current user information later when web forms,
		// Rest APIs sending offer details with our user info
		this.user = new User();
	}

	public Offer(int id) {
		this.id = id;
	}

	public Offer(int id, User user, String offerDetails) {
		this.id = id;
		this.user = user;
		this.offerDetails = offerDetails;
	}

	public Offer(User user, String offerDetails) {
		this.user = user;
		this.offerDetails = offerDetails;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getOfferDetails() {
		return offerDetails;
	}

	public void setOfferDetails(String offerDetails) {
		this.offerDetails = offerDetails;
	}

}
