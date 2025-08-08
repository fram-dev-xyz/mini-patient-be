package com.xtramile.fram.repository;

import com.xtramile.fram.model.dto.StateDto;
import com.xtramile.fram.model.entity.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface StateRepository extends JpaRepository<State, Integer> {
    @Query("SELECT new com.xtramile.fram.model.dto.StateDto(s.id, s.name, null) FROM State s")
    List<StateDto> findAllDto();

    @Query("SELECT s FROM State s join s.suburbs where s.id = ?1")
    Optional<State> findStateById(Integer id);
}
