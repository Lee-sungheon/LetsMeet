package com.anjanda.letsmeet.chat.service;

import java.util.List;

import com.anjanda.letsmeet.repository.dto.MeetingRoomChat;

/**
 * 
 * @Date : 2021. 2. 5.
 * @Team : AnJanDa
 * @author : 임호빈
 * @deploy : 김동빈
 * @Project : 레쓰밋 :: backend
 * @Function : 채팅 관련 서비스 인터페이스 클래스
 * @Description
 *
 */

public interface MeetingRoomChatService {
	
	/* C :: 약속방 채팅 추가 생성 메소드 */
	int createMeetingRoomChat(MeetingRoomChat meetingRoomChat) throws Exception;

	/* R :: 약속방 채팅 조회 */
	List<MeetingRoomChat> selectChatByRno(int mrcMrNo) throws Exception;
}
