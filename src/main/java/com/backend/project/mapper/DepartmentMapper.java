package com.backend.project.mapper;

import com.backend.project.Entities.DepartmentEntity;
import com.backend.project.modelDTO.DepartmentDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DepartmentMapper {

    DepartmentMapper INSTANCE = Mappers.getMapper(DepartmentMapper.class);

    DepartmentDTO departmentEntityToDepartmentDTO(DepartmentEntity departmentEntity);

    DepartmentEntity departmentDTOToDepartmentEntity(DepartmentDTO departmentDTO);
}
