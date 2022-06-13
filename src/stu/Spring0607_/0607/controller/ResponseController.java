package com.spring.basic.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.spring.basic.model.UserVO;

@Controller
@RequestMapping("/response")
public class ResponseController {

	@GetMapping("/res-ex01")
	public void resEx01() {

	}

	/*
	 //1.Model 객체를 사용하여 화면에 데이터 전송하기.
	  
	  @GetMapping("/test") 
	  public void test(@RequestParam("age") int age,Model  model) {
	 
	  //모델은 뷰페이지로 자동으로 전달함
	  model.addAttribute("age",age);
	  model.addAttribute("nick","멍멍이"); 
	  }
	  
	 */	

	//2.@ModelAttribute를 사용한 화면에 데이터 전송 처리      
	//@RequestParam + model.addAttribute 처럼 동작
	@GetMapping("/test")		 //파라미터 값 
	public void test(@ModelAttribute("age") int age ,Model model) {
			model.addAttribute("nick","야옹이");
			//model.addAttribute("age",age); 는 할 필요 없다 !! 
		
		
	}
	
	@GetMapping("/test2")
	public void test2(@ModelAttribute("info") UserVO vo ) {
		//info라는 이름으로 모델에 담는다는 의미 
		System.out.println("메서드 내의 콘솔 출력 :"+vo);
		
	}
	
	
	//3.ModelAndView 객체를 활용한 처리  : model객체 +뷰 지정 가능 
	// 비동기 통신 처리 할 때 주로 사용
	@GetMapping("/test3")
	public ModelAndView test3() {
		ModelAndView mv=new ModelAndView();
		mv.addObject("userName","김철수");   //addAttribute 한것과 같다
		mv.addObject("userAge", 30);
		mv.setViewName("/response/test3");
		
		return mv;
		
		
	}
	
	/////////////////////////////////////////////////////
	
	
	@GetMapping("/res-quiz01")
	public void resquiz() {}
	

	//커맨드 객체와 Model을 사용한 방식	
	/*
	  @PostMapping("/res-login")
	  public String login(UserVO user,Model model) {
	  	String id=user.getUserId();
	  	String pw=user.getUserPw();
	  
	  	model.addAttribute("userId",id);
	  	
	   if(id.equals("kim123") && pw.equals("1234"))
	    return "response/res-quiz02";
	  
	  return "response/res-quiz03";
	  
	  }
	  
	  ///////////
	    @PostMapping("/res-login")
	    public String reslogin(@ModelAttribute("userId") String id,@RequestParam("userPw" String pw)
	     {
	     if(id.equals("kim1234") && pw.equals("1234"))
	     return "response/res-quiz02";
		
		return "response/res-quiz03";
	  
	  	}
	  
	  */
	
	
	@PostMapping("/res-login")
	public String reslogin(@ModelAttribute("user") UserVO user) {
		if(user.getUserId().equals("kim123") && user.getUserPw().equals("1234"))
			return "response/res-quiz02";
		
		return "response/res-quiz03";
		
	}
	
	////////////////////////////////////////////////////////////////////////////
	
	//Redirect 처리
	
	//폼 화면을 보여주는 메서드 
	@GetMapping("/login")
	public String login() {
		System.out.println("/lgoin : GET 요청 발생!");
		return "response/res-redirect-form";
	}
	
	@PostMapping("/login")
	public String login(@RequestParam("userId") String id,
			@RequestParam("userPw") String pw,
			@RequestParam("userPwChk") String pwChk,
			RedirectAttributes ra) {
			//redirectAttributes 메서드를 활용하면 ,
			//일회성으로 데이터를 화면에 전달할 수 있음 
		
		System.out.println("/login : POST요청 발생!");
		System.out.println("ID :"+id);
		System.out.println("PW :"+pw);
		System.out.println("CHK :"+pwChk);
		
		if(id.equals("")) {
			//redirect 상황에서 model 객체를 사용하게 되면 
			//model 내부의 데이터가 재 요청이 들어올 때 파라미터 값으로 붙어서 들어옵니다.
			//데이터가 url 주소 뒤에 ?와 함께 노출되어 전달이 됩니다.
			//model.addAttribute("msg","아아디는 필수값이에요!");  //매개변수 Model model사용해야함
			
			//redirect 상황에서 일회성으로 데이터를 전송할 때 사용하는 메서드.
			//url 뒤에 데이터가 붙지 않습니다 . 한 번 이용 후에는 알아서 소멸합니다.
			ra.addFlashAttribute("msg", "아아디는 필수값이에요!");
			//정상적으로 p 태그에 msg 붙음  
			
			return "redirect:/response/login";  
			// /response/login URL로  재 요청이 들어감 ,즉 login()메서드 동작 
			
			
			//redirect 처리를 할 때에는 , Model 활용하기가 나쁨 -- URL의 파라미터 값으로 붙기 때문
			
		}else if(!pw.equals(pwChk)){
			ra.addFlashAttribute("msg", "비밀번호 확인란을 체크하세요.");
			return "redirect:/response/login";
		}else {
			return null;
		}
		 
		
	}
	
	
	
	
}
































