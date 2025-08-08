package com.xtramile.fram.model.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "suburb")
public class Suburb {
    @Id
    private Integer id;
    private String name;
    @ManyToOne
    @JoinColumn(name="stateId", nullable=false)
    private State state;
}
