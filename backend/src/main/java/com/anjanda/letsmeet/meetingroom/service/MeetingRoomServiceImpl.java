package com.anjanda.letsmeet.meetingroom.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anjanda.letsmeet.repository.dto.MeetingRoom;
import com.anjanda.letsmeet.repository.mapper.MeetingRoomMapper;

/**
 * 
 * @Date : 2021. 2. 4.
 * @Team : AnJanDa
 * @author : 김지현
 * @deploy : 김동빈
 * @Project : 레쓰밋 :: backend
 * @Function : 약속방 서비스 클래스
 * @Description
 *	- 약속방 생성
 *	- 약속방 전체조회 (사용자 기준) -> 메인
 *	- 약속방 상세조회 => 메인에 보이는 것과,,, 끝난 약속 처리 필요...
 *	- 약속방 수정 -> 제일 중요
 *	- 약속방 삭제
 */

@Service
public class MeetingRoomServiceImpl implements MeetingRoomService{
	/* 미팅룸 매퍼 객체 불러오기 */
	@Autowired
	private MeetingRoomMapper meetingRoomMapper;
	
	/* C :: 약속방 생성 메소드 */
	@Override
	public int createMeetingRoom(MeetingRoom meetingRoom) throws Exception {
		return meetingRoomMapper.insertMeetingRoom(meetingRoom);
	}
	
	/* R :: 약속방 전체 조회 */
	@Override
	public List<MeetingRoom> reviewMyMeetingRoom(int uNo) throws Exception {
		return meetingRoomMapper.selectMeetingRoom(uNo);
	}
	
	/* R :: 약속방 상세 조회 */
	@Override
	public MeetingRoom reviewDetailMeetingRoom(int mrNo) throws Exception {
		return meetingRoomMapper.selectMeetingRoomByMrNo(mrNo);
	}
	
	/* R :: 캘린더에 표시될 확정된 약속 조회 */
	@Override
	public List<MeetingRoom> reviewCertainMeetingRoom(int uNo) throws Exception {
		return meetingRoomMapper.selectCertainMeetingRoom(uNo);
	}
	
	/* U :: 약속방 수정 */ 
	@Override
	public boolean updateMeetingRoom(MeetingRoom meetingRoom) throws Exception {
		return meetingRoomMapper.updateMeetingRoom(meetingRoom) == 1;
	}
	
	@Override
	public boolean updateMeetingRoomMidpoint(MeetingRoom meetingRoom) {
		// TODO Auto-generated method stub
		return meetingRoomMapper.updateMeetingRoomMidpoint(meetingRoom)==1;
	}
	
	@Override
	public boolean updateMeetingRoomFinalDate(MeetingRoom meetingRoom) throws Exception {
		return meetingRoomMapper.updateMeetingRoomFinalDate(meetingRoom)==1;
	}
	
	@Override
	public boolean updateMeetingRoomFinalPlace(MeetingRoom meetingRoom) throws Exception {
		return meetingRoomMapper.updateMeetingRoomFinalPlace(meetingRoom)==1;
	}
	
	/* D :: 약속방 삭제 */
	@Override
	public boolean deleteMeetingRoom(int mrNo) throws Exception {
		return meetingRoomMapper.deleteMeetingRoom(mrNo) == 1;
	}

	//미팅룸에 사람 추가 하기
	@Override
	public int createMeetingRoomUser(int mrNo, int uNo) throws Exception {
		// TODO Auto-generated method stub
		return meetingRoomMapper.createMeetingRoomUser(mrNo,uNo);
	}

	@Override
	public int selectMeetingRoomBySuper(MeetingRoom meetingRoom) throws Exception {
		// TODO Auto-generated method stub
		return meetingRoomMapper.selectMeetingRoomBySuper(meetingRoom);
	}

	@Override
	public void updateMeetingRoomCntUp(int mruMrNo) {
		meetingRoomMapper.updateMeetingRoomCntUp(mruMrNo);
		
	}

	@Override
	public void updateMeetingRoomCntDown(int mruMrNo) {
		meetingRoomMapper.updateMeetingRoomCntDown(mruMrNo);
		
	}

	@Override
	public void updateMeetingRoomUser(int mrNo) {
		meetingRoomMapper.updateMeetingRoomUserInMeetingRoom(mrNo);
		
	}



}
