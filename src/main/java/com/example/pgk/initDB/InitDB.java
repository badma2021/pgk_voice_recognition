package com.example.pgk.initDB;
import com.example.pgk.security.PassEncoder;
import com.example.pgk.dao.RoleRepositoryJpql;
import com.example.pgk.repository.UserRepositoryImpl;
import com.example.pgk.model.entity.User;
import com.example.pgk.model.entity.Role;
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
                  RoleRepositoryJpql roleRepositoryJpql,PassEncoder passwordEncoder) {
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
                .roles(Set.of(new Role("ROLE_USER")))
                .build();
        User user2 = User.builder()
                .firstName("Roman")
                .lastName("Romanov")
                .phone("773")
                .email("roman@mail.ru")
                .password(passwordEncoder.passwordEncoder().encode("2"))
                .username("roman")
                .roles(Set.of(new Role("ROLE_ADMIN")))
                .build();
        User user3 = User.builder()
                .firstName("Marina")
                .lastName("Fedotova")
                .phone("774")
                .email("marina@mail.ru")
                .password(passwordEncoder.passwordEncoder().encode("5"))
                .username("marina")
                .build();
        // user1.setUserGroups(Set.of(group1));
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
///////////////////////////////////////////////////////////
//        Competence competence1 = Competence.builder()
//                .title("UX/UI")
//                .build();
//        Competence competence2 = Competence.builder()
//                .title("Agile")
//                .build();
//
//        Competence competence3 = Competence.builder()
//                .title("PHP")
//                .build();
//
//        Competence competence4 = Competence.builder()
//                .title("Data Science")
//                .build();
//        competenceRepository.save(competence1);
//        competenceRepository.save(competence2);
//        competenceRepository.save(competence4);
//        competenceRepository.save(competence3);
///////////////////////////////////////////////////////////
//        UserCompetence userCompetence1 = UserCompetence.builder()
//                .user(user1)
//                .competence(competence1)
//                .ordered(1)
//                .tested_at(LocalDateTime.of(2020, 3, 22, 13, 30))
//                .is_expert(false)
//                .build();
//
//        UserCompetence userCompetence2 = UserCompetence.builder()
//                .user(user2)
//                .competence(competence2)
//                .ordered(4)
//                .tested_at(LocalDateTime.of(2021, 3, 22, 13, 30))
//                .is_expert(true)
//                .build();
//
//        UserCompetence userCompetence3 = UserCompetence.builder()
//                .user(user3)
//                .competence(competence3)
//                .ordered(5)
//                .tested_at(now())
//                .is_expert(true)
//                .build();
//
//        UserCompetence userCompetence4 = UserCompetence.builder()
//                .user(user3)
//                .competence(competence4)
//                .ordered(2)
//                .tested_at(LocalDateTime.of(2022, 3, 22, 13, 30))
//                .is_expert(false)
//                .build();
//        userCompetenceRepository.save(userCompetence3);
//        userCompetenceRepository.save(userCompetence1);
//        userCompetenceRepository.save(userCompetence4);
//        userCompetenceRepository.save(userCompetence2);
//
//
////////////////////////////////////////////////////////
//        Reaction reaction1 = com.fastwin.newidea.model.entity.Reaction.builder()
//                .type(ReactionType.LIKE)
//                .build();
//        Reaction reaction2 = com.fastwin.newidea.model.entity.Reaction.builder()
//                .type(ReactionType.FAVOURITE)
//                .build();
//        reactionRepository.save(reaction1);
//        reactionRepository.save(reaction2);

    }
}