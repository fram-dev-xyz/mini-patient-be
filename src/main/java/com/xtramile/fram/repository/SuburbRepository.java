package com.xtramile.fram.repository;

import com.xtramile.fram.model.entity.Suburb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SuburbRepository extends JpaRepository<Suburb, Integer> {

}
