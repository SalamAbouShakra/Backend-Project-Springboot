package com.backend.project.mapper;

import com.backend.project.Entities.ExpenseclaimEntity;
import com.backend.project.Entities.ExpensetypeEntity;
import com.backend.project.modelDTO.ExpenseclaimDTO;
import com.backend.project.modelDTO.ExpensetypeDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ExpensetypeMapper {
    ExpensetypeMapper INSTANCE = Mappers.getMapper(ExpensetypeMapper.class);

    ExpensetypeDTO expensetypeEntityToExpensetypeDTO(ExpensetypeEntity expensetypeEntity);

    ExpensetypeEntity expensetypeDTOToExpenesetypeEntity(ExpensetypeDTO expensetypeDTO);
}
