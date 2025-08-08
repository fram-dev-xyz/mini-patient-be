package com.xtramile.fram.service;

import com.xtramile.fram.model.dto.StateDto;
import com.xtramile.fram.model.dto.SuburbDto;
import com.xtramile.fram.model.entity.State;
import com.xtramile.fram.repository.StateRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ResourceServiceImpl implements ResourceService{
    @Autowired
    private StateRepository stateRepository;

    @Override
    public List<StateDto> getStates() {
        return stateRepository.findAllDto();
    }

    @Override
    public StateDto getState(int stateId) {
        Optional<State> stateOpt = stateRepository.findStateById(stateId);
        State state = stateOpt.orElseThrow(() -> new EntityNotFoundException("State not found"));
        //convert to DTO
        StateDto.StateDtoBuilder stateDtoBuilder = StateDto.builder()
                .id(state.getId())
                .name(state.getName())
                .suburbs(state.getSuburbs().stream().map(suburb -> new SuburbDto(suburb.getId(), suburb.getName())).toList());
        return stateDtoBuilder.build();
    }

}
