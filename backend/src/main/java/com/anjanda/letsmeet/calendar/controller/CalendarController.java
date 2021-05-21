package com.anjanda.letsmeet.calendar.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anjanda.letsmeet.calendar.service.CalendarService;
import com.anjanda.letsmeet.repository.dto.Calendar;

@RestController
@RequestMapping("/calendar")
public class CalendarController {
	
	@Autowired
	private CalendarService calendarService;
	
	/* C :: 개인 일정 추가  */
	@PostMapping("/add")
	public ResponseEntity<String> calendarAdd(Calendar calendar) throws Exception {
		System.out.println("들어갈 정보 : " + calendar.toString());
		if(calendarService.insertCalendar(calendar) > 0)
			return new ResponseEntity<String>("개인 일정 추가 성공", HttpStatus.OK);
		return new ResponseEntity<String>("개인 일정 추가 실패", HttpStatus.NO_CONTENT);
	}
	
	/* R :: 개인 일정 조회  */
	@GetMapping("/get")
	public ResponseEntity<List<Calendar>> calendarReview(int cUNo) throws Exception {
		System.out.println("조회할 사람 번호 : " + cUNo);
		return new ResponseEntity<List<Calendar>>(calendarService.selectCalendar(cUNo), HttpStatus.OK);
	}
	
	/* D :: 개인 일정 삭제  */
	@DeleteMapping("/delete")
	public ResponseEntity<String> calendarDelete(Calendar calendar) throws Exception {
		System.out.println("삭제할 정보 : " + calendar.toString());
		if(calendarService.deleteSelect(calendar) > 0)
			return new ResponseEntity<String>("일정 삭제 성공", HttpStatus.OK);
		return new ResponseEntity<String>("일정 삭제 실패", HttpStatus.NO_CONTENT);
	}

}
