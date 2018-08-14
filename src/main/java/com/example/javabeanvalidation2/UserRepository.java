package com.example.javabeanvalidation2;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by mtumilowicz on 2018-08-14.
 */
public interface UserRepository extends JpaRepository<User, Integer> {
}
