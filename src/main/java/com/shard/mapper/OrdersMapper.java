package com.shard.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectKey;

import com.shard.domain.CouponIssuanceVO;
import com.shard.domain.CouponVO;
import com.shard.domain.DeliverAddrVO;
import com.shard.domain.DetailOrderVO;
import com.shard.domain.ItemVO;
import com.shard.domain.OrdersVO;
import com.shard.domain.ShardMemberVO;

public interface OrdersMapper {
	//주문확인 후 pay로 넘어갈때 orders 생성
	public void orderInsert(OrdersVO ovo);
	// 삽입 후 생성된 키 받기
	@SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="orderId", before=false, resultType=int.class)
	public int getLastInsertId();
	//detailOrder 생성
	public void detailOrderInsert(DetailOrderVO dvo);
	
	//주문 데이터 가져오기
	public ShardMemberVO getCustomer(String email);
	public List<Integer> getDetailOrder(int orderId);
	public List<ItemVO> getDetailOrderItems(@Param("itemNumList")List<Integer> itemNumList);
	public List<CouponIssuanceVO> getCouponIssuance(String email);
	public List<CouponVO> getCoupon(@Param("couponNumList")List<Integer> couponNumList);
	//고객 기본배송지 찾기
	public int getDefaultAddress(String email);
	
	//고객 기본배송지 -> 일반배송지로 수정
	public void defaultAddressUpdate(int addrNum);
	
	//배송지 저장(추가)
	public void addDeliverAddr(DeliverAddrVO dvo);
	//배송지 수정
	public void deliverAddrUpdate(@Param("dvo")DeliverAddrVO dvo, @Param("addrNum")int addrNum);
	
	//주문 페이지를 벗어날 경우 주문 삭제
	//참조된 detailOrder먼저 삭제 후 orders삭제
	public void detailOrdersDelete(int orderId);
	public void ordersDelete(int orderId);
	
	//주문 취소시
	//주문검색 usePoint select
	public int getUsePoint(int orderId);
	//사용했던 쿠폰 return
	public void couponReturnUpdate(int orderId);
	//payComplete->0로 변경
	public void payCompleteReturnUpdate(int orderId);
	//사용했던 point retune
	public void customerPointReturnUpdate(int usePoint);
	
	//배송완료 후 membershipUpdate
	public int getTotalAmountForLast3Months(String email);
	public int membershipUpdate(int memNum);
	
	}