package com.Files.DataSync.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(
        name = "users",
        indexes = {
                @Index(name = "idx_email", columnList = "email")
        }
)
public class User {
    @Id
    private String email;
    private String password;
}
