package com.shard.dao;

import java.sql.Connection;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.shard.mapper.UserMapper;
import com.shard.service.UserService;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@Log4j
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class ShardMemberDAOImplTest {
		
	@Autowired
	private UserMapper usermapper;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private DataSource dataSource;
	
	@Test
	public void idCheckTest() {
		log.info(usermapper.getUser("admin"));
	}
	
	@Test
	public void emailCheck() {
		log.info(usermapper.emailCheck("asdasd@gmail.com"));
	}
	
	@Test
	public void userCheck() {
		log.info(userService.userCheck("dodo607", "faker123!@"));
	}
	
	@Test
	public void connectTest() throws Exception{
		Connection con = dataSource.getConnection();
		
		log.info(con);
	}
}
