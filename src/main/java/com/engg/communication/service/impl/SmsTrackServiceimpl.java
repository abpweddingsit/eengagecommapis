package com.engg.communication.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.engg.communication.dao.SmsTrackDao;
import com.engg.communication.service.SmsTrackService;

@Service
public class SmsTrackServiceimpl implements SmsTrackService{
	@Autowired
	SmsTrackDao smsTrackDao;
	public void insertTrackIds(String bulkId, String id) {
		smsTrackDao.insertTrackIds(bulkId, id);
	}
}
