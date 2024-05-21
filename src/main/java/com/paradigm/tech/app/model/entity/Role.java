package com.paradigm.tech.app.model.entity;

import com.paradigm.tech.app.utlls.*;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "mst_role")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Enumerated(EnumType.STRING)
    private ERole role;
}
