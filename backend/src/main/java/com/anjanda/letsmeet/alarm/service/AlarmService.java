package com.anjanda.letsmeet.alarm.service;

import java.util.List;

import com.anjanda.letsmeet.repository.dto.Alarm;

public interface AlarmService {

	/* C :: 알람 1줄 생성 */
	int createAlarm(Alarm alarm) throws Exception;

	/* R :: 사용자의 알람 전체 조회 */
	List<Alarm> reviewAlarm(int uNo) throws Exception;

	/* D :: 사용자의 알람 삭제 */
	boolean deleteAlarm(Alarm alarm) throws Exception;

}