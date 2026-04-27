package com.club.escalade.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

@Controller
@RequestMapping("/principal")
public class PrincipalController {

    @GetMapping
    @ResponseBody
    public String showPrincipal(Principal principal) {
        return principal.getName();
    }
}
