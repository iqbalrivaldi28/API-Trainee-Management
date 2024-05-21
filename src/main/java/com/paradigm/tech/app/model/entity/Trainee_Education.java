package com.paradigm.tech.app.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "trainee_education")
public class Trainee_Education {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "trainee_detail_id")
    private Trainee_Detail traineeDetail;

    private String institutionName;
    private String fieldOfStudy;
    private Year graduationYear;
    private Float cGPA;

}
