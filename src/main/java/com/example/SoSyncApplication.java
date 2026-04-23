package com.example;

import com.example.entity.User;
import com.example.enums.Role;
import com.example.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableScheduling
public class SoSyncApplication {

    public static void main(String[] args) {
        SpringApplication.run(SoSyncApplication.class, args);
    }

    @Bean
    public CommandLineRunner initData(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepository.findByEmail("test@example.com").isEmpty()) {
                User user = new User();
                user.setName("Leyla");
                user.setEmail("test@example.com");
                user.setPassword(passwordEncoder.encode("password123"));
                user.setRole(Role.ROLE_USER);
                userRepository.save(user);
            }
        };
    }
}
