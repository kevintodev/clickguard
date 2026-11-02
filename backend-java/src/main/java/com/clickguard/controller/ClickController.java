package com.clickguard.controller;

import com.clickguard.dto.ClickRequest;
import com.clickguard.model.Click;
import com.clickguard.service.ClickService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/clicks")
@CrossOrigin(origins = "http://localhost:5173")
public class ClickController {

    private final ClickService service;

    public ClickController(ClickService service) {
        this.service = service;
    }

    @PostMapping
    public Click receive(@Valid @RequestBody ClickRequest request) {
        return service.processClick(request);
    }

    @GetMapping
    public List<Click> recent() {
        return service.getRecent();
    }
}