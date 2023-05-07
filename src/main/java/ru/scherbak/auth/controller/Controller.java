package ru.scherbak.auth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class Controller {
    @GetMapping("/")
    public String home() {
        return "Home";
    }

    @GetMapping("/user")
    public String user(Principal principal) {
        return "User " + principal.getName();
    }

    @GetMapping("/admin")
    public String admin() {
        return "Admin";
    }
}
