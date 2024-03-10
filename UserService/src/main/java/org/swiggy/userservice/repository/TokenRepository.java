package org.swiggy.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.swiggy.userservice.model.entites.Token;
import org.swiggy.userservice.model.enums.UserType;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {
    @Query("SELECT t FROM Token t WHERE t.value = :value and t.user.email = :email and t.user.userType = :userType and t.isDeleted = false")
    Optional<Token> findByValueAndUserEmailAndUserType(String value, String email, UserType userType);
}
