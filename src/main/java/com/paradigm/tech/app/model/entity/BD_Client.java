package com.paradigm.tech.app.model.entity;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.*;

@Entity
@Table(name = "bd_client")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@SQLDelete(sql = "UPDATE bd_client SET is_deleted = true WHERE id = ?")
public class BD_Client {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(nullable = false)
    private String clientName;
    private String clientAddress;
    private String clientEmail;
    private Boolean isDeleted = Boolean.FALSE;

    @ManyToOne
    @JoinColumn(name = "bd_detail_id")
    private BD_Detail bdDetail;
}
