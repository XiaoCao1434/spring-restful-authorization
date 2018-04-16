package com.beini.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 公共的controller(首页)
 * @author lb_chen
 *
 */
@Controller
public class BaseController {
	@GetMapping("/index")
	public String index() {
		return "index";
	}
}
