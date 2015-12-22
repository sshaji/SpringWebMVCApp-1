package com.shaji.javaee.offers.model;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.refEq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.test.util.ReflectionTestUtils;

import junit.framework.TestCase;

public class OffersDAOTest extends TestCase {

	private OffersDAO offersDao;

	@Mock
	private NamedParameterJdbcTemplate jdbc;

	@Mock
	private GeneratedKeyHolder generatedKeyHolder;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		offersDao = new OffersDAO();
		ReflectionTestUtils.setField(offersDao, "jdbc", jdbc);
		ReflectionTestUtils.setField(offersDao, "generatedKeyHolder", generatedKeyHolder);
	}

	@Test
	public void testGetOffersAll() {
		List<Offer> offers = new ArrayList<Offer>();
		offers.add(new Offer(100, "John", "john@test.com", "I can fix kitchen"));
		offers.add(new Offer(101, "Ted", "ted@test.com", "I can fix roof"));

		when(jdbc.query(any(String.class), any(SqlParameterSource.class), any(RowMapper.class))).thenReturn(offers);

		List<Offer> retOffers = offersDao.getOffers(0, 2, "");

		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("limit", 2);
		String sql = "select * from offers limit :limit";
		verify(jdbc, times(1)).query(eq(sql), refEq(paramMap), any(RowMapper.class));
		assertEquals("Result -> count not matching", retOffers.size(), 2);
	}

	@Test
	public void testGetOffersWithOffset() {
		List<Offer> offers = new ArrayList<Offer>();
		offers.add(new Offer(100, "John", "john@test.com", "I can fix kitchen"));
		offers.add(new Offer(101, "Ted", "ted@test.com", "I can fix roof"));

		when(jdbc.query(any(String.class), any(SqlParameterSource.class), any(RowMapper.class))).thenReturn(offers);

		List<Offer> retOffers = offersDao.getOffers(1, 2, "");

		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("offset", 1);
		paramMap.addValue("limit", 2);
		String sql = "select * from offers limit :limit offset :offset";
		verify(jdbc, times(1)).query(eq(sql), refEq(paramMap), any(RowMapper.class));
		assertEquals("Result -> count not matching", retOffers.size(), 2);
	}

	@Test
	public void testGetOffersWithSearch() {
		List<Offer> offers = new ArrayList<Offer>();
		offers.add(new Offer(100, "John", "john@test.com", "I can fix kitchen"));
		offers.add(new Offer(101, "Ted", "ted@test.com", "I can fix roof"));

		when(jdbc.query(any(String.class), any(SqlParameterSource.class), any(RowMapper.class))).thenReturn(offers);

		List<Offer> retOffers = offersDao.getOffers(0, 2, "test");

		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("limit", 2);
		paramMap.addValue("searchString", "%test%");
		String sql = "select * from offers where name like :searchString or email like :searchString or offerdetails like :searchString limit :limit";
		verify(jdbc, times(1)).query(eq(sql), refEq(paramMap), any(RowMapper.class));
		assertEquals("Result -> count not matching", retOffers.size(), 2);
	}

	@Test
	public void testGetOfferById() {
		Offer offer = new Offer(100, "John", "john@test.com", "I can fix kitchen");

		when(jdbc.queryForObject(any(String.class), any(SqlParameterSource.class), any(RowMapper.class))).thenReturn(offer);

		Offer retOffer = offersDao.getOfferById(100);

		MapSqlParameterSource paramMap = new MapSqlParameterSource("id", 100);
		String sql = "select * from offers where id = :id";
		verify(jdbc, times(1)).queryForObject(eq(sql), refEq(paramMap), any(RowMapper.class));
		assertEquals("Result -> name not matching", retOffer.getName(), "John");
	}

	@Test
	public void testCreateOffer() {
		Offer offer = new Offer("John", "john@test.com", "I can fix kitchen");

		when(jdbc.update(any(String.class), any(SqlParameterSource.class), any(GeneratedKeyHolder.class))).thenReturn(1);
		when(generatedKeyHolder.getKey()).thenReturn(100);

		int retId = offersDao.createOffer(offer);

		assertEquals("Result -> id not matching", retId, 100);
	}

	@Test
	public void testCreateOfferFail() {
		Offer offer = new Offer("John", "john@test.com", "I can fix kitchen");

		when(jdbc.update(any(String.class), any(SqlParameterSource.class), any(GeneratedKeyHolder.class))).thenReturn(0);
		when(generatedKeyHolder.getKey()).thenReturn(100);

		int retId = offersDao.createOffer(offer);

		assertEquals("Result -> id not matching", retId, 0);
	}

	@Test
	public void testUpdateOffer() {
		Offer offer = new Offer(100, "John", "john@test.com", "I can fix kitchen");

		when(jdbc.update(any(String.class), any(SqlParameterSource.class))).thenReturn(1);

		boolean ret = offersDao.updateOffer(offer);

		assertTrue("Result -> id not matching", ret);
	}

	@Test
	public void testUpdateOfferFail() {
		Offer offer = new Offer(100, "John", "john@test.com", "I can fix kitchen");

		when(jdbc.update(any(String.class), any(SqlParameterSource.class))).thenReturn(0);

		boolean ret = offersDao.updateOffer(offer);

		assertFalse("Result -> id not matching", ret);
	}

	@Test
	public void testDeleteOffer() {
		when(jdbc.update(any(String.class), any(SqlParameterSource.class))).thenReturn(1);

		boolean ret = offersDao.deleteOffer(100);

		assertTrue("Result -> id not matching", ret);
	}

	@Test
	public void testDeleteOfferFail() {
		when(jdbc.update(any(String.class), any(SqlParameterSource.class))).thenReturn(0);

		boolean ret = offersDao.deleteOffer(100);

		assertFalse("Result -> id not matching", ret);
	}

}
