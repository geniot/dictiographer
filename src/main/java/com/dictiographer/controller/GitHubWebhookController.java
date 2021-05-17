package com.dictiographer.controller;

import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/github")
public class GitHubWebhookController {
    @PostMapping("/update")
    public void update(@RequestBody String json) {
        try {
            // otherwise verify using hash
            // see https://docs.github.com/en/developers/webhooks-and-events/securing-your-webhooks
            if (json.contains("https://github.com/geniot/")){
                Runtime.getRuntime().exec("sudo systemctl restart dictiographer.service");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/welcome")
    public String welcome() {
        return "welcome redeployment 2222";
    }
}
