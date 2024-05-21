package com.paradigm.tech.app.model.entity;

import com.paradigm.tech.app.utlls.*;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "trainee_skill")
public class Trainee_Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "trainee_detail_id")
    private Trainee_Detail traineeDetail;

    @ManyToOne
    @JoinColumn(name = "skill_id")
    private M_Skill skill;

    @Enumerated(value = EnumType.STRING)
    private ELevel level;
}
