package org.swiggy.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.swiggy.userservice.model.entites.Users;
import org.swiggy.userservice.model.enums.Status;
import org.swiggy.userservice.model.enums.UserType;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    @Query("SELECT u FROM Users u WHERE u.location.city = :city and u.status = :status and u.userType = :userType")
    List<Users> findByCity(String city, Status status, UserType userType);
}
