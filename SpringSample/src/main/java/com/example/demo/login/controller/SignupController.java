package com.example.demo.login.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.login.domain.model.SignupForm;

@Controller
public class SignupController {

	//ラジオボタン
	private Map<String, String> radioMarriage;

	//ラジオボタンの初期化メソッド
	private Map<String, String> initRadioMarriage() {
		Map<String, String> radio = new LinkedHashMap<>();
		radio.put("既婚", "true");
		radio.put("未婚", "false");
		return radio;
	}

	@GetMapping("/signup")
	public String getSignUp(@ModelAttribute SignupForm form,
			Model model) {
		radioMarriage = initRadioMarriage();
		model.addAttribute("radioMarriage", radioMarriage);
		return "login/signup";
	}

	@PostMapping("/signup")
	public String postSignUp(@ModelAttribute SignupForm form,
			BindingResult bindingResult, Model model) {
		//データバインド失敗の場合
		//入力チェックに引っかかった場合
		if (bindingResult.hasErrors()) {
			return getSignUp(form, model);
		}
		System.out.println(form);
		return "redirect:/login";
	}
}
