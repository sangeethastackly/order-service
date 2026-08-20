package order_service.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConfigController {

    @Value("${app.message}")
    private String message;

    @Value("${app.environment}")
    private String environment;

    @GetMapping("/config/message")
    public String getConfigMessage() {
        return message + " - " + environment;
    }
}