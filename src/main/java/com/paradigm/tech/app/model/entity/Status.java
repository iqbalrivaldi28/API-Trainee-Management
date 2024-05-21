package com.paradigm.tech.app.model.entity;

import com.paradigm.tech.app.utlls.*;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "mst_status")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Status {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Enumerated(EnumType.STRING)
    private EStatus status;
}
