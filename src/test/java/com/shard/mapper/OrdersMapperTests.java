package com.shard.mapper;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.shard.domain.DeliverAddrVO;
import com.shard.domain.DetailOrderVO;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@Log4j
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class OrdersMapperTests {
	
	@Autowired
	private OrdersMapper mapper;

	@Test
	public void orderInsert() {
	//	mapper.orderInsert(10000, "email14@gmail.com");
		log.info(mapper.getLastInsertId());
	}
	@Test
	public void detailOrderInsert() {
		DetailOrderVO dvo = DetailOrderVO.builder()
				.itemNum(1)
				.cnt(2)
				.orderId(1)
				.size("S")
				.build();
				
		mapper.detailOrderInsert(dvo);
	}
	@Test
	public void getCustomer() {
		mapper.getCustomer("email14@gmail.com");
	}
	@Test
	public void getDetailOrder() {
		mapper.getDetailOrder(1);
	}
	@Test
	public void getDetailOrderItem() {
		List<Integer> list = new ArrayList<Integer>();
		list.add(4);
		list.add(5);
		list.add(6);
	//	mapper.getDetailOrderItem(list);
	}
	
	
	@Test
	public void addDeliverAddr() {
		DeliverAddrVO dvo = DeliverAddrVO.builder()
				.addrName("우리집")
				.zipCode(19999)
				.userAddr("경기도 수원시 인계동")
				.detailAddr("에스팝타워 101동 111호")
				.email("email15@gmail.com")
				.defaultWhether(1)
				.build();
		mapper.addDeliverAddr(dvo);
	}
	
	@Test
	public void getCouponIssuance() {
		mapper.getCouponIssuance("email15@gmail.com");
	}
	
	@Test
	public void getCoupon() {
		List<Integer> list = new ArrayList<Integer>();
		list.add(1);
		list.add(2);
		list.add(3);
		mapper.getCoupon(list);
	}
	@Test
	public void detailOrdersDelete() {
		mapper.detailOrdersDelete(2);
	}
	@Test
	public void ordersDelete() {
		mapper.ordersDelete(2);
	}
	
	
}
