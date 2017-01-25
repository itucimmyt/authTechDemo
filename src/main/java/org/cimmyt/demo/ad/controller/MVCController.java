package org.cimmyt.demo.ad.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path="/path")
public class MVCController {

	@RequestMapping(path="/hello")
	@ResponseBody
	public String getSomeData(@RequestParam(defaultValue="Mr. X") String name
			,Authentication auth) {
		
		return "<h1>Hello</h1><h2>Whow are you, "+name+"?</h2>"+
		 "<p>Programatically getting your user data: </p>"+
				"<ul>"+
				"<li>"+auth.getName()+"</li>"+
				"<li>"+auth.getPrincipal()+"</li>"+
				"<li>"+auth.getDetails()+"</li>"+
				"<li>"+auth.getCredentials()+"</li>"+
				"</ul>";
	}
}
