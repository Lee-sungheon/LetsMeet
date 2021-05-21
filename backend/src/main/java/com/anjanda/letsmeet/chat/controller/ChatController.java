package com.anjanda.letsmeet.chat.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anjanda.letsmeet.chat.service.MeetingRoomChatService;
import com.anjanda.letsmeet.repository.dto.MeetingRoomChat;

/**
 * 
 * @Date : 2021. 2. 5.
 * @Team : AnJanDa
 * @author : 임호빈
 * @deploy : 김동빈 
 * @Project : 레쓰밋 :: backend
 * @Function : 채팅 관련 컨트롤러
 * @Description
 *	- 약속방 상세화면에서 채팅 기능의 Controller부분
 */

@RestController
@RequestMapping("/chat")
public class ChatController {

	/* 챗 서비스 객체 불러오기 */
	@Autowired
	private MeetingRoomChatService chatservice;
	
	/* 해당 약속방의 채팅 불러오기 */
	@GetMapping("/open")
	public List<MeetingRoomChat> selectChatByRno(int mrcMrNo) throws Exception{
		System.out.println("방에 입장"+mrcMrNo);
		return chatservice.selectChatByRno(mrcMrNo);
	}
	
	/* 해당 약속방의 채팅 생성 */
	@GetMapping("/create")
	public int createChat(MeetingRoomChat meetingRoomChat)throws Exception{
		
		return chatservice.createMeetingRoomChat(meetingRoomChat);
	}
	
}
