package com.anjanda.letsmeet.repository.mapper;

/**
 * 
 * @Date : 2021. 2. 4.
 * @Team : AnJanDa
 * @author : 임호빈
 * @deploy : 김동빈
 * @Project : 레쓰밋 :: backend
 * @Function : MeetingRoomChat 관련 매퍼 인터페이스 클래스
 * @Description : MeetingRoomChatService 및 meetingroomchatmapper.xml 참고
 * 
 */

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.anjanda.letsmeet.repository.dto.MeetingRoomChat;

@Mapper
public interface MeetingRoomChatMapper {
	
	List<MeetingRoomChat> selectChatByRno(int mrcMrNo) throws Exception;

	int createMeetingRoomChat(MeetingRoomChat meetingRoomChat) throws Exception;

}
