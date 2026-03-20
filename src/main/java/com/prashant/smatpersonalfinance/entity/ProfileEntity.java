package com.prashant.smatpersonalfinance.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name="tbl_profile")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;
    @Column(name="full_name")
    private String fullName;
    @Column(name="email",unique = true)
            private String email;
    @Column(name="password")
            private String password;
    @Column(name="created_at" , updatable = false)
    @CreationTimestamp
    private String createdAt;
    @UpdateTimestamp
    @Column(name="updated_at")
    private String updatedAt;
    @Column(name="is_active")
    private Boolean isActive;
    @Column(name="activated_token")
    private String activatedToken;
    @PrePersist
    public void prePersist(){
        if(this.isActive==null){
            isActive=false;
        }
    }

}
