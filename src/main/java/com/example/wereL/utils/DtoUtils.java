package com.example.wereL.utils;

import com.example.wereL.exception.NotValidRequestException;
import com.example.wereL.model.dto.PartDTO;
import com.example.wereL.model.dto.RoleDTO;
import com.example.wereL.model.dto.UserDTO;
import com.example.wereL.model.entity.Role;
import com.example.wereL.model.entity.Part;
import com.example.wereL.model.entity.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class DtoUtils {
    public RoleDTO[] roleToRoleDTOArray(final Set<Role> roles) {
        if (roles == null) {
            System.out.println("dto");
            throw new NotValidRequestException();
        }
        final RoleDTO[] roleDTOS = new RoleDTO[roles.size()];
        int index = 0;
        for (Role r : roles) {
            roleDTOS[index] = new RoleDTO(r.getName());
            index++;
        }
        return roleDTOS;
    }

    public Part partDtoToEntity(PartDTO partDto) {
        Part part = new Part();
        part.setPartNumber(partDto.getPartNumber());
        part.setProductionYear(partDto.getProductionYear());
        part.setPartName(partDto.getPartName());
        part.setAudioRecordName(partDto.getAudioRecordName());
        part.setCreatedAt(partDto.getCreatedAt());
        part.setComment(partDto.getComment());
        part.setFactoryNumber(partDto.getFactoryNumber());
        return part;
    }

    public User userDtoToEntity(UserDTO userDto) {
        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setBirthday(userDto.getBirthday());
        user.setPassword(userDto.getPassword());
        Set<Role> roles = new HashSet<>();
        final RoleDTO[] roleDTOS = userDto.getRoles();
        int index = 0;
        for (RoleDTO r : roleDTOS) {
            roles.add(new Role(r.getName()));
            index++;
        }
        user.setRoles(roles);
        return user;
    }
    public List<String> roleSetToRoleList(final Set<Role> roles) {
        if (roles == null) {
            System.out.println("Lito");
            throw new NotValidRequestException();
        }
        int n = roles.size();
        final List<String> roleList = new ArrayList<>(n);
        for (Role x : roles)
            roleList.add(x.getName());
        return roleList;
        }

}
