package com.backend.project.mapper;

import com.backend.project.Entities.ExpenseclaimEntity;
import com.backend.project.Entities.ExpenseclaimentryEntity;
import com.backend.project.modelDTO.ExpenseclaimDTO;
import com.backend.project.modelDTO.ExpenseclaimentryDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ExpenseclaimentryMapper {
    ExpenseclaimentryMapper INSTANCE = Mappers.getMapper(ExpenseclaimentryMapper.class);

    ExpenseclaimentryDTO expenseclaimentryEntityToExpenseclaimentryDTO(ExpenseclaimentryEntity expenseclaimentryEntity);

    ExpenseclaimentryEntity expenseclaimentryDTOToExpeneseclaimentryEntity(ExpenseclaimentryDTO expenseclaimentryDTO);
}
