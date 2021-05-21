package com.anjanda.letsmeet.calendar.service;

import java.util.List;

import com.anjanda.letsmeet.repository.dto.Calendar;

public interface CalendarService {

	/* C :: 개인 일정 추가  */
	int insertCalendar(Calendar calendar) throws Exception;

	/* R :: 개인 일정 조회  */
	List<Calendar> selectCalendar(int cUNo) throws Exception;

	/* D :: 개인 일정 삭제  */
	int deleteSelect(Calendar calendar) throws Exception;

}