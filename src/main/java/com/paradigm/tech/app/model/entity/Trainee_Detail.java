package com.paradigm.tech.app.model.entity;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;

import java.util.Date;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "trainee_detail")
@SQLDelete(sql = "UPDATE trainee_detail SET is_deleted = true WHERE id=?")
public class Trainee_Detail {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String firstName;
    private String lastName;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dateOfBirth;
    private String placeOfBirth;
    private String phone;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date joinedAt;

    private String urlPhoto;
    private Boolean isDeleted = Boolean.FALSE;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "batch_id")
    private M_Batch batch ;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private Status status;
}