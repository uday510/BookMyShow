package com.lld.bookmyshow.repositories;

import com.lld.bookmyshow.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Override
    Optional<User> findById(Long aLong);
    Optional<User>findByEmail(String email);

    @Override
    User save(User user);
}


// JPA : Interface
// Hibernate : Actual Implementation of this interface