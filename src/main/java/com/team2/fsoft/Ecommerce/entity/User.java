package com.team2.fsoft.Ecommerce.entity;

import com.team2.fsoft.Ecommerce.enum_constant.Gender;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User extends  BaseEntity {

    @Column(unique = true)
    private  String email;

    private String password;

    private  String name;

    private String address;

    private int age;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_code")
    private Role role;

}
