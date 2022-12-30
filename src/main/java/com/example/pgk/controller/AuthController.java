package com.example.pgk.controller;

import com.example.pgk.exception.IncorrectJwtTokenException;
import com.example.pgk.model.dto.JwtDTO;
import com.example.pgk.model.dto.UserDTO;
import com.example.pgk.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

@RestController
@RequestMapping(value = "/api/v1")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(value = "/login")
    public ResponseEntity<JwtDTO> login(@RequestBody UserDTO userDTO, HttpServletResponse response){
        final JwtDTO jwtDTO = authService.loginAndValidateUser(userDTO,response);
        final Cookie cookie = new Cookie("token", jwtDTO.getRefreshToken());
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        response.addCookie(cookie);
        //jwtDTO.setRefreshToken(null);
        return new ResponseEntity<>(jwtDTO, HttpStatus.OK);
    }
    @PostMapping(value = "/register")
    public ResponseEntity<String> register(@RequestBody UserDTO userDTO){
        return new ResponseEntity<>(authService.registerUserAndValid(userDTO),HttpStatus.OK);
    }

    @GetMapping(path = "register/confirm")
    public String confirm(@RequestParam("token") String token) {
        return authService.confirmToken(token);
    }
    @PostMapping(value = "/gettokens")
    public ResponseEntity<JwtDTO> getTokens(HttpServletRequest request){
        final Cookie[] cookie = request.getCookies();
        for(Cookie c : cookie){
            if(c.getName().equals("token")){
                return new ResponseEntity<>(authService.updateTokensAndValid(c.getValue()),HttpStatus.OK);
            }
        }
        throw new IncorrectJwtTokenException();
    }
    @PostMapping(value = "/test/admin")
    public ResponseEntity<String> admin(Principal principal){
        return new ResponseEntity<>(principal.getName(),HttpStatus.OK);
    }
    @PostMapping(value = "/test")
    public ResponseEntity<String> test(Principal principal){
        return new ResponseEntity<>(principal.getName(),HttpStatus.OK);
    }
}
