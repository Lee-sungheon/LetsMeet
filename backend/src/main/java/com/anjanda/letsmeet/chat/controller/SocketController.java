package com.anjanda.letsmeet.chat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.anjanda.letsmeet.chat.service.MeetingRoomChatService;
import com.anjanda.letsmeet.repository.dto.MeetingRoomChat;

/**
 * 
 * @Date : 2021. 2. 5.
 * @Team : AnJanDa
 * @author : 임호빈
 * @deploy : 김동빈
 * @Project : 레쓰밋 :: backend
 * @Function : 채팅 기능 관련 소켓 클래스
 * @Description
 *	- 채팅을 위해선, 소켓 클래스 필요
 */

@Controller
public class SocketController {
	@Autowired
	private MeetingRoomChatService chatservice;

    /* '/receive'를 메시지를 받을 endpoint로 설정 */
    @MessageMapping("/receive")

    /* '/send'로 메시지를 반환 */
    @SendTo("/send")
    
    //SocketHandler는 1) /receive에서 메시지를 받고, /send로 메시지를 보내줍니다.
    // 정의한 SocketVO를 1) 인자값, 2) 반환값으로 사용합니다.
    public MeetingRoomChat SocketHandler(MeetingRoomChat meetingRoomChat) throws Exception {
        // vo에서 getter로 userName을 가져옵니다.
//    	System.out.println(session.getAttributes().toString());
        int userNo = meetingRoomChat.getMrcUNo();
        // vo에서 setter로 content를 가져옵니다.
        String content = meetingRoomChat.getMrcContent();
        int mrNo=meetingRoomChat.getMrcMrNo();
        //        int uNo=(int) session.getAttribute("uNo");
        // 생성자로 반환값을 생성합니다.
        MeetingRoomChat result = new MeetingRoomChat();
        
        System.out.println(userNo);
        System.out.println(content);
        System.out.println(mrNo+"방번호");
        
        result.setMrcContent(content);
        result.setMrcUNo(userNo);
        result.setMrcMrNo(mrNo);
        
        chatservice.createMeetingRoomChat(result);
        // 반환
        return result;
    }
}
