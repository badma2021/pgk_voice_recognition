package com.example.pgk.service;


import com.example.pgk.dao.ConfirmationTokenRepository;
import com.example.pgk.dao.RoleRepositoryJpql;
import com.example.pgk.dao.UserRepositoryJpql;
import com.example.pgk.email.EmailSender;
import com.example.pgk.exception.IncorrectJwtTokenException;
import com.example.pgk.exception.NotValidRequestException;
import com.example.pgk.exception.UserAlreadyExistRunTimeException;
import com.example.pgk.exception.UserNotFoundException;
import com.example.pgk.model.dto.JwtDTO;
import com.example.pgk.model.dto.UserDTO;
import com.example.pgk.model.entity.ConfirmationToken;
import com.example.pgk.model.entity.Role;
import com.example.pgk.model.entity.User;
import com.example.pgk.repository.RedisRepository;
import com.example.pgk.security.JwtUtils;
import com.example.pgk.utils.DtoUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
@Slf4j
public class AuthService {
    private final UserRepositoryJpql userRepositoryJpql;
    private final PasswordEncoder encoder;
    private final JwtUtils jwtUtils;
    private final DtoUtils dtoUtils;
    private final RedisRepository redisRepository;
    private final RoleRepositoryJpql roleRepositoryJpql;
    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final EmailSender emailSender;

    public AuthService(UserRepositoryJpql userRepositoryJpql, PasswordEncoder encoder,
                       JwtUtils jwtUtils, DtoUtils dtoUtils, RedisRepository redisRepository, RoleRepositoryJpql roleRepositoryJpql, ConfirmationTokenRepository confirmationTokenRepository, EmailSender emailSender) {
        this.userRepositoryJpql = userRepositoryJpql;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
        this.dtoUtils = dtoUtils;
        this.redisRepository = redisRepository;
        this.roleRepositoryJpql = roleRepositoryJpql;
        this.confirmationTokenRepository = confirmationTokenRepository;
        this.emailSender = emailSender;
    }

    public JwtDTO loginAndValidateUser(final UserDTO userDTO, HttpServletResponse response) {
        if (userDTO.getEmail() == null || userDTO.getPassword() == null) {
            System.out.println("login");
            throw new NotValidRequestException();
        }
        return loginUser(userDTO);
    }

    private JwtDTO loginUser(final UserDTO userDTO) {
        //TODO set refresh to cookie
        final User user = userRepositoryJpql.getUserAndRolesByEmail(userDTO.getEmail());
        if (user == null) {
            log.info("email not found");
            throw new UserNotFoundException("Not correct email or password");
        }
        if (!encoder.matches(userDTO.getPassword(), user.getPassword())) {
            log.info("password not same");
            throw new UserNotFoundException("Not correct email or password");
        }
        userDTO.setId(Integer.parseInt(String.valueOf(user.getId())));
        userDTO.setRoles(dtoUtils.RoleToRoleDTOArray(user.getRoles()));
        final String refreshToken = jwtUtils.generateRefreshToken(userDTO);
        System.out.println(refreshToken);
        redisRepository.save(user.getEmail(), String.valueOf(refreshToken.hashCode()));
        return new JwtDTO(jwtUtils.generateAccessToken(userDTO), refreshToken);
    }

    public JwtDTO updateTokensAndValid(final String refresh) {
        //TODO get refresh from cookie
        if (refresh == null) {
            throw new IncorrectJwtTokenException();
        }
        if (!jwtUtils.validateRefreshToken(refresh)) {
            throw new IncorrectJwtTokenException();
        }
        return updateTokens(refresh);
    }

    private JwtDTO updateTokens(final String refresh) {
        final UserDTO userDTO = jwtUtils.parseRefreshToken(refresh);
        final String hashToken = String.valueOf(refresh.hashCode());
        final String tokenRedis = redisRepository.getValue(userDTO.getEmail());

        if (hashToken.equals(tokenRedis)) {
            final String newRefreshToken = jwtUtils.generateRefreshToken(userDTO);
            redisRepository.save(userDTO.getEmail(), String.valueOf(newRefreshToken.hashCode()));
            final String newAccessToken = jwtUtils.generateAccessToken(userDTO);
            return new JwtDTO(newAccessToken, newRefreshToken);
        }
        throw new IncorrectJwtTokenException();
    }

    public String registerUserAndValid(final UserDTO userDTO) {
        if (userDTO == null) {
            throw new NotValidRequestException();
        }
        if (userDTO.getEmail() == null || userDTO.getPassword() == null) {
            throw new NotValidRequestException();
        }
        return registerUser(userDTO);
    }

    private String registerUser(final UserDTO userDTO) {
        final User user = userRepositoryJpql.getUserAndRolesByEmail(userDTO.getEmail());
        if (user != null) {
            throw new UserAlreadyExistRunTimeException();
        }
        Role role = roleRepositoryJpql.getRoleByName("ROLE_USER");
        if (role == null) {
            Role role1 = new Role("ROLE_USER");
            roleRepositoryJpql.save(role1);
            role = role1;
        }
        final Set<Role> roleSet = new HashSet<>();
        roleSet.add(role);
        final User user1 = new User(userDTO.getEmail(), encoder.encode(userDTO.getPassword()), roleSet);

        userRepositoryJpql.save(user1);

        String token = UUID.randomUUID().toString();
        //TODO: Send confirmation token
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                user1
        );
        confirmationTokenRepository.save(confirmationToken);
        String link = "http://localhost:8888/api/v1/register/confirm/?token=" + token;
        emailSender.send(
                userDTO.getEmail(),
                buildEmail(userDTO.getFirstName(), link) );
        return "User Save";
    }

    @Transactional
    public String confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenRepository.findByToken(token)
                .orElseThrow(() ->
                        new IllegalStateException("token not found"));

        if (confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("email already confirmed");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiredAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("token expired");
        }

        confirmationTokenRepository.updateConfirmedAt(
                token, LocalDateTime.now());
        userRepositoryJpql.enableUser(
                confirmationToken.getUser().getEmail());
        return "confirmed";
    }
    private String buildEmail(String name, String link) {
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "\n" +
                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "        \n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td style=\"padding-left:10px\">\n" +
                "                  \n" +
                "                    </td>\n" +
                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Confirm your email</span>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "              </a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "      <td>\n" +
                "        \n" +
                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                "        \n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + name + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Thank you for registering. Please click on the below link to activate your account: </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\"" + link + "\">Activate Now</a> </p></blockquote>\n Link will expire in 15 minutes. <p>See you soon</p>" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "\n" +
                "</div></div>";
    }
}
