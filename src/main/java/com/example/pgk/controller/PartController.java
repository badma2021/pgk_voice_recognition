package com.example.pgk.controller;

import com.example.pgk.model.dto.PartDTO;
import com.example.pgk.model.entity.Part;
import com.example.pgk.model.entity.User;
import com.example.pgk.service.PartService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/part")
public class PartController {
    private static final Logger logger = LoggerFactory.getLogger(PartController.class);
    private final PartService partService;

    @GetMapping("/getall")
    public ResponseEntity<PartDTO[]> getAllParts() {
        return new ResponseEntity<>(partService.getAllParts(), HttpStatus.OK);
    }

    @GetMapping("/getallbyyear")
    public ResponseEntity<PartDTO[]> getAllPartsByYear(@RequestParam(name = "year") int year) {
        return new ResponseEntity<>(partService.getAllPartsAndProductionYear(year), HttpStatus.OK);
    }
}
