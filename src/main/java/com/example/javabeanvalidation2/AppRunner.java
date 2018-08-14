package com.example.javabeanvalidation2;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * Created by mtumilowicz on 2018-07-28.
 */
@Component
@AllArgsConstructor
@Slf4j
public class AppRunner implements CommandLineRunner {

    private final UserRepository userRepository;

    @Override
    public void run(String... args) {
        User user = User.builder()
                .emails(Arrays.asList(null, "not valid email"))
                .rank(-1)
                .build();
        
        try {
            userRepository.save(user);
        } catch (Exception ex) {
            System.out.println(ex.getLocalizedMessage());
        }
    }

}
