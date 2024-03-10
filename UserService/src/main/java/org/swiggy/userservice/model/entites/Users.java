package org.swiggy.userservice.model.entites;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.jdbc.DatabaseDriver;
import org.swiggy.userservice.model.dto.Location;
import org.swiggy.userservice.model.enums.Status;
import org.swiggy.userservice.model.enums.UserType;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;
    private String password;
    private String email;
    private String contactNumber;
    @Embedded
    private Location location;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private UserType userType = UserType.CUSTOMER;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Status status = Status.ACTIVE;
    private LocalDateTime createdDate;

}
