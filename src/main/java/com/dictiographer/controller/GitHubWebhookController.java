package com.dictiographer.controller;

import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/github")
public class GitHubWebhookController {
    @PostMapping("/update")
    public void update(@RequestBody String json) {
        try {
            Runtime.getRuntime().exec("sudo systemctl restart dictiographer.service");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/welcome")
    public String welcome() {
        return "welcome!";
    }
}
