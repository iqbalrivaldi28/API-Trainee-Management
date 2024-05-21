package com.paradigm.tech.app.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_job_application")
public class T_Job_Application {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(updatable = false)
    private Date applicationDate;

    @ManyToOne
    @JoinColumn(name = "job_vacancy_id")
    private Job_Vacancy jobVacancy;

    @ManyToOne
    @JoinColumn(name = "trainee_detail_id")
    private Trainee_Detail traineeDetail;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private Status status;
}
