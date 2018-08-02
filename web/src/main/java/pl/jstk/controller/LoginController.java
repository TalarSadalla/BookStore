package pl.jstk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import pl.jstk.service.UserService;
import pl.jstk.to.UserTo;

@Controller
public class LoginController {

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	UserService userService;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView userLogin() {
		ModelAndView modelAndView = new ModelAndView("login");
		return modelAndView;
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView userLogin(@ModelAttribute UserTo userTo) {
		ModelAndView modelAndView = new ModelAndView("welcome");
		modelAndView.setViewName("redirect:/");
		return modelAndView;
	}
}
