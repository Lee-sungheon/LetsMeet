package com.anjanda.letsmeet.chat.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anjanda.letsmeet.repository.dto.MeetingRoomChat;
import com.anjanda.letsmeet.repository.mapper.MeetingRoomChatMapper;

/**
 * 
 * @Date : 2021. 2. 5.
 * @Team : AnJanDa
 * @author : 임호빈
 * @deploy : 김동빈
 * @Project : 레쓰밋 :: backend
 * @Function : 채팅 관련 서비스 클래스
 * @Description
 *
 */

@Service
public class MeetingRoomChatServiceImpl implements MeetingRoomChatService {

	/* 매퍼 클래스 객체 불러오기 */
	@Autowired
	private MeetingRoomChatMapper mapper;
	
	/* C :: 약속방 채팅 추가 생성 메소드 */
	@Override
	public int createMeetingRoomChat(MeetingRoomChat meetingRoomChat) throws Exception {
		return mapper.createMeetingRoomChat(meetingRoomChat);
	}

	/* R :: 약속방 채팅 조회 */
	@Override
	public List<MeetingRoomChat> selectChatByRno(int mrcMrNo) throws Exception {
		return mapper.selectChatByRno(mrcMrNo);
	}
}
