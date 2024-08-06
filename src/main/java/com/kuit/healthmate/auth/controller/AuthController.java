package com.kuit.healthmate.auth.controller;

import com.kuit.healthmate.global.response.ApiResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/login")
public class AuthController {

    @GetMapping("/{registrationId}")
    public String redirectPage(@PathVariable String registrationId) {
        return "redirect:http://localhost:9000/oauth2/authorization/" + registrationId;
    }

    @ResponseBody
    @GetMapping("/success")
    public ApiResponse<String> loginSuccess(@RequestParam String jwt) {
        return new ApiResponse<>(jwt);
    }
}
