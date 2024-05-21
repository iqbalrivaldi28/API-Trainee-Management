package com.paradigm.tech.app.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "trainee_social_media")
public class Social_Media {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String platformName;
    private String url;

    @ManyToOne
    @JoinColumn(name = "trainee_detail_id")
    private Trainee_Detail traineeDetail;
}
