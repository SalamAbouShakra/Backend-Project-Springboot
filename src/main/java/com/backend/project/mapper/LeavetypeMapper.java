package com.backend.project.mapper;

import com.backend.project.Entities.LeaveEntity;
import com.backend.project.Entities.LeavetypeEntity;
import com.backend.project.modelDTO.LeaveDTO;
import com.backend.project.modelDTO.LeavetypeDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface LeavetypeMapper {
    LeavetypeMapper INSTANCE = Mappers.getMapper(LeavetypeMapper.class);

    LeavetypeDTO leavetypeEntityToLeavetypeDTO(LeavetypeEntity leavetypeEntity);

    LeavetypeEntity leavetypeDTOToLeavetypeEntity(LeavetypeDTO leavetypeDTO);
}
