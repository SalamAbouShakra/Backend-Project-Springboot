package com.backend.project.mapper;

import com.backend.project.Entities.EmployeeEntity;
import com.backend.project.modelDTO.EmployeeDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EmployeeMapper {

    EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);

    EmployeeDTO employeeEntityToEmployeeDTO(EmployeeEntity employeeEntity);

    EmployeeEntity employeeDTOToEmployeeEntity(EmployeeDTO employeeDTO);
}
