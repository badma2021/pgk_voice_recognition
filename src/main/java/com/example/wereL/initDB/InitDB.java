package com.example.wereL.initDB;
import com.example.wereL.security.PassEncoder;
import com.example.wereL.dao.RoleRepositoryJpql;
import com.example.wereL.repository.UserRepositoryImpl;
import com.example.wereL.model.entity.User;
import com.example.wereL.model.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.util.Set;

@Component
public class InitDB {
    private final UserRepositoryImpl userRepository;
    private final RoleRepositoryJpql roleRepositoryJpql;

    private final PassEncoder passwordEncoder;

    @Autowired
    public InitDB(UserRepositoryImpl userRepository,
                  RoleRepositoryJpql roleRepositoryJpql, PassEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepositoryJpql = roleRepositoryJpql;

        this.passwordEncoder = passwordEncoder;

    }
    @PostConstruct
    public void initializationDB() {

        User user1 = User.builder()
                .firstName("Ivan")
                .lastName("Ivanov")
                .phone("777")
                .email("ivan@mail.ru")
                .password(passwordEncoder.passwordEncoder().encode("Ivan080navI"))
                .username("ivan")
                .enabled(true)
                .roles(Set.of(new Role("ROLE_USER")))
                .build();
        User user2 = User.builder()
                .firstName("Roman")
                .lastName("Romanov")
                .phone("773")
                .email("roman@mail.ru")
                .password(passwordEncoder.passwordEncoder().encode("2"))
                .username("roman")
                .enabled(true)
                .roles(Set.of(new Role("ROLE_ADMIN")))
                .build();
        User user3 = User.builder()
                .firstName("Marina")
                .lastName("Fedotova")
                .phone("774")
                .email("marina@mail.ru")
                .password(passwordEncoder.passwordEncoder().encode("5"))
                .username("marina")
                .enabled(true)
                .build();

        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
///////////////////////////////////////////////////////////
    }
}