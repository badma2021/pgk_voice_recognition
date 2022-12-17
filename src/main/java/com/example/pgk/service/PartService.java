package com.example.pgk.service;

import com.example.pgk.dao.PartRepository;
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
    private final DtoUtils dtoUtils;

    public PartService(PartRepository partRepository, DtoUtils dtoUtils) {
        this.partRepository = partRepository;
        this.dtoUtils = dtoUtils;
    }

    public List<Part> getAllParts() {
        return partRepository.findAll();
    }
    public PartDTO[] getAllPartsAndProductionYear(int year) {
        List<Part>parts=partRepository.findAllByYear(year);
        final PartDTO[] partDTOs = new PartDTO[parts.size()];
        int indexPart = 0;
        for(Part p : parts){
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
}
