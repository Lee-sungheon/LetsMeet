package com.anjanda.letsmeet.meetingroomuser.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anjanda.letsmeet.repository.dto.MeetingRoomUser;
import com.anjanda.letsmeet.repository.dto.MeetingRoomUserInfo;
import com.anjanda.letsmeet.repository.mapper.MeetingRoomUserMapper;

/**
 * 
 * @Date : 2021. 2. 4.
 * @Team : AnJanDa
 * @author : 김지현
 * @deploy : 김동빈
 * @Project : 레쓰밋 :: backend
 * @Function : 약속방 구성 멤버 서비스 클래스
 * @Description
 *
 */

@Service
public class MeetingRoomUserServiceImpl implements MeetingRoomUserService {

	/* 미팅룸 매퍼 객체 불러오기 */
	@Autowired
	private MeetingRoomUserMapper meetingRoomUserMapper;
	
	/* C :: 약속방 내 멤버 추가 (일단 모두가 가능하도록..) */
	@Override
	public int createMeetingRoomUser(MeetingRoomUser meetingRoomUser) throws Exception {
		return meetingRoomUserMapper.insertMeetingRoomUser(meetingRoomUser);
	}
	
	/* R :: 약속방 내 멤버 조회 */
	@Override
	public List<MeetingRoomUserInfo> reviewMyMeetingRoomUser(int mrNo) {
		return meetingRoomUserMapper.selectMyMeetingRoomUser(mrNo);
	}
	
	/* U :: 약속방에 속한 본인의 위치와 일정 설정 */
	@Override
	public boolean updateMeetingRoomUserSet(MeetingRoomUser meetingRoomUser) {
		return meetingRoomUserMapper.updateMeetingRoomUserSet(meetingRoomUser) == 1;
	}

	@Override
	public boolean deleteMeetingRoomUser(MeetingRoomUser meetingRoomUser) {
		// TODO Auto-generated method stub
		return meetingRoomUserMapper.deleteMeetingRoomUser(meetingRoomUser) == 1;
	}

	@Override
	public boolean deleteMeetingRoomUserAll(int mrNo) {
		// TODO Auto-generated method stub
		return meetingRoomUserMapper.deleteMeetingRoomUserAll(mrNo) == 1;
	}

}
