package com.apap.tutorial8.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tutorial8.model.UserRoleModel;
import com.apap.tutorial8.service.UserRoleService;

@Controller
@RequestMapping("/user")
public class UserRoleController {
	@Autowired
	private UserRoleService userService;
	
	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
	private String addUserSubmit(@ModelAttribute UserRoleModel user, Model model) {
		System.out.println(user.getPassword());
		System.out.println(userService.checkIfPasswordValid(user.getPassword()));
		if(userService.checkIfPasswordValid(user.getPassword())) {
			userService.addUser(user);
			model.addAttribute("message", "User Ditambah");
		}
		else {
			model.addAttribute("message", "Password Tidak Memenuhi Ketentuan");
		}
		return "home";
	}
	
	@RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
	private String updatePass(Model model, Principal principal, @RequestParam("oldPass") String oldPass, @RequestParam("newPass") String newPass, @RequestParam("newPassConfirm") String newPassConfirm) {
		String uname = principal.getName();
		UserRoleModel user = userService.findByUsername(uname);
		System.out.println(user.getPassword());
		System.out.println(oldPass);
		if(userService.checkIfValidOldPassword(user, oldPass)) {
			System.out.println("SAMA");
			if(newPass.equals(newPassConfirm)) {
				if(userService.checkIfPasswordValid(newPass)) {
					userService.updatePassword(uname, newPass);
					model.addAttribute("message", "Password Berhasil Diupdate");
				}
				
				else {
					model.addAttribute("message", "Password Tidak Memenuhi Ketentuan");
				}
				
			}
			else {
				System.out.println("PASSWORD BARU BEDA");
				model.addAttribute("message", "Password Baru Beda");
			}
		}
		
		else {
			System.out.println("BEDA");
			model.addAttribute("message", "Password Lama Salah");
		}
		
		return "home";
	}
	
}
