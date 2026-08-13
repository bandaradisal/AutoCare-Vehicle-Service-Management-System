package com.autocare.autocare.controller;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AccessDeniedController {

    @GetMapping("/access-denied")
    public String accessDenied(HttpServletResponse response) {

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);

        return "error/403";
    }
}