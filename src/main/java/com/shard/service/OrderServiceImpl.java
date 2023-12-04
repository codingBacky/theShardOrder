package com.shard.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.shard.domain.CouponIssuanceVO;
import com.shard.domain.CouponVO;
import com.shard.domain.DeliverAddrVO;
import com.shard.domain.DetailOrderVO;
import com.shard.domain.ItemVO;
import com.shard.domain.OrdersVO;
import com.shard.domain.ShardMemberVO;
import com.shard.mapper.OrdersMapper;

import lombok.extern.log4j.Log4j;

@Configuration
@EnableTransactionManagement
@Service
@Log4j
public class OrderServiceImpl implements OrderService{

	@Autowired
	private OrdersMapper mapper;

	@Override
	public ShardMemberVO getCustomer(String email) {
		return mapper.getCustomer(email);
	}
	
	@Override
	public List<Integer> getDetailOrder(int orderId) {
		return mapper.getDetailOrder(orderId);
	}

	@Override
	public List<ItemVO> getDetailOrderItems(List<Integer> itemNumList) {
		return mapper.getDetailOrderItems(itemNumList);
	}

	@Override
	public List<CouponIssuanceVO> getCouponIssuance(String email) {
		return mapper.getCouponIssuance(email);
	}
	
	//CouponIssuanceVO list 에서 couponNum추출
	@Override
	public List<Integer> extractCouponNums(List<CouponIssuanceVO> cvo) {
	        return cvo.stream()
	            .map(CouponIssuanceVO::getCouponNum)
	            .collect(Collectors.toList());
	}
	//쿠폰당 할인(할인율, 금액할인) 가져오기
	@Override
	public List<CouponVO> getCoupon(List<Integer> couponNumList) {
		return mapper.getCoupon(couponNumList);
	}
	//할인율, 금액할인 구분하여 차감할 금액 산출(전체 금액에서)
	
	
	//배송지 수정할때 기본배송지로 수정할 경우 기존 기본배송지 ->defaultWhether =0 으로 변경
	//배송지 수정할때 기본배송지에서 일반배송지로는 수정불가 다른 배송지를 기본배송지로 정할경우에 일반배송지로 변경 후 배송지 수정
	@Override
	public void deliverAddrUpdate(DeliverAddrVO dvo, int addrNum, String email) {
		Integer defaultAddress= mapper.getDefaultAddress(email);
		mapper.defaultAddressUpdate(defaultAddress);
		mapper.deliverAddrUpdate(dvo, addrNum);
	}
	
	//배송지를 추가할때 기본배송지를 새로 추가할 경우 기존 기본배송지 ->defaultWhether =0 으로 변경 후 새배송지 추가
	@Transactional
	@Override
	public void addDeliverAddr(DeliverAddrVO dvo, String email) {
		Integer defaultAddress= mapper.getDefaultAddress(email);
		mapper.defaultAddressUpdate(defaultAddress);
		mapper.addDeliverAddr(dvo);
	}
	@Transactional
	@Override
	public int orderInsert(OrdersVO ovo) {
		mapper.orderInsert(null);
		int result = mapper.getLastInsertId();
		return result;
	}
	
	//주문취소
	@Transactional
	@Override
	public void orderCancle(int orderId) {
		int point = mapper.getUsePoint(orderId);
		mapper.couponReturnUpdate(orderId);
		mapper.payCompleteReturnUpdate(orderId);
		mapper.customerPointReturnUpdate(point);
	}

	//DetailOrderVO 객체에서 getCartItemCnts 메서드를 호출하여 cartItemCnts 값을 추출
	//이를 스트림의 map 함수를 통해 각 DetailOrderVO 객체에 적용하고, 그 결과를 리스트로 수집
	@Override
	public List<Integer> extractCartItemCnts(List<DetailOrderVO> dvo) {
	        return dvo.stream()
	            .map(DetailOrderVO::getCnt)
	            .collect(Collectors.toList());
	}

	@Override
	public int calculateDiscountedAmount() {
		// TODO Auto-generated method stub
		return 0;
	}
		
	//sum(가격*수량)
//	@Override
//    public int calculateTotalPrice(List<Integer> sales, List<Integer> cnt) {
//        // 예외 처리: 두 리스트의 크기가 다르면 예외 처리
//        if (sales.size() != cnt.size()) 
//            throw new IllegalArgumentException("두 리스트의 크기가 같아야 합니다.");
//        
//        int totalPrice = 0;
//        // 각 index를 곱하고 더하기
//        for (int i = 0; i < sales.size(); i++) {
//            int price = sales.get(i);
//            int quantity = cnt.get(i);
//            totalPrice += price * quantity;
//        }
//        return totalPrice;
//    }
}
