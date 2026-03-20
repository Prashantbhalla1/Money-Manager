package com.prashant.smatpersonalfinance.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name="tbl_expanse")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExpanseEntity {
    @Column(name="id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    @Column(name="name")
    private String name;
    @Column(name="amount")
    private BigDecimal amount;
    @Column(name="date")
    private LocalDate date;
    @CreationTimestamp
    @Column(name="created_at",updatable = false)
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    private    ProfileEntity profile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private CategoryEntity category;


    @PrePersist

    public void prePersist(){
        if(this.date==null){
            this.date=LocalDate.now();
        }
    }


}
