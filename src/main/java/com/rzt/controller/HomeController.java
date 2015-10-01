package com.rzt.controller;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.rzt.client.Example1;
import com.rzt.client.Jira;
import com.rzt.client.User;

import net.rcarz.jiraclient.JiraException;

@Controller
public class HomeController {

	@RequestMapping(value = "/")
	public ModelAndView home(HttpServletResponse response)
			throws IOException, JiraException, InterruptedException, ExecutionException {
		System.out.println("Home");
		ModelAndView mv = new ModelAndView("home");
		return mv;
	}

	@RequestMapping(value = "/login")
	public ModelAndView login(@RequestBody User userObject)
			throws IOException, JiraException, InterruptedException, ExecutionException {
		System.out.println("Login");
		ModelAndView mv = new ModelAndView("login");
		Example1 e = new Example1(userObject);
		mv.addObject("user", userObject);
		mv.addObject("allSprint", e.getAllSprints());
		mv.addObject("allProject", e.getAllProjects());
		return mv;
	}

	@RequestMapping(value = "/list")
	public ModelAndView list(@RequestBody Jira jiraObject)
			throws IOException, JiraException, InterruptedException, ExecutionException {
		System.out.println("List");
		Example1 e = new Example1(jiraObject);
		ModelAndView mv = new ModelAndView("list");
		mv.addObject("allIssues", e.getAllIssues());
		return mv;
	}
}
