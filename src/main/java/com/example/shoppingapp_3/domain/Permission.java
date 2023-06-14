package com.example.shoppingapp_3.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "Permission")
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "permission_id")
    private Long permissionId;

    @Column(name = "role")
    private String role;
}
