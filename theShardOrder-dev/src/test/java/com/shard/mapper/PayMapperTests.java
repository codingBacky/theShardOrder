package com.shard.mapper;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.shard.domain.PayVO;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@Log4j
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class PayMapperTests {
	
	@Autowired
	private PayMapper mapper;

	@Test
	public void payInsert() {
		PayVO pvo = PayVO.builder()
				.orderId(4)
				.email("email15@gmail.com")
				.payTotal(50000)
				.payMethod("creadit card")
				.build();
		mapper.payInsert(pvo);
	}
	
	@Test
	public void cartDelete() {
		List<Integer> list = new ArrayList<Integer>();
		list.add(1);
		list.add(2);
		mapper.cartDelete(1, list);
	}
	
	@Test
	public void useCouponIssuanceUpdate() {
		mapper.useCouponIssuanceUpdate(4, 3);
	}
	
	@Test
	public void payCompleteUpdate() {
		mapper.payCompleteUpdate(4);
	}
	
	@Test
	public void ordersUpdate() {
		mapper.ordersUpdate(1000,4);
	}
	
	@Test
	public void customerPointUpdate() {
		mapper.customerPointUpdate(1000,"email15@gmail.com");
	}
	
	@Test
	public void getTotalAmountForLast3Months() {
		mapper.getTotalAmountForLast3Months("email15@gmail.com");
	}
}
