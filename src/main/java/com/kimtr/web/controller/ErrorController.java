package com.kimtr.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ErrorController {

	@GetMapping("/code_401")
    public String forbidden() {
        return "/error/code_401";
    }
//	@PostMapping("/forbidden")
//    public String forbiddenpost() {
//        return "/error/Forbidden";
//    }
}
