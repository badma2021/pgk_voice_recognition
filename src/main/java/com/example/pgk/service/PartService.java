package com.example.pgk.service;

import com.example.pgk.dao.PartRepository;
import com.example.pgk.dao.UserRepository;
import com.example.pgk.model.entity.Part;
import com.example.pgk.model.entity.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Service
public class PartService {
    @Resource
    private PartRepository partRepository;


    public List<Part> getAllParts() {
        return partRepository.findAll();
    }
    public List<Part> getAllPartsAndProductionYear(int year) {
        return partRepository.findAllByYear(year);
    }
}
