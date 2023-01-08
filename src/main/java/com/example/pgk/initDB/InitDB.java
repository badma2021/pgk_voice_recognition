package com.example.pgk.initDB;
import com.example.pgk.dao.PartRepository;
import com.example.pgk.model.entity.Part;
import com.example.pgk.security.PassEncoder;
import com.example.pgk.dao.RoleRepositoryJpql;
import com.example.pgk.repository.UserRepositoryImpl;
import com.example.pgk.model.entity.User;
import com.example.pgk.model.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.Set;

@Component
public class InitDB {
    private final UserRepositoryImpl userRepository;
    private final RoleRepositoryJpql roleRepositoryJpql;
    private final PartRepository partRepository;
    private final PassEncoder passwordEncoder;

    @Autowired
    public InitDB(UserRepositoryImpl userRepository,
                  RoleRepositoryJpql roleRepositoryJpql, PartRepository partRepository, PassEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepositoryJpql = roleRepositoryJpql;
        this.partRepository = partRepository;
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
Part part1= Part.builder()
        .partName("Колесная пара")
        .partNumber(1L)
        .productionYear("2015.0")
        .factoryNumber("222")
        .comment("шайба, без буксы")
        .audioRecordName("wav2.wav")
        .createdAt(LocalDateTime.of(2022, 9, 22, 13, 30))
        .user(user1)
        .build();

        Part part2= Part.builder()
                .partName("рама боковая")
                .partNumber(2L)
                .productionYear("2017.0")
                .factoryNumber("китай")
                .comment("")
                .audioRecordName("wav1.wav")
                .createdAt(LocalDateTime.of(2021, 9, 21, 13, 30))
                .user(user2)
                .build();

        partRepository.save(part1);
        partRepository.save(part2);
    }
}