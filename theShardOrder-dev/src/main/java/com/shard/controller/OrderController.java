package com.shard.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shard.domain.CouponIssuanceVO;
import com.shard.domain.CouponVO;
import com.shard.domain.ItemVO;
import com.shard.domain.ShardMemberVO;
import com.shard.service.OrderService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller
@SessionAttributes({"user", "token"})
@RequestMapping("/order/*")
@Log4j
@RequiredArgsConstructor
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	@PostMapping("/checkOut")
	public void order( @RequestParam String email,
					   @RequestParam int totalPrice,
					   @RequestParam int TotalPriceWithShipping,
					   @RequestParam int pointRate,
					   @RequestParam List<Integer> sales,
					   @RequestParam List<Integer> cnt,
					   @ModelAttribute("itemList")List<ItemVO> itemList, 
			Model model) {
		ShardMemberVO customer = orderService.getCustomer("email10@gmail.com");
		log.info(orderService.getCustomer("email10@gmail.com"));
		model.addAttribute("customer", customer);
		
		//model.addAttribute("itemList", ivoList);
		
		List<CouponIssuanceVO> cvoList = orderService.getCouponIssuance("email10@gmail.com");
		model.addAttribute("cvoList", cvoList);
		
		List<Integer> couponNums = orderService.extractCouponNums(cvoList);
		List<CouponVO> discount = orderService.getCoupon(couponNums);
		model.addAttribute("discount" + discount);
		
		//할인율, 할인금액 중 0이 아닌 숫자 추출
		
		
		//할인 적용된금액
		
		
		//pointRate를 적용해 총 적립금액 산출
		
		//총 결제금액
		//model.addAttribute("TotalPriceWithShipping", TotalPriceWithShipping);
	}
	
	//쿠폰선택
	@GetMapping("/chooseCouponOut")
	public String chooseCoupon(int discount, int TotalPriceWithShipping, List<ItemVO> ivoList, RedirectAttributes rttr) {
		//할인율, 할인금액 할인율 -> price * (discount/100), 할인 금액
		
		
		if(discount<100) {
			
		}else {
			
		}
		//할인 된 금액
		
		//쿠폰적용된금액
		
		//rttr.addAttribute("",)
		return "redirect:/order/checkOut";
	}
}