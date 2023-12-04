package com.shard.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shard.domain.ShardMemberVO;
import com.shard.service.KakaoLoginService;
import com.shard.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller
@SessionAttributes({ "user", "token" })
@RequestMapping("/shard/*")
@Log4j
@RequiredArgsConstructor
public class UserController {

	private final KakaoLoginService kakaoLoginService;

	private final UserService userservice;

	// index 메인 페이지로 이동
	@GetMapping("")
	public String index() {
		log.info("index");
		return "index";
	}

	// 로그인 페이지로 이동
	@GetMapping("/login")
	public String login() {
		log.info("login");
		return "user/shardLogin";
	}

	// 회원가입 페이지로 이동
	@GetMapping("/join")
	public String join(ShardMemberVO vo, RedirectAttributes rttr, Model model) {
		log.info("join");
		return "user/shardJoin";
	}

	// 카카오 회원가입 페이지로 이동
	@GetMapping("/kakaoJoin")
	public String kakaoJoin(Model model, @ModelAttribute("userEmail") String userEmail, @ModelAttribute("nickName") String nickName) {
		log.info("kakaoJoin");
		model.addAttribute("userEmail", userEmail);
		model.addAttribute("nickName", nickName);
		return "user/kakaoJoin";
	}

	// 일반로그인을 했을 때 로그아웃
	@GetMapping("/logout")
	public String logout(SessionStatus status, Model model) {
		status.setComplete();
		model.addAttribute("result", "logout");

		return "index";
	}

	// 카카오로 로그인했을 때 따로 만든 카카오 로그아웃
	@GetMapping("/kakaoLogout")
	public String kakaoLogout(SessionStatus status, RedirectAttributes rttr) throws Throwable {
		status.setComplete();
		rttr.addFlashAttribute("result", "logout");

		return "redirect:/shard/";
	}

	// ajax로 회원가입할 때 데이터베이스에 사용중인 아이디가 있는지 체크
	@GetMapping("/idCheck")
	public void idCheck(String userId, HttpServletResponse response) throws Exception {
		System.out.println(userId);
		int result = userservice.idCheck(userId);
		MultiValueMap<String, Object> json = new LinkedMultiValueMap<>();
		json.add("result", result);
		response.setContentType("application/json");
		response.getWriter().write(json.toString());
	}

	// 회원이 로그인할 때 체킹
	@PostMapping("/login")
	public String userLogin(Model model, @RequestParam("userId") String userId, @RequestParam("userPwd") String userPwd) {
		String url = "";
		
		int result = userservice.userCheck(userId, userPwd);
		
		if (result == 1) {
			ShardMemberVO vo = userservice.getUser(userId);
			model.addAttribute("user", vo);
			url = "index";
		} else {
			model.addAttribute("result", -1);
			url = "user/shardLogin";
		}
		return url;
	}
	
	// 관리자 로그인할 때 체킹
	@PostMapping("/adminLogin")
	public String adminLogin(Model model, @RequestParam("userId") String userId, @RequestParam("userPwd") String userPwd) {
		String url = "";
		
		int result = userservice.adminCheck(userId, userPwd);
		
		if (result == 1) {
			ShardMemberVO vo = userservice.getUser(userId);
			model.addAttribute("user", vo);
			url = "index";
		} else {
			model.addAttribute("result", "noAdmin");
			url = "user/shardLogin";
		}
		return url;
	}

	// 카카오 로그인을 할 때
	@GetMapping("/login/oauth")
	public String kakaoOauth(@RequestParam(required = false) String code, Model model, RedirectAttributes rttr)
			throws Throwable {
		String url = "";

		String access_Token = kakaoLoginService.getAccessToken(code);

		HashMap<String, Object> userInfo = kakaoLoginService.getUserInfo(access_Token);
		System.out.println("###nickName###" + userInfo.get("nickName"));
		System.out.println("###email###" + userInfo.get("email"));

		String userEmail = (String) userInfo.get("email");
		String nickName = (String) userInfo.get("nickName");
		int result = userservice.emailCheck(userEmail);

		if (result != 0) {
			ShardMemberVO vo = userservice.getUserEmail(userEmail);
			rttr.addAttribute("user", vo);
			rttr.addAttribute("token", access_Token);
			url = "redirect:/shard/";
		} else {
			rttr.addAttribute("userEmail", userEmail);
			rttr.addAttribute("nickName", nickName);
			url = "redirect:/shard/kakaoJoin";
		}
		return url;
	}

	@PostMapping("/join")
	public String join(ShardMemberVO vo, Model model, @RequestParam("birthYear") int year,
			@RequestParam("birthMonth") int month, @RequestParam("birthDay") int day) {
		String dob = year + "-" + month + "-" + day;
		vo.setDob(dob);
		int result = userservice.insertUser(vo);
		if (result == 1) {
			model.addAttribute("result", "success");
		} else {
			model.addAttribute("result", "faild");
		}
		return "user/shardLogin";
	}
	
	@PostMapping("/kakaoJoin")
	public String kakaoJoin(Model model, ShardMemberVO vo, @RequestParam("birthYear") int year,
			@RequestParam("birthMonth") int month, @RequestParam("birthDay") int day) {
		String dob = year + "-" + month + "-" + day;
		vo.setDob(dob);
		int result = userservice.insertKakaoUser(vo);
		return "";
	}
}