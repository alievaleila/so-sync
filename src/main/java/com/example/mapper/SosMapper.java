package com.example.mapper;

import com.example.dto.SosRequest;
import com.example.dto.SosResponse;
import com.example.entity.Sos;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface SosMapper {

    Sos toEntity(SosRequest request);
    SosResponse toResponse(Sos sos);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromRequest(SosRequest request, @MappingTarget Sos sos);
}
