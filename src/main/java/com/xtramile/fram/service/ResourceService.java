package com.xtramile.fram.service;

import com.xtramile.fram.model.dto.StateDto;

import java.util.List;

public interface ResourceService {
    List<StateDto> getStates();
    StateDto getState(int stateId);
}
