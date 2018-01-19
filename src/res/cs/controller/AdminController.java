package res.cs.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AdminController {
	@RequestMapping(value="/Account", method=RequestMethod.GET)
	public ModelAndView displayHomePage(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("AdminAccountInfo");
		// mav.addObject("fuego", "jjj");//this is going to the request
		return mav;
	}
}
