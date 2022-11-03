package com.konai.hsyang.konatoybe.login.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class JoinController {

    @GetMapping("/joinForm")
    public String join(){

        return "joinForm";
    }
}
