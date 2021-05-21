package com.anjanda.letsmeet.repository.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.anjanda.letsmeet.repository.dto.MeetingRoomUser;
import com.anjanda.letsmeet.repository.dto.MeetingRoomUserInfo;

/**
 * 
 * @Date : 2021. 2. 4.
 * @Team : AnJanDa
 * @author : 김지현
 * @deploy : 김동빈
 * @Project : 레쓰밋 :: backend
 * @Function : MeetingRoomUser 관련 매퍼 인터페이스 클래스
 * @Description : MeetingRoomUserService 및 meetingroomusermapper.xml 참고
 * 
 */

@Mapper
public interface MeetingRoomUserMapper {
	/* C :: 약속방 내 멤버 추가 (일단 모두가 가능하도록..) */
	int insertMeetingRoomUser(MeetingRoomUser meetingRoomUser) throws Exception;

	/* R :: 약속방 내 멤버 조회 */
	List<MeetingRoomUserInfo> selectMyMeetingRoomUser(int mrNo);

	/* U :: 약속방에 속한 본인의 위치와 일정 설정 */
	int updateMeetingRoomUserSet(MeetingRoomUser meetingRoomUser);

	/* D :: 약속방에서 나가기 */
	int deleteMeetingRoomUser(MeetingRoomUser meetingRoomUser);

	/* D :: 약속방삭제 */
	int deleteMeetingRoomUserAll(int mrNo);


}
