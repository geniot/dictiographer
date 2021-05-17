package com.dictiographer.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/github")
public class GitHubWebhookController {
    @PostMapping("/update")
    public void update(@RequestBody String json) {
        System.out.println(json);
    }
}
