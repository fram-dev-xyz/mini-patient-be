package com.xtramile.fram.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "patient")
public class Patient{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "pid")
    private String patientId;
    private String firstName;
    private String lastName;
    @Column(name = "dob")
    private LocalDate dateOfBirth;
    private String gender;
    private String phoneNumber;
    private String address;
    @ManyToOne
    @JoinColumn(name = "suburbId")
    private Suburb suburb;
    private String postcode;

    @Version
    private int version;
    @CreationTimestamp
    private Instant createdAt;
    @UpdateTimestamp
    private Instant updatedAt;
}
