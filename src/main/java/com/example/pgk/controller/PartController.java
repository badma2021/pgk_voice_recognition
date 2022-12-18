package com.example.pgk.controller;

import com.example.pgk.model.dto.PartDTO;
import com.example.pgk.model.entity.Part;
import com.example.pgk.model.entity.User;
import com.example.pgk.service.PartService;
import com.example.pgk.utils.GenerateCSVReport;
import org.springframework.http.HttpHeaders;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import com.example.pgk.utils.GenerateExcelReport;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
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
    public ResponseEntity<String> create(@RequestPart("partDto") PartDTO[] partDtos,@RequestParam("file") MultipartFile file){
        String fileName=null;
        LocalDateTime date=LocalDateTime.now().withNano(0);
        Long userId=partDtos[1].getUserId();
        try {

            partService.save(file, date, userId);
            fileName= file.getOriginalFilename();

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
        return new ResponseEntity<>(partService.createPart(partDtos, fileName, date, userId), HttpStatus.OK);
    }


    @GetMapping(value = "/getalltoexcel")
    public ResponseEntity<InputStreamResource> excelCustomersReport() throws IOException {
        PartDTO[] users = partService.getAllParts();
        ByteArrayInputStream in = GenerateExcelReport.usersToExcel(users);
        // return IO ByteArray(in);
        HttpHeaders headers = new HttpHeaders();
        // set filename in header
        headers.add("Content-Disposition", "attachment; filename=parts.xlsx");
        return ResponseEntity.ok().headers(headers).body(new InputStreamResource(in));
    }

    @GetMapping(value = "/getalltocsv")
    public void csvUsers(HttpServletResponse response) throws IOException {
        PartDTO[] parts = partService.getAllParts();
        GenerateCSVReport.writeUsers(response.getWriter(), parts);
        response.setHeader("Content-Disposition", "attachment; filename=AllUsersCSVReport.csv");
    }
//    @PostMapping(value = "/create")
//    public ResponseEntity<String> create(@RequestBody PartDTO partDto, Principal principal){
//        return new ResponseEntity<>(partService.createPart(partDto), HttpStatus.OK);
//    }
}
