package pl.jstk.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class MyErrorController implements ErrorController {

	@RequestMapping("/error")
	public ModelAndView handleError(HttpServletRequest request, ModelAndView modelAndView) {
		modelAndView = new ModelAndView("403");
		Object statusCode = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
		if (Integer.valueOf(statusCode.toString()) == HttpStatus.FORBIDDEN.value()) {
			modelAndView.addObject("error", "Sorry You don't have access to this page!!");
			return modelAndView;
		}
		if (Integer.valueOf(statusCode.toString()) == HttpStatus.NOT_FOUND.value()) {
			modelAndView.addObject("error", "Sorry this page does not exist!");
			return modelAndView;
		}

		modelAndView.addObject("error", Integer.valueOf(statusCode.toString()));
		return modelAndView;
	}

	public String getErrorPath() {
		return "/error";
	}
}
