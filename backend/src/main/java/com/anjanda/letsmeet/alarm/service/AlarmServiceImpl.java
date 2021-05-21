package com.anjanda.letsmeet.alarm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anjanda.letsmeet.repository.dto.Alarm;
import com.anjanda.letsmeet.repository.mapper.AlarmMapper;

@Service
public class AlarmServiceImpl implements AlarmService {
	
	@Autowired
	private AlarmMapper alarmMapper;
	
	/* C :: 알람 1줄 생성 */
	@Override
	public int createAlarm(Alarm alarm) throws Exception {
		return alarmMapper.insertAlarm(alarm);
	}
	
	/* R :: 사용자의 알람 전체 조회 */
	@Override
	public List<Alarm> reviewAlarm(int uNo) throws Exception {
		return alarmMapper.selectAlarm(uNo);
	}
	
	/* D :: 사용자의 알람 삭제 */
	@Override
	public boolean deleteAlarm(Alarm alarm) throws Exception {
		return alarmMapper.deleteAlarm(alarm);
	}
}
