package com.sqc.academy;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Lession1 {
	@RequestMapping("/greeting")
	public String hello(@RequestParam(defaultValue = "") String name) {
		//return "Hello World" + name;
		return String.format("Hello %s", name);
	}
}
