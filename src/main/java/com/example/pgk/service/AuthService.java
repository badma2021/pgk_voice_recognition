package com.example.pgk.service;


import com.example.pgk.dao.RoleRepositoryJpql;
import com.example.pgk.dao.UserRepositoryJpql;
import com.example.pgk.exception.IncorrectJwtTokenException;
import com.example.pgk.exception.NotValidRequestException;
import com.example.pgk.exception.UserAlreadyExistRunTimeException;
import com.example.pgk.exception.UserNotFoundException;
import com.example.pgk.model.dto.JwtDTO;
import com.example.pgk.model.dto.UserDTO;
import com.example.pgk.model.entity.Role;
import com.example.pgk.model.entity.User;
import com.example.pgk.repository.RedisRepository;
import com.example.pgk.security.JwtUtils;
import com.example.pgk.utils.DtoUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.Set;

@Service
@Slf4j
public class AuthService {
    private final UserRepositoryJpql userRepositoryJpql;
    private final PasswordEncoder encoder;
    private final JwtUtils jwtUtils;
    private final DtoUtils dtoUtils;
    private final RedisRepository redisRepository;
    private final RoleRepositoryJpql roleRepositoryJpql;

    public AuthService(UserRepositoryJpql userRepositoryJpql, PasswordEncoder encoder,
                       JwtUtils jwtUtils, DtoUtils dtoUtils, RedisRepository redisRepository, RoleRepositoryJpql roleRepositoryJpql) {
        this.userRepositoryJpql = userRepositoryJpql;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
        this.dtoUtils = dtoUtils;
        this.redisRepository = redisRepository;
        this.roleRepositoryJpql = roleRepositoryJpql;
    }

    public JwtDTO loginAndValidateUser(final UserDTO userDTO, HttpServletResponse response){
        if(userDTO.getEmail() == null || userDTO.getPassword() == null){
            System.out.println("login");
            throw new NotValidRequestException();
        }
        return loginUser(userDTO);
    }
    private JwtDTO loginUser(final UserDTO userDTO){
        //TODO set refresh to cookie
        final User user = userRepositoryJpql.getUserAndRolesByEmail(userDTO.getEmail());
        if(user == null){
            log.info("email not found");
            throw new UserNotFoundException("Not correct email or password");
        }
        if(!encoder.matches(userDTO.getPassword(), user.getPassword())){
            log.info("password not same");
            throw new UserNotFoundException("Not correct email or password");
        }
        userDTO.setId(Integer.parseInt(String.valueOf(user.getId())));
        userDTO.setRoles(dtoUtils.RoleToRoleDTOArray(user.getRoles()));
        final String refreshToken = jwtUtils.generateRefreshToken(userDTO);
        System.out.println(userDTO);
        redisRepository.save(user.getEmail(),String.valueOf(refreshToken.hashCode()));
        return new JwtDTO(jwtUtils.generateAccessToken(userDTO), refreshToken);
    }
    public JwtDTO updateTokensAndValid(final String refresh){
        //TODO get refresh from cookie
        if(refresh == null){
            throw new IncorrectJwtTokenException();
        }
        if(!jwtUtils.validateRefreshToken(refresh)){
            throw new IncorrectJwtTokenException();
        }
        return updateTokens(refresh);
    }
    private JwtDTO updateTokens(final String refresh){
        final UserDTO userDTO = jwtUtils.parseRefreshToken(refresh);
        final String hashToken = String.valueOf(refresh.hashCode());
        final String tokenRedis = redisRepository.getValue(userDTO.getEmail());

        if(hashToken.equals(tokenRedis)){
            final String newRefreshToken = jwtUtils.generateRefreshToken(userDTO);
            redisRepository.save(userDTO.getEmail(), String.valueOf(newRefreshToken.hashCode()));
            final String newAccessToken = jwtUtils.generateAccessToken(userDTO);
            return new JwtDTO(newAccessToken,newRefreshToken);
        }
        throw new IncorrectJwtTokenException();
    }
    public String registerUserAndValid(final UserDTO userDTO){
        if(userDTO == null){
            throw new NotValidRequestException();
        }
        if(userDTO.getEmail() == null || userDTO.getPassword() == null){
            throw new NotValidRequestException();
        }
        return registerUser(userDTO);
    }
    private String registerUser(final UserDTO userDTO){
        final User user = userRepositoryJpql.getUserAndRolesByEmail(userDTO.getEmail());
        if(user != null){
            throw new UserAlreadyExistRunTimeException();
        }
        Role role = roleRepositoryJpql.getRoleByName("ROLE_USER");
        if(role == null){
            Role role1 = new Role("ROLE_USER");
            roleRepositoryJpql.save(role1);
            role = role1;
        }
        final Set<Role> roleSet = new HashSet<>();
        roleSet.add(role);
        final User user1 = new User(userDTO.getEmail(), encoder.encode(userDTO.getPassword()), roleSet);
        userRepositoryJpql.save(user1);
        return "User Save";
    }
}
