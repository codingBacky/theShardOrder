package com.shard.service;

import java.util.List;

import com.shard.domain.ShardMemberVO;

public interface UserService {
	public int userCheck(String userId, String userPwd);
	
	public ShardMemberVO getUser(String userId);
	
	public int idCheck(String userId);

	public ShardMemberVO getUserEmail(String email);

	public List<ShardMemberVO> getUserList();

	public int insertUser(ShardMemberVO vo);
	
	public int insertKakaoUser(ShardMemberVO vo);

	public int updateUser(ShardMemberVO vo);

	public int deleteUser(String userId);
	
	public int emailCheck(String email);

	public int adminCheck(String userId, String userPwd);
}
