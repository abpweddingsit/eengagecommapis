
package com.engg.communication.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.engg.communication.dao.SmsTrackDao;

@Repository
public class SmsTrackDaoImpl implements SmsTrackDao{
	@Autowired
	private JdbcTemplate jdbcTemplate;
	public void insertTrackIds(String bulkId, String id) {
		String query = "INSERT INTO engagement_sms_bulk_track_id (\"campaignId\",\"bulkId\" ) values("+id+", "+bulkId+")";
		jdbcTemplate.update(query);
	}

}
