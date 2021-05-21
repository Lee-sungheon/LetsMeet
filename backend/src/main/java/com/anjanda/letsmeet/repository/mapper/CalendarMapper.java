package com.anjanda.letsmeet.repository.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.anjanda.letsmeet.repository.dto.Calendar;

@Mapper
public interface CalendarMapper {
	
	/* C :: 개인 일정 추가 */
	public int insertCalendar(Calendar calendar) throws Exception;

	/* R :: 개인 일정 조회 */
	public List<Calendar> selectCalendar(int cUNo) throws Exception;
	
	/* D :: 개인 일정 삭제 */
	public int deleteCalendar(Calendar calendar) throws Exception;
}
