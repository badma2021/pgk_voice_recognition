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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
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
        return new ResponseEntity<>(partService.getAllPartsByYear(year), HttpStatus.OK);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<String> create(@RequestPart("partDto") PartDTO partDto,@RequestParam("file") MultipartFile file){
        try {
            partService.save(file);
            partDto.setAudioRecordPath(file.getOriginalFilename());

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
        return new ResponseEntity<>(partService.createPart(partDto), HttpStatus.OK);
    }

//    @PostMapping(value = "/create")
//    public ResponseEntity<String> create(@RequestBody PartDTO partDto, Principal principal){
//        return new ResponseEntity<>(partService.createPart(partDto), HttpStatus.OK);
//    }
}
