package com.paradigm.tech.app.model.entity;

import com.paradigm.tech.app.utlls.*;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "m_batch")
public class M_Batch {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Enumerated(EnumType.STRING)
    private EBatch batchName ;
}
