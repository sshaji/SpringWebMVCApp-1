package com.shaji.javaee.repository.tests;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.shaji.javaee.model.Offer;
import com.shaji.javaee.model.User;
import com.shaji.javaee.repository.OfferRepository;
import com.shaji.javaee.repository.UserRepository;

import junit.framework.TestCase;

@ActiveProfiles("test")
@ContextConfiguration(locations = { "classpath:com/shaji/javaee/test/config/test-context.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class OfferRepositoryTest extends TestCase {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private OfferRepository offersDao;

	private User user1 = new User("john", "john", 1, "John", "Test", "john@test.com");
	private User user2 = new User("ted", "ted", 1, "Ted", "Test", "ted@test.com");

	@Before
	@Override
	public void setUp() throws Exception {
		userRepository.create(user1);
		userRepository.create(user2);
	}

	@After
	@Override
	public void tearDown() throws Exception {
		userRepository.delete(user1.getUserName());
		userRepository.delete(user2.getUserName());
	}

	@Test
	public void testCreateAndFetchOffers() {
		Offer offer1 = new Offer(user1, "I can fix software");
		Offer offer2 = new Offer(user2, "I can fix hardware");
		Offer offer3 = new Offer(user2, "I can fix house");

		// Create 3 offers
		offer1 = offersDao.create(offer1);
		offer2 = offersDao.create(offer2);
		offer3 = offersDao.create(offer3);

		assertNotNull("Create offer should return the id of the record", offer1.getId());
		assertNotNull("Create offer should return the id of the record", offer2.getId());
		assertNotNull("Create offer should return the id of the record", offer3.getId());

		// Get all
		List<Offer> retOffers = offersDao.get(0, 0, "");
		assertEquals("Get all offers should return 3 offers", 3, retOffers.size());

		// Get with limit
		retOffers = offersDao.get(0, 2, "");
		assertEquals("Get with limit 2 should return 2 offers", 2, retOffers.size());

		// Get with offset and limit
		retOffers = offersDao.get(1, 2, "");
		assertEquals("Get with offset 1 and limit 2 should return 2 offers", 2, retOffers.size());

		// Get with search
		retOffers = offersDao.get(0, 0, "house");
		assertEquals("Search for house shoud return 1 offer", 1, retOffers.size());

		// Get with id
		Offer retOffer = offersDao.getById(offer1.getId());
		assertEquals("Get with id shoud return 1 offer with that id", offer1.getId(), retOffer.getId());

		// clear
		offersDao.delete(offer1.getId());
		offersDao.delete(offer2.getId());
		offersDao.delete(offer3.getId());
	}

	@Test
	public void testUpdateOffer() {
		Offer offer1 = new Offer(user1, "I can fix software");

		// Create 1 offer
		offer1 = offersDao.create(offer1);

		// Update name
		Offer offer1Updated = new Offer(offer1.getId(), user1, "I can fix hardware");
		offersDao.update(offer1Updated);

		// Get again
		Offer retOffer = offersDao.getById(offer1.getId());
		assertEquals("Name should have updated", "I can fix hardware", retOffer.getOfferDetails());

		// clear
		offersDao.delete(offer1.getId());
	}

	@Test
	public void testDeleteOffer() {
		Offer offer1 = new Offer(user1, "I can fix software");

		// Create 1 offer
		offer1 = offersDao.create(offer1);

		// Delete offer
		offersDao.delete(offer1.getId());

		// Get
		Offer retOffer = offersDao.getById(offer1.getId());
		assertNull("Offer should be deleted", retOffer);
	}

}
