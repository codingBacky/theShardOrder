package com.shard.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shard.domain.ItemVO;
import com.shard.service.CartService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller
@SessionAttributes({"user", "token"})
@RequestMapping("/order/*")
@Log4j
@RequiredArgsConstructor
public class CartController {
	
	@Autowired
	private CartService cartService;
	
	@GetMapping("/cart")
	public void cart(/*@ModelAttribute("email")*/String email, Integer itemNum, String size, Model model) {
		model.addAttribute("email", "email10@gmail.com");
		
		int cartNum = cartService.getCartnum("email10@gmail.com");
		log.info("cartNum >>" + cartNum);
		
		cartService.initCart("email10@gmail.com", 1, "S");
		log.info("cart 들어옴");
		
		//cartService.detailCartItems(mapper.getCartnum(email));
		log.info("detailCartItems view >>" + cartService.detailCartItems(cartNum));
		model.addAttribute("cartNum", cartNum);
		
		List<ItemVO> ivo= cartService.detailCartItems(cartNum);
		model.addAttribute("itemList", ivo);
		
		int memNum = cartService.getMembership("email10@gmail.com");
		log.info("getMembership view >>" + cartService.getMembership("email10@gmail.com"));
		
		int pointRate = cartService.getPointRate(memNum);
		int deliveryCharge = cartService.deliveryCharge(memNum);
		log.info("getPointRate view >>" + cartService.getPointRate(memNum));
		log.info("deliveryCharge view >>" + deliveryCharge);
		model.addAttribute("deliveryCharge", deliveryCharge);
		
		
		List<Integer> extractCartItemCnts = cartService.extractCartItemCnts(cartNum);
		List<Integer> sales = cartService.extractSales(ivo);
		log.info("extractCartItemCnts >>"+ extractCartItemCnts);
		log.info("sales >>" + sales);
		model.addAttribute("extractCartItemCnts", extractCartItemCnts);
		
		//인덱스의 범위를 생성하고, 각 인덱스에 대해 두 리스트를 곱하여 새로운 리스트를 생성
		List<Integer> totalPriceList = cartService.totalPriceList(sales, extractCartItemCnts);
		log.info("totalPriceList" + totalPriceList);
		model.addAttribute("totalPriceList",totalPriceList);
		
		int totalPrice = cartService.calculateTotalPrice(sales, extractCartItemCnts);
		log.info("totalPrice >>" + totalPrice);
		model.addAttribute("totalPrice", totalPrice);
		
		int TotalPriceWithShipping = deliveryCharge + totalPrice;
		log.info(TotalPriceWithShipping);
		model.addAttribute("TotalPriceWithShipping", TotalPriceWithShipping);
		
		List<Integer> itemRewardPoints = cartService.itemRewardPoints(sales, pointRate);
		log.info("itemRewardPoints >>" + itemRewardPoints);
		model.addAttribute("itemRewardPoints", itemRewardPoints);
	}
	@GetMapping("/cartReflash")
	public String cartReflash(@RequestParam("email") String email,
					            @RequestParam("cartNum") int cartNum,
					            Model model) {
		
		log.info("cart 들어옴");
		log.info("cartNum >>" + cartNum);
		List<ItemVO> ivoList= cartService.detailCartItems(cartNum);
		log.info("ivoList >>" + ivoList);
		 // itemNumList가 비어 있다면 빈 결과를 반환
		if (ivoList.isEmpty()) {
	        // 처리할 내용 추가
	        return "redirect:/order/cart_empty"; // 빈 카트 페이지로 리다이렉트 또는 이동
	    }
		
		model.addAttribute("itemList", ivoList);
	    
		//cartService.detailCartItems(mapper.getCartnum(email));
		log.info("detailCartItems view >>" + cartService.detailCartItems(cartNum));
		model.addAttribute("cartNum", cartNum);
		
		
		
		int memNum = cartService.getMembership("email10@gmail.com");
		log.info("getMembership view >>" + cartService.getMembership("email10@gmail.com"));
		
		int pointRate = cartService.getPointRate(memNum);
		int deliveryCharge = cartService.deliveryCharge(memNum);
		log.info("getPointRate view >>" + cartService.getPointRate(memNum));
		log.info("deliveryCharge view >>" + deliveryCharge);
		model.addAttribute("deliveryCharge", deliveryCharge);
		
		
		List<Integer> extractCartItemCnts = cartService.extractCartItemCnts(cartNum);
		List<Integer> sales = cartService.extractSales(ivoList);
		log.info("extractCartItemCnts >>"+ extractCartItemCnts);
		log.info("sales >>" + sales);
		model.addAttribute("extractCartItemCnts", extractCartItemCnts);
		
		int totalPrice = cartService.calculateTotalPrice(sales, extractCartItemCnts);
		log.info("totalPrice >>" + totalPrice);
		model.addAttribute("totalPrice", totalPrice);
		
		int TotalPriceWithShipping = deliveryCharge + totalPrice;
		log.info(TotalPriceWithShipping);
		model.addAttribute("TotalPriceWithShipping", TotalPriceWithShipping);
		
		List<Integer> itemRewardPoints = cartService.itemRewardPoints(sales, pointRate);
		log.info("itemRewardPoints >>" + itemRewardPoints);
		model.addAttribute("itemRewardPoints", itemRewardPoints);
		
		return "/order/cart";
	}
	@GetMapping("/cart_empty")
	public String cartEmpty() {
		return "/order/cart";
	}
	
	@PostMapping("/chooseDetailCartDelete")
	@ResponseBody
	public String chooseDetailCartDelete(@RequestParam(value="selectedItems[]") List<Integer> selectedItems , RedirectAttributes rttr) {
	    // JSON으로 전송된 데이터에서 필요한 값을 추출
		//선택상품 detailCart삭제
	//	cartService.chooseDetailCartDelete(itemNumList, cartNum);
		
		//cartNum에 detailCart다시 가져오기
	//	List<ItemVO> ivo= cartService.detailCartItems(cartNum);
	//	rttr.addFlashAttribute("itemList", ivo);
		
		return "redirect:/order/cartReflash";
	}

	@PostMapping("/allDetailCartDelete")
	public String allDetailCartDelete(@RequestParam("cartNum") int cartNum,
	                                  @RequestParam("email") String email,
	                                  RedirectAttributes rttr) {
	    try {
	        // 서버에서의 작업 수행 (예: DB에서 데이터 삭제)
	        cartService.allDetailCartDelete(cartNum);

	        // 작업이 성공한 경우 데이터를 리다이렉트 시 전달
	        rttr.addAttribute("email", email);
	        rttr.addAttribute("cartNum", cartNum);

	        return "redirect:/order/cartReflash";
	    } catch (Exception e) {
	        // 작업이 실패한 경우 에러 페이지로 리다이렉트 또는 다른 처리
	        return "redirect:/error";
	    }
	}
	
	@GetMapping("/updateExpectedAmount")
	public String updateExpectedAmount( @RequestParam(name = "cnt") int cnt,
										@RequestParam(name = "itemNum") int itemNum,
										@RequestParam(name = "cartNum") int cartNum) {
		    cartService.detailCartCntUpdate(cnt, itemNum, cartNum);
		return "redirect:/order/cartReflash";
	}
	
}