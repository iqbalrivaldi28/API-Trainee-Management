package com.paradigm.tech.app.model.entity;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.*;

@Entity
@Table(name = "bd_detail")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@SQLDelete(sql = "UPDATE bd_detail SET is_deleted = true WHERE id = ?")
public class BD_Detail {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(nullable = false)
    private String firstName;
    private String lastName;
    private String urlPhoto;
    private Boolean isDeleted = Boolean.FALSE;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
