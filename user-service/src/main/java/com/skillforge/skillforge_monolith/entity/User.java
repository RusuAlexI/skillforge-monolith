package com.skillforge.skillforge_monolith.entity;

import com.skillforge.skillforge_monolith.common.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "app_user")  // "user" is a reserved word in PostgreSQL
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;  // Will hash later with BCrypt

    @Column(nullable = false)
    private String name;

}
