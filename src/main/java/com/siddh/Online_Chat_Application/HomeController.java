package com.siddh.Online_Chat_Application;
 // Use your actual package name

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String welcome() {
        return "Welcome to Online Chat Application!";
    }
}

