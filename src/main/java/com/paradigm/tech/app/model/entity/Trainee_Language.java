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
@Table(name = "trainee_language")
public class Trainee_Language {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "trainee_detail_id")
    private Trainee_Detail traineeDetail;

    @ManyToOne
    @JoinColumn(name = "language_id")
    private M_Language language;

    @Enumerated(value = EnumType.STRING)
    private ELevel level;
}
