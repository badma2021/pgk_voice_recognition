package com.example.pgk.controller;

import com.example.pgk.service.PartService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class PartController {
    private static final Logger logger = LoggerFactory.getLogger(PartController.class);
    private final PartService userService;
}
