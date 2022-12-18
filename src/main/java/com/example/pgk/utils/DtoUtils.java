package com.example.pgk.utils;

import com.example.pgk.exception.NotValidRequestException;
import com.example.pgk.model.dto.PartDTO;
import com.example.pgk.model.dto.RoleDTO;
import com.example.pgk.model.entity.Role;
import com.example.pgk.model.entity.Part;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class DtoUtils {
    public RoleDTO[] RoleToRoleDTOArray(final Set<Role> roles){
        if(roles == null){
            System.out.println("dto");
            throw new NotValidRequestException();
        }
        final RoleDTO[] roleDTOS = new RoleDTO[roles.size()];
        int index = 0;
        for(Role r : roles){
            roleDTOS[index] = new RoleDTO(r.getName());
            index++;
        }
        return roleDTOS;
    }

    public Part partDtoToEntity(PartDTO partDto){
        Part part=new Part();
        part.setPartNumber(partDto.getPartNumber());
        part.setProductionYear(partDto.getProductionYear());
        part.setPartName(partDto.getPartName());
        part.setAudioRecordName(partDto.getAudioRecordName());
        part.setCreatedAt(partDto.getCreatedAt());
        part.setComment(partDto.getComment());
        part.setFactoryNumber(partDto.getFactoryNumber());
        return part;
    }


}
