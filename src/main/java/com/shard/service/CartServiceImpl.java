package com.shard.service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shard.domain.DetailCartVO;
import com.shard.domain.ItemVO;
import com.shard.mapper.CartMapper;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class CartServiceImpl implements CartService{
	public static final int STANDARD_DELIVERY_CHARGE = 3000;
	
	@Autowired
	private CartMapper mapper;
	
	@Override
	public void initCart(String email, Integer itemNum, String size) {
		 int realItemNum = itemNum; // 기본값은 0으로 설정하거나 다른 값으로 지정
		Integer cartNum = mapper.getCartnum(email);
	    log.info("cartNum check >>" + cartNum);
		//cartNum으로 cart여부 체크
		//cartNum이 존재하지 않으면 cart와 해당 cart의 detailCart 생성
	    if (cartNum == null) {
	        mapper.cartInsert(email);
	        cartNum = mapper.getCartnum(email);
	        mapper.detailCartInsert(realItemNum, cartNum, size);
	    //기존 cart가 있으면 해당상품 detail itemNum을 체크, 
	    //존재하면 개수만 1 늘려주고 없으면 detailCart를 추가해준다.
	    }else if (cartNum != null) {
	        List<Integer> itemNumList = mapper.getItemNums(cartNum);
	        boolean itemNumExists = false;

	        for (Integer checkCart : itemNumList) {
	            if (checkCart.equals(realItemNum)) {
	                // cartNumList 안에 itemNum과 동일한 값이 있을 때 실행할 코드
	                mapper.detailCartCntInitUpdate(realItemNum, cartNum);
	                itemNumExists = true;
	                break;
	            }
	        }
	        // detailCart에 같은 itemNum이 없으면 detailCart에 해당 itemNum 추가
	        if (!itemNumExists) 
	        	mapper.detailCartInsert(realItemNum, cartNum, size);
	    }
	}
	
	@Override
	public int getCartnum(String email) {
		return mapper.getCartnum(email);
		
	}
	
	//장바구니에 담긴 상품 List
	@Override
	public List<ItemVO> detailCartItems(int cartNum) {
		List<Integer> itemNumList = mapper.getItemNums(cartNum);
		
		 // itemNumList가 비어 있다면 빈 결과를 반환
	    if (itemNumList.isEmpty()) {
	        return Collections.emptyList();
	    }
	    
	    return mapper.getDetailcartItems(itemNumList);
	}

	@Override
	public int getMembership(String email) {
		return mapper.getMembership(email);
	}

	@Override
	public int getPointRate(int memNum) {
		return mapper.getPointRate(memNum);
	}
	
	@Override
	public int deliveryCharge(int memNum) {
	    int result = STANDARD_DELIVERY_CHARGE;
	   
	    // memNum이 2(memdership 등급 - shard)인 경우 배송료를 0으로 설정
	    if (memNum == 2) {
	        result = 0;
	    }
	    return result;
	}
	
	@Override
	public void chooseDetailCartDelete(List<Integer> itemNumList, int cartNum) {
		mapper.chooseDetailCartDelete(itemNumList, cartNum);
	}

	@Override
	public void allDetailCartDelete(Integer cartNum) {
		mapper.allDetailCartDelete(cartNum);
	}
	
	//DetailOrderVO 객체에서 getCartItemCnts 메서드를 호출하여 cartItemCnts 값을 추출
	//이를 스트림의 map 함수를 통해 각 DetailOrderVO 객체에 적용하고, 그 결과를 리스트로 수집
	@Override
	public List<Integer> extractCartItemCnts(int cartNum) {
		List<DetailCartVO> dvo = mapper.getDetailCart(cartNum);
	        return dvo.stream()
	            .map(DetailCartVO::getCartItemCnt)
	            .collect(Collectors.toList());
	}
	@Override
	public List<Integer> extractSales(List<ItemVO> ivo) {
//		List<Integer> itemNums = mapper.getItemNums(cartNum);
//		List<ItemVO> ivo = mapper.getDetailcartItems(itemNums);
		return ivo.stream()
				.map(ItemVO::getSale)
				.collect(Collectors.toList());
	}
	
	//sum(가격*수량)
	@Override
    public int calculateTotalPrice(List<Integer> sales, List<Integer> itemCnts) {
        // 예외 처리: 두 리스트의 크기가 다르면 예외 처리
        if (sales.size() != itemCnts.size()) 
            throw new IllegalArgumentException("두 리스트의 크기가 같아야 합니다.");
        
        int totalPrice = 0;
        // 각 index를 곱하고 더하기
        for (int i = 0; i < sales.size(); i++) {
            int price = sales.get(i);
            int quantity = itemCnts.get(i);
            totalPrice += price * quantity;
        }
        return totalPrice;
    }
	
	//상품금액에는 0.1을 곱하고 10원 단위로 내림된 값
	@Override
    public List<Integer> itemRewardPoints(List<Integer> sales, int pointRate) {
    List<Integer> rewardPoints = sales.stream()
    .map(sale -> (int) Math.floor(sale * (pointRate/100.0)))
    .collect(Collectors.toList());
    return rewardPoints;
	}
}
