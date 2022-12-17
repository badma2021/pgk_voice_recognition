package com.example.pgk.service;

import com.example.pgk.dao.PartRepository;
import com.example.pgk.dao.UserRepository;
import com.example.pgk.model.dto.PartDTO;
import com.example.pgk.model.dto.RoleDTO;
import com.example.pgk.model.entity.Part;
import com.example.pgk.utils.DtoUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PartService {
    @Resource
    private PartRepository partRepository;
    @Resource
    private UserRepository userRepository;
    private final DtoUtils dtoUtils;

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
            p1.setAudioRecordPath(p.getAudioRecordPath());
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
            p1.setAudioRecordPath(p.getAudioRecordPath());
            p1.setCreatedAt(p.getCreatedAt());
            p1.setComment(p.getComment());
            p1.setUserId(p.getId());
            p1.setFactoryNumber(p.getFactoryNumber());
            partDTOs[indexPart] = p1;
            indexPart++;
        }
        return partDTOs;
    }

    public String createPart(PartDTO partDto) {

        Part part = dtoUtils.partDtoToEntity(partDto);
        part.setUser(userRepository.getById(partDto.getUserId()));
        partRepository.save(part);
        return "деталь учтена";
    }
}
