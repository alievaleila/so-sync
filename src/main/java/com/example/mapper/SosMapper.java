package com.example.mapper;

import com.example.dto.SosRequestDto;
import com.example.dto.SosResponseDto;
import com.example.entity.Sos;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SosMapper {

    Sos toEntity(SosRequestDto request);

    SosResponseDto toResponse(Sos sos);
}
