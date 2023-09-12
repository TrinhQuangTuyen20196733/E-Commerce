package com.team2.fsoft.Ecommerce.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Shop {

    @Id
    private  long id;

    private String name;

    private String address;

    @Column(name = "phone_number", unique = true)
    private String phoneNumber;

    @Column(name = "avatar_url")
    private String avatarUrl;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private User user;

    @Column(name = "created_date")
    @CreatedDate
    private Date created;

    @Column(name = "modified_date")
    @LastModifiedDate
    private Date modified;

    @Column(name = "created_by")
    @CreatedBy
    private String author;

    @Column(name = "modified_by")
    @LastModifiedDate
    private String editor;
}
