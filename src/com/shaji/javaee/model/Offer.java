package com.shaji.javaee.model;

import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "offers")
public class Offer {
	@Id
	private String id;

	@DBRef
	private User user;

	@Size(min = 5, max = 250, message = "Offer must be between 5 and 250 chars")
	private String offerDetails;

	public Offer() {
		// Added to allow setting current user information later when web forms,
		// Rest APIs sending offer details with our user info
		this.user = new User();
	}

	public Offer(String id) {
		this.id = id;
	}

	public Offer(String id, User user, String offerDetails) {
		this.id = id;
		this.user = user;
		this.offerDetails = offerDetails;
	}

	@PersistenceConstructor
	public Offer(User user, String offerDetails) {
		this.user = user;
		this.offerDetails = offerDetails;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
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
