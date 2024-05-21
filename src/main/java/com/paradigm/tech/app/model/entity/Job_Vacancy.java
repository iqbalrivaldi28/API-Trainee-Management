package com.paradigm.tech.app.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "job_vacancy")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Job_Vacancy {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private Instant createdAt;
    private Integer quota;
    @Column(nullable = false)
    private String position;
    private String description;

    @ManyToOne
    @JoinColumn(name = "bd_client_id")
    private BD_Client bdClient;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private Status status;
}
