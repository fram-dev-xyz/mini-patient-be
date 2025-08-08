package com.xtramile.fram.model.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class StateDto {
    private int id;
    private String name;
    private List<SuburbDto> suburbs;
}
