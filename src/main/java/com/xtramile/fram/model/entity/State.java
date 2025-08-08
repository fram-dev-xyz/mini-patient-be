package com.xtramile.fram.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "state")
public class State {
    @Id
    private Integer id;
    private String name;
    @OneToMany(mappedBy = "state")
    private List<Suburb> suburbs;
}
