package com.shaji.javaee.offers.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component("offersDao")
public class OffersDAO {

	private NamedParameterJdbcTemplate jdbc;
	private GeneratedKeyHolder generatedKeyHolder;

	@Autowired
	public void setDataSource(DataSource jdbc) {
		this.jdbc = new NamedParameterJdbcTemplate(jdbc);
	}

	/**
	 * Get all offers
	 * 
	 * @return
	 */
	public List<Offer> getOffers(int offset, int limit, String searchString) {
		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		StringBuffer sql = new StringBuffer();
		sql.append("select * from offers");
		if (!searchString.isEmpty()) {
			paramMap.addValue("searchString", "%" + searchString + "%");
			sql.append(" ");
			sql.append("where name like :searchString or email like :searchString or offerdetails like :searchString");
		}
		if (limit > 0) {
			paramMap.addValue("limit", limit);
			sql.append(" ");
			sql.append("limit :limit");
		}
		if (offset > 0) {
			paramMap.addValue("offset", offset);
			sql.append(" ");
			sql.append("offset :offset");
		}

		return jdbc.query(sql.toString(), paramMap, new RowMapper<Offer>() {

			public Offer mapRow(ResultSet rs, int rowNum) throws SQLException {
				return new Offer(rs.getInt("id"), rs.getString("name"), rs.getString("email"), rs.getString("offerdetails"));
			}

		});
	}

	/**
	 * Get one offer by id
	 * 
	 * @param id
	 * @return
	 */
	public Offer getOfferById(int id) {
		MapSqlParameterSource paramMap = new MapSqlParameterSource("id", id);
		String sql = "select * from offers where id = :id";

		return jdbc.queryForObject(sql, paramMap, new RowMapper<Offer>() {

			public Offer mapRow(ResultSet rs, int rowNum) throws SQLException {
				return new Offer(rs.getInt("id"), rs.getString("name"), rs.getString("email"), rs.getString("offerdetails"));
			}

		});
	}

	/**
	 * Create an offer
	 * 
	 * @param offer
	 * @return id of the created record
	 */
	public int createOffer(Offer offer) {
		BeanPropertySqlParameterSource paramSource = new BeanPropertySqlParameterSource(offer);
		String sql = "insert into offers (name, email, offerdetails) values (:name, :email, :offerDetails)";

		if (generatedKeyHolder == null) {
			generatedKeyHolder = new GeneratedKeyHolder();
		}
		if (jdbc.update(sql, paramSource, generatedKeyHolder) == 1) {
			return generatedKeyHolder.getKey().intValue();
		} else {
			return 0;
		}
	}

	/**
	 * Update an offer
	 * 
	 * @param offer
	 * @return
	 */
	public boolean updateOffer(Offer offer) {
		BeanPropertySqlParameterSource paramSource = new BeanPropertySqlParameterSource(offer);
		String sql = "update offers set name = :name, email = :email, offerdetails = :offerDetails where id = :id";

		return jdbc.update(sql, paramSource) == 1;
	}

	/**
	 * Delete an offer
	 * 
	 * @param id
	 * @param offer
	 * @return
	 */
	public boolean deleteOffer(int id) {
		MapSqlParameterSource paramMap = new MapSqlParameterSource("id", id);
		String sql = "delete from offers where id = :id";

		return jdbc.update(sql, paramMap) == 1;
	}

	/**
	 * Create multiple offers - Transaction
	 * 
	 * @param offers
	 * @return
	 */
	@Transactional
	public int[] createOffers(List<Offer> offers) {
		SqlParameterSource[] batchParams = SqlParameterSourceUtils.createBatch(offers.toArray());
		String sql = "insert into offers (id, name, email, offerdetails) values (:id, :name, :email, :offerDetails)";

		return jdbc.batchUpdate(sql, batchParams);
	}

	/**
	 * Update multiple offers - Transaction
	 * 
	 * @param offers
	 * @return
	 */
	@Transactional
	public int[] updateOffers(List<Offer> offers) {
		SqlParameterSource[] batchParams = SqlParameterSourceUtils.createBatch(offers.toArray());
		String sql = "update offers set name = :name, email = :email, offerdetails = :offerDetails where id = :id";

		return jdbc.batchUpdate(sql, batchParams);
	}

}
