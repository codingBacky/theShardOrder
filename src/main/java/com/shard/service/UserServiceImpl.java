package com.shard.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.shard.domain.ShardMemberVO;
import com.shard.mapper.UserMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

@Service
@Log4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	
	private final UserMapper mapper;
	
	@Override
	public int userCheck(String userId, String userPwd) {
		int result = 0;
		String pwd = mapper.userCheck(userId);
		if(pwd.equals(userPwd)) {
			result = 1;
		}else 
			result = 0;
		
		return result;
	}

	@Override
	public ShardMemberVO getUser(String userId) {
		return mapper.getUser(userId);
	}

	@Override
	public int idCheck(String userId) { // 회원가입할 때 id 체크
		int result = -1;

		String id = mapper.getUserId(userId);
		System.out.println(id);

		if (id != null) // 데이터베이스에 아이디가 있을 때
			result = 1;
		else // 데이터베이스에 아이디가 없을 때
			result = 0;

		return result;
	}

	@Override
	public List<ShardMemberVO> getUserList() {
		return mapper.getUserList();
	}

	@Override
	public int insertUser(ShardMemberVO vo) {
		return mapper.insertUser(vo);
	}
	
	@Override
	public int insertKakaoUser(ShardMemberVO vo) {
		return mapper.insertKakaoUser(vo);
	}

	@Override
	public int updateUser(ShardMemberVO vo) {
		return mapper.updateUser(vo);
	}

	@Override
	public int deleteUser(String email) {
		return mapper.deleteUser(email);
	}

	@Override
	public ShardMemberVO getUserEmail(String email) {
		return mapper.getUserEmail(email);
	}

	@Override
	public int emailCheck(String email) {
		int result = 0;
		String userEmail = mapper.emailCheck(email);
		if(userEmail != null) {
			result = 1;
		}else 
			result = 0;
		return result;
	}

	@Override
	public int adminCheck(String userId, String userPwd) {
		int result = 0;
		String pwd = mapper.adminCheck(userId);
		if(pwd.equals(userPwd)) {
			result = 1;
		}else 
			result = 0;
		return result;
	}
}
