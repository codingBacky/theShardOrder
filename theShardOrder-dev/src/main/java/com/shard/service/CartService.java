package com.shard.service;

import java.util.List;
import java.util.OptionalInt;

import com.shard.domain.DetailCartVO;
import com.shard.domain.ItemVO;

public interface CartService {

	public void initCart(String email, Integer itemNum, String size);
	public int getCartnum(String email);
	public List<ItemVO> detailCartItems(int cartNum);
	public List<Integer> getItemNums(int cartNum);
	//멤버십에 따른 배송비, 적립률 산정을 위한
	public int getMembership(String email);
	public int getPointRate(int memNum);
	public int  deliveryCharge(int memNum);
	public void chooseDetailCartDelete(List<Integer> itemNumList,int cartNum);
	public void allDetailCartDelete(Integer cartNum);
	//계산
	//같은 cartNum의 DetailOrderVO List에서 cartItemCnt추출해서 List애 담음
	public List<Integer> extractCartItemCnts(int cartNum);
	//같은 cartNum의 item List에서 cartItemCnt추출해서 List애 담음
	public List<Integer> extractSales(List<ItemVO> ivo);
	public List<Integer> totalPriceList(List<Integer> sales, List<Integer> itemCnts);
	//List[ item.itemNum의 sale * detailcart.cartNum/email 의 cartItemCnt ]를 SUM 
	public int calculateTotalPrice(List<Integer> sales, List<Integer> cartItemCnts);
	//item당 적립금
	public List<Integer> itemRewardPoints(List<Integer> sales, int pointRate);
	//수량수정시 수량변경
	public void detailCartCntUpdate(int detailCartCntUpdate, int itemNum, int cartNum);
}
