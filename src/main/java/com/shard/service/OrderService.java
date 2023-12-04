package com.shard.service;

import java.util.List;

import com.shard.domain.CouponIssuanceVO;
import com.shard.domain.CouponVO;
import com.shard.domain.DeliverAddrVO;
import com.shard.domain.DetailOrderVO;
import com.shard.domain.ItemVO;
import com.shard.domain.OrdersVO;
import com.shard.domain.ShardMemberVO;

public interface OrderService{
	//필요한 데이터 가져오기
	public ShardMemberVO getCustomer(String email);
	public List<Integer> getDetailOrder(int orderId);
	public List<ItemVO> getDetailOrderItems(List<Integer> itemNumList);
	public List<CouponIssuanceVO> getCouponIssuance(String email);
	public List<Integer> extractCouponNums(List<CouponIssuanceVO> cvoList);
	public List<CouponVO> getCoupon(List<Integer> couponNumList);
	//배송지 추가/변경
	public void addDeliverAddr(DeliverAddrVO dvo, String email);
	public void deliverAddrUpdate(DeliverAddrVO dvo, int addrNum, String email);
	public int orderInsert(OrdersVO ovo);
	//계산
	
	public int calculateDiscountedAmount();
	//DetailOrderVO에서 cartItemCnt추출해서 List애 담음
	public List<Integer> extractCartItemCnts(List<DetailOrderVO> dvo);
	//List[ item.itemNum의 sale * detailcart.cartNum/email 의 cartItemCnt ]를 SUM 
//	public int calculateTotalPrice(List<Integer> sales, List<Integer> cnt);
	//주문취소
	public void orderCancle(int orderId);
}
