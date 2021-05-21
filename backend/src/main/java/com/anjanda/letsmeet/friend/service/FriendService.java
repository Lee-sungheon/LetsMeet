package com.anjanda.letsmeet.friend.service;

import java.util.List;

import com.anjanda.letsmeet.repository.dto.User;

/**
 * 
 * @Date : 2021. 2. 4.
 * @Team : AnJanDa
 * @author : 김지현
 * @deploy : 김동빈
 * @Project : 레쓰밋 :: backend
 * @Function : Friend 서비스 인터페이스 클래스
 * @Description
 *
 */

public interface FriendService {

	/* C :: 사용자의 친구 추가 */
	int createFriend(int myUNo, int friend) throws Exception;

	/* R :: 사용자의 친구 조회 */
	List<User> reviewFriend(int myUNo) throws Exception;

	/* R :: 사용자가 검색한 친구 목록 조회 */
	public List<User> reviewSearchFriend(String uEmail) throws Exception;
	
	/* D :: 사용자의 친구 삭제 */
	boolean deleteFriend(int myUNo, int friend) throws Exception;


	List<User> SearchFriendByname(String uEmail);

}