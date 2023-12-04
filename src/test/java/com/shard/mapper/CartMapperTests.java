package com.shard.mapper;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@Log4j
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class CartMapperTests {
	
	@Autowired
	private CartMapper mapper;

	@Test
	public void getCartnum() {
		mapper.getCartnum("email10@gmail.com");
	}
	@Test
	public void detailCartCntInitUpdate() {
		mapper.detailCartCntInitUpdate(2,1);
	}
	@Test
	public void cartInsert() {
		mapper.cartInsert("email11@gmail.com");
	}
	@Test
	public void detailCartInsert() {
		mapper.detailCartInsert(5, 1, "M");
	}
	@Test
	public void getDetailCart() {
		mapper.getDetailCart(1);
	}
	@Test
	public void getMembership() {
		mapper.getMembership("email10@gmail.com");
	}
	@Test
	public void chooseDetailCartDelete() {
		List<Integer> list = new ArrayList<Integer>();
		list.add(4);
		list.add(5);
		mapper.chooseDetailCartDelete(list, 1);
	}
	@Test
	public void allDetailCartDelete() {
		mapper.allDetailCartDelete(2);
	}
}
