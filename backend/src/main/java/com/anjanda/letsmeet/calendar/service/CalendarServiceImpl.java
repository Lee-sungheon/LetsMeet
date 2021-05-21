package com.anjanda.letsmeet.calendar.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anjanda.letsmeet.repository.dto.Calendar;
import com.anjanda.letsmeet.repository.mapper.CalendarMapper;

@Service
public class CalendarServiceImpl implements CalendarService {
	
	@Autowired
	private CalendarMapper calendarMapper;
	
	/* C :: 개인 일정 추가  */
	@Override
	public int insertCalendar(Calendar calendar) throws Exception {
		return calendarMapper.insertCalendar(calendar);
	}
	
	/* R :: 개인 일정 조회  */
	@Override
	public List<Calendar> selectCalendar(int cUNo) throws Exception {
		return calendarMapper.selectCalendar(cUNo);
	}
	
	/* D :: 개인 일정 삭제  */
	@Override
	public int deleteSelect(Calendar calendar) throws Exception {
		return calendarMapper.deleteCalendar(calendar);
	}
}
