package com.paradigm.tech.app.model.entity;

import java.time.*;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "mst_users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "role_id")
    private Role role;
    private Instant createdAt;
}
