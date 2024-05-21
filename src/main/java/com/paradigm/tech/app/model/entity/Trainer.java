package com.paradigm.tech.app.model.entity;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "trainer_detail")
@SQLDelete(sql = "UPDATE trainer_detail SET is_deleted = true WHERE id=?")
public class Trainer {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "batch_id")
    private M_Batch batch;

    @Column(nullable = false)
    private String firstName;
    private String lastName;
    private String urlPhoto;
    private Boolean isDeleted = Boolean.FALSE;

}
