package com.anjanda.letsmeet.repository.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.anjanda.letsmeet.repository.dto.User;

/**
 * 
 * @Date : 2021. 2. 4.
 * @Team : AnJanDa
 * @author : 김지현
 * @deploy : 김동빈
 * @Project : 레쓰밋 :: backend
 * @Function : 친구 관련 매퍼 인터페이스 클래스
 * @Description : FriendService 및 friendmapper.xml 참고
 * 
 */

@Mapper
public interface FriendMapper {
	

	/* C :: 사용자의 친구 추가 */
	public int insertFriend(int myUNo, int friend) throws Exception;
	
	/* R :: 사용자의 친구 조회 */
	public List<User> selectFriend(int myUNo) throws Exception;
	
	/* R :: 사용자가 검색한 친구 목록 조회 */
	public List<User> selectSearchFriend(String uEmail) throws Exception;
	
	/* D :: 사용자의 친구 삭제 */
	public int deleteFriend(int myUNo, int friend) throws Exception;

	public List<User> selectSearchFriendByname(String uEmail);
}
