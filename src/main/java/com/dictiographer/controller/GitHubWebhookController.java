package com.dictiographer.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/github")
public class GitHubWebhookController {
    @PostMapping("/update")
    public void update(@RequestBody String json) {
        System.out.println(json);
    }

    @GetMapping("/welcome")
    public void welcome() {
        System.out.println("Welcome");
    }
}
