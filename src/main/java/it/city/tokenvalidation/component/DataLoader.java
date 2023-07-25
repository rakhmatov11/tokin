package it.city.tokenvalidation.component;

import it.city.tokenvalidation.entity.User;
import it.city.tokenvalidation.entity.enums.RoleName;
import it.city.tokenvalidation.repository.AuthRepository;
import it.city.tokenvalidation.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;


@Component
public class DataLoader implements CommandLineRunner {
    final
    AuthRepository userRepository;
    final
    RoleRepository roleRepository;
    final
    PasswordEncoder passwordEncoder;


    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String initMode;

    public DataLoader(AuthRepository userRepository, RoleRepository roleRepository,
                      @Lazy PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if (initMode.equals("create")) {
            userRepository.save(
                    new User(
                            "Bobur",
                            "Raxmatov",
                            "Bobur",
                            "+998908904811",
                            "Boburraxmatov2007@gmail.com",
                            passwordEncoder.encode("root123"),
                            Collections.singleton(roleRepository.findAllByRoleName(RoleName.ROLE_SUPER_ADMIN)),
                            true
                    ));
        }
    }
}