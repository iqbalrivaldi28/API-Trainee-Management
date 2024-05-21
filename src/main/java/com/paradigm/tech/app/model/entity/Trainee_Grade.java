package com.paradigm.tech.app.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "trainee_grade")
public class Trainee_Grade {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "trainee_detail_id")
    private Trainee_Detail traineeDetail;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private Status status;

    private Float grade;
}
