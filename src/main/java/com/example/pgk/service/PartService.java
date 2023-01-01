package com.example.pgk.service;

import com.example.pgk.dao.PartRepository;
import com.example.pgk.dao.UserRepository;
import com.example.pgk.model.dto.PartDTO;
import com.example.pgk.model.entity.Part;
import com.example.pgk.utils.DtoUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class PartService {
    private static final Logger logger = LoggerFactory.getLogger(PartService.class);
    @Resource
    private PartRepository partRepository;
    @Resource
    private UserRepository userRepository;
    private final DtoUtils dtoUtils;
    @Value("${upload.path}")
    private String uploadPath;

    public PartService(PartRepository partRepository, DtoUtils dtoUtils) {
        this.partRepository = partRepository;
        this.dtoUtils = dtoUtils;
    }

    public PartDTO[] getAllParts() {
        List<Part> parts = partRepository.findAll();
        final PartDTO[] partDTOs = new PartDTO[parts.size()];
        int indexPart = 0;
        for (Part p : parts) {
            final PartDTO p1 = new PartDTO();
            p1.setPartNumber(p.getPartNumber());
            p1.setProductionYear(p.getProductionYear());
            p1.setPartName(p.getPartName());
            p1.setAudioRecordName(p.getAudioRecordName());
            p1.setCreatedAt(p.getCreatedAt());
            p1.setComment(p.getComment());
            p1.setUserId(p.getId());
            p1.setFactoryNumber(p.getFactoryNumber());
            partDTOs[indexPart] = p1;
            indexPart++;
        }
        return partDTOs;
    }

    public PartDTO[] getAllPartsByYear(int year) {
        List<Part> parts = partRepository.findAllByYear(year);
        final PartDTO[] partDTOs = new PartDTO[parts.size()];
        int indexPart = 0;
        for (Part p : parts) {
            final PartDTO p1 = new PartDTO();
            p1.setPartNumber(p.getPartNumber());
            p1.setProductionYear(p.getProductionYear());
            p1.setPartName(p.getPartName());
            p1.setAudioRecordName(p.getAudioRecordName());
            p1.setCreatedAt(p.getCreatedAt());
            p1.setComment(p.getComment());
            p1.setUserId(p.getId());
            p1.setFactoryNumber(p.getFactoryNumber());
            partDTOs[indexPart] = p1;
            indexPart++;
        }
        return partDTOs;
    }

    public String createPart(PartDTO[] partDtos, String filename, LocalDateTime date, Long userId) {
        List<Part> parts= IntStream
                .rangeClosed(0, partDtos.length - 1)
                .mapToObj(j -> new Part(
                        partDtos[j].getPartName(),
                        partDtos[j].getPartNumber(),
                        partDtos[j].getProductionYear(),
                        partDtos[j].getFactoryNumber(),
                        partDtos[j].getComment(),
                        userId+"_"+date+"_"+filename,
                        date,
                        userRepository.getById(userId)
                ))
                .collect(Collectors.toList());
        logger.info("before saveAll");
        partRepository.saveAllAndFlush(parts);
        return "запись учтена";
    }
    @PostConstruct
    public void init() {
        try {
            Files.createDirectories(Paths.get(uploadPath));
        } catch (IOException e) {
            throw new RuntimeException("Could not create upload folder!");
        }
    }

    public void save(MultipartFile file, LocalDateTime date, Long userId) {
        try {
            Path root = Paths.get(uploadPath);
            if (!Files.exists(root)) {
                init();
            }
            Files.copy(file.getInputStream(), root.resolve(userId+"_"+date+"_"+file.getOriginalFilename()));
        } catch (Exception e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
    }
}
