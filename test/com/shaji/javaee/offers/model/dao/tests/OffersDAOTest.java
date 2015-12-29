package com.shaji.javaee.offers.model.dao.tests;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.shaji.javaee.offers.model.dao.OffersDAO;
import com.shaji.javaee.offers.model.form.Offer;

import junit.framework.TestCase;

@ActiveProfiles("test")
@ContextConfiguration(locations = { "classpath:com/shaji/javaee/offers/test/config/test-context.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class OffersDAOTest extends TestCase {

	@Autowired
	private OffersDAO offersDao;

	@Test
	public void testCreateAndFetchOffers() {
		Offer offer1 = new Offer("John Test", "john@test.com", "I can fix software");
		Offer offer2 = new Offer("Ted Test", "ted@test.com", "I can fix hardware");
		Offer offer3 = new Offer("Ash Test", "ash@test.com", "I can fix house");

		// Create 3 offers
		int id1 = offersDao.createOffer(offer1);
		int id2 = offersDao.createOffer(offer2);
		int id3 = offersDao.createOffer(offer3);

		assertNotNull("Create offer should return the id of the record", id1);
		assertNotNull("Create offer should return the id of the record", id2);
		assertNotNull("Create offer should return the id of the record", id3);

		// Get all
		List<Offer> retOffers = offersDao.getOffers(0, 0, "");
		assertEquals("Get all offers should return 3 offers", 3, retOffers.size());

		// Get with limit
		retOffers = offersDao.getOffers(0, 2, "");
		assertEquals("Get with limit 2 should return 2 offers", 2, retOffers.size());

		// Get with offset and limit
		retOffers = offersDao.getOffers(1, 2, "");
		assertEquals("Get with offset 1 and limit 2 should return 2 offers", 2, retOffers.size());

		// Get with search
		retOffers = offersDao.getOffers(0, 0, "house");
		assertEquals("Search for house shoud return 1 offer", 1, retOffers.size());

		// Get with id
		Offer retOffer = offersDao.getOfferById(id1);
		assertEquals("Get with id shoud return 1 offer with that id", id1, retOffer.getId());

		// clear
		offersDao.deleteOffer(id1);
		offersDao.deleteOffer(id2);
		offersDao.deleteOffer(id3);
	}

	@Test
	public void testUpdateOffer() {
		Offer offer1 = new Offer("John Test", "john@test.com", "I can fix software");

		// Create 1 offer
		int id1 = offersDao.createOffer(offer1);

		// Update name
		Offer offer1Updated = new Offer(id1, "New Name", "john@test.com", "I can fix software");
		offersDao.updateOffer(offer1Updated);

		// Get again
		Offer retOffer = offersDao.getOfferById(id1);
		assertEquals("Name should have updated to new name", "New Name", retOffer.getName());

		// clear
		offersDao.deleteOffer(id1);
	}

	@Test
	public void testDeleteOffer() {
		Offer offer1 = new Offer("John Test", "john@test.com", "I can fix software");

		// Create 1 offer
		int id1 = offersDao.createOffer(offer1);

		// Delete offer
		offersDao.deleteOffer(id1);

		// Get
		Offer retOffer = offersDao.getOfferById(id1);
		assertNull("Offer should be deleted", retOffer);
	}

}
