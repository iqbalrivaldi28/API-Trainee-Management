package com.paradigm.tech.app.model.entity;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "trainee_experience")
public class Trainee_Experience {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "trainee_detail_id")
    private Trainee_Detail traineeDetail;

    private String companyName;
    private String position;

    @JsonFormat(pattern="MM-yyyy")
    private Date startDate;
    @JsonFormat(pattern="MM-yyyy")
    private Date endDate;
    private String description;
}
