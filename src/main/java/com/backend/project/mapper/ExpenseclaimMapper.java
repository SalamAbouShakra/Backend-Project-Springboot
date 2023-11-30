package com.backend.project.mapper;


import com.backend.project.Entities.ExpenseclaimEntity;

import com.backend.project.modelDTO.ExpenseclaimDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ExpenseclaimMapper {
    ExpenseclaimMapper INSTANCE = Mappers.getMapper(ExpenseclaimMapper.class);

    ExpenseclaimDTO expenseclaimEntityToExpenseclaimDTO(ExpenseclaimEntity expenseclaimEntity);

    ExpenseclaimEntity expenseclaimDTOToExpeneseclaimEntity(ExpenseclaimDTO expenseclaimDTO);
}
