package org.sis.ancmessaging.web;

import java.util.Collection;

import org.sis.ancmessaging.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
	@Autowired
	private AccountService accountService;
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String getLoginPage(@RequestParam(value = "error", required = false) boolean error, Model model) {
		if (error) {
			model.addAttribute("error", "Invalid username or password!!");
		} else {
			model.addAttribute("error", "");
		}
		return "login";
	}
	
	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public String adminIndex() {
		return "adminindex";
	}
	
	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public String userIndex(Model model) {
		String username = accountService.getCurrentUsername();
		model.addAttribute("username", username);
		return "redirect:/user/mother";
	}
	
	@RequestMapping(value = "/welcome", method = RequestMethod.GET)
	public String printWelcome(Model model) {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.addAttribute("username", user.getUsername());
		Collection<GrantedAuthority> roles = user.getAuthorities();
		Object [] rolesArray = roles.toArray();
		String role = ((GrantedAuthority)rolesArray[0]).getAuthority();
		if (role.equals("ROLE_ADMIN")) {
			return "adminindex";
		} else {
			return "index";
		}
	}
	
	@RequestMapping(value = "/loginerror", method = RequestMethod.GET)
	public String showError(Model model) {
		model.addAttribute("error", true);
		return "login";
	}
	
}
