package com.example.javabeanvalidation2;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.TransactionSystemException;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * Created by mtumilowicz on 2018-08-22.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserValidationTest {

    @Autowired
    private UserRepository userRepository;

    private User fullValidUser = User.builder()
            .name("name")
            .aboutMe("about me about me")
            .age(20)
            .emails(Arrays.asList("a@a.pl", "b@b.pl"))
            .expirationDate(LocalDate.of(2020, 10, 10))
            .rank(0)
            .build();

    @Test
    public void saveValid() {
        userRepository.save(fullValidUser);
    }

    @Test(expected = TransactionSystemException.class)
    public void name() {
        userRepository.save(fullValidUser.toBuilder().name(null).build());
    }

    @Test(expected = TransactionSystemException.class)
    public void aboutMe_tooShort() {
        userRepository.save(fullValidUser.toBuilder().aboutMe("to short").build());
    }

    @Test(expected = TransactionSystemException.class)
    public void aboutMe_tooLong() {
        userRepository.save(fullValidUser.toBuilder().aboutMe("to long to long to long to long to long ").build());
    }

    @Test(expected = TransactionSystemException.class)
    public void age_tooLow() {
        userRepository.save(fullValidUser.toBuilder().age(17).build());
    }

    @Test(expected = TransactionSystemException.class)
    public void age_tooHigh() {
        userRepository.save(fullValidUser.toBuilder().age(151).build());
    }

    @Test(expected = TransactionSystemException.class)
    public void emails_empty() {
        userRepository.save(fullValidUser.toBuilder().emails(null).build());
    }

    @Test(expected = TransactionSystemException.class)
    public void emails_withEmptyElems() {
        LinkedList<String> strings = new LinkedList<>();
        strings.add(null);
        userRepository.save(fullValidUser.toBuilder().emails(strings).build());
    }

    @Test(expected = TransactionSystemException.class)
    public void emails_withNonEmails() {
        userRepository.save(fullValidUser.toBuilder().emails(Arrays.asList("a", "b")).build());
    }

    @Test(expected = TransactionSystemException.class)
    public void expirationDate_past() {
        userRepository.save(fullValidUser.toBuilder().expirationDate(LocalDate.of(2010, 10, 10)).build());
    }

    @Test(expected = TransactionSystemException.class)
    public void rank_negative() {
        userRepository.save(fullValidUser.toBuilder().rank(-1).build());
    }
}
