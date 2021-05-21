package com.anjanda.letsmeet.meetingroomuser.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anjanda.letsmeet.meetingroom.service.MeetingRoomService;
import com.anjanda.letsmeet.meetingroomuser.service.MeetingRoomUserService;
import com.anjanda.letsmeet.repository.dto.MeetingRoomUser;
import com.anjanda.letsmeet.repository.dto.MeetingRoomUserInfo;
import com.anjanda.letsmeet.user.service.UserService;

/**
 * 
 * @Date : 2021. 2. 4.
 * @Team : AnJanDa
 * @author : 김지현
 * @deploy : 김동빈
 * @Project : 레쓰밋 :: backend
 * @Function : 약속방에 구성된 멤버 관련 컨트롤러 클래스
 * @Description
 *	- 추가 수정 필요함
 */
@RestController
@RequestMapping("/meetingRoomUser")
public class MeetingRoomUserController {
	
	/* 약속방에 부를 유저를 위한 객체 불러오기 */
	@Autowired
	private UserService userService;
	
	/* 약속방 객체 불러오기 (추후 계산을 위한?) */
	@Autowired
	private MeetingRoomService meetingRoomService;
	
	/* 약속방 유저 객체 불러오기 */
	@Autowired
	private MeetingRoomUserService meetingRoomUserService;
	
	/* C :: 약속방 내 멤버 추가 (일단 모두가 가능하도록..) */
	@PostMapping("/adduser")
	public ResponseEntity<String> createMeetingRoomUser(@RequestBody MeetingRoomUser user) throws Exception {
		if(meetingRoomUserService.createMeetingRoomUser(user) > 0) {
			meetingRoomService.updateMeetingRoomCntUp(user.getMruMrNo());
			return new ResponseEntity<String>("약속방 내 멤버 추가 성공", HttpStatus.OK);
		}
		return new ResponseEntity<String>("약속방 내 멤버 추가 실패", HttpStatus.NO_CONTENT);
	}
	
	/* R :: 약속방 내 멤버 조회 */
	@GetMapping("/userInfo")
	public ResponseEntity<List<MeetingRoomUserInfo>> reviewMyMeetingRoomUser(int mrNo) throws Exception {
		
//		System.out.println(meetingRoomUserService.reviewMyMeetingRoomUser(mrNo).get(0).toString());
		return new ResponseEntity<List<MeetingRoomUserInfo>>(meetingRoomUserService.reviewMyMeetingRoomUser(mrNo), HttpStatus.OK);
	}
	
	/* U :: 약속방에 속한 본인의 위치와 시간 설정 */
	@PutMapping("/set")
	public ResponseEntity<String> updateMeetingRoomUserSet(@RequestBody MeetingRoomUser meetingRoomUser){
		if(meetingRoomUserService.updateMeetingRoomUserSet(meetingRoomUser)) {
			return new ResponseEntity<String>("약속방의 본인 위치와 시간 성공", HttpStatus.OK);
		}
		return new ResponseEntity<String>("약속방의 본인 위치와 시간 설정 실패", HttpStatus.NO_CONTENT);
	}
	
   /*D :: 약속방에서 나가기*/
   @DeleteMapping("/delete")
   public ResponseEntity<String> deleteMeetingRoomUser(int mrNo, int uNo) throws Exception {
      System.out.println("미팅룸삭제");
      System.out.println(mrNo+" "+uNo);
      MeetingRoomUser meetingRoomUser=new MeetingRoomUser();
      meetingRoomUser.setMruMrNo(mrNo);
      meetingRoomUser.setMruUNo(uNo);
      System.out.println(meetingRoomUser.getMruMrNo()+"미팅룸넘버"+meetingRoomUser.getMruUNo()+"유저넘버");
      if(meetingRoomUserService.deleteMeetingRoomUser(meetingRoomUser)) {
         meetingRoomService.updateMeetingRoomCntDown(meetingRoomUser.getMruMrNo());
         return new ResponseEntity<String>("약속방 삭제 성공", HttpStatus.OK);
      }
      return new ResponseEntity<String>("약속방 삭제 실패", HttpStatus.NO_CONTENT);
   }
	
}
