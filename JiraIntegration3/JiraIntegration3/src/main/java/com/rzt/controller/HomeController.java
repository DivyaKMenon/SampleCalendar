package com.rzt.controller;

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.rzt.client.Example1;

@Controller
public class HomeController {

	@RequestMapping( value = "/" )
	public ModelAndView test( HttpServletResponse response ) throws IOException
	{

		System.out.println("home");
		Example1 e = new Example1();
		return new ModelAndView("home");
	}
}
