package com.shard.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.shard.domain.PayVO;

public interface PayMapper {
	public void payInsert(PayVO pvo);
	public void cartDelete(@Param("cartNum")int cartNum, @Param("itemNumList")List<Integer> itemNumList);
	public void useCouponIssuanceUpdate(@Param("orderId")int orderId, @Param("issueNum")int issueNum);
	public void payCompleteUpdate(int orderId);
	public void ordersUpdate(@Param("usePoint")int usePoint, @Param("orderId")int orderId);
	public void customerPointUpdate(@Param("usedpoint")int usedpoint, @Param("email")String email);
	public int getTotalAmountForLast3Months(String email);
	public void membershipUpdate(int memNum);
	
}