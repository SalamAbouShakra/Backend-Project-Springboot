package com.backend.project.mapper;

import com.backend.project.Entities.DepartmentEntity;
import com.backend.project.Entities.LeaveEntity;
import com.backend.project.modelDTO.DepartmentDTO;
import com.backend.project.modelDTO.LeaveDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface LeaveMapper {
    LeaveMapper INSTANCE = Mappers.getMapper(LeaveMapper.class);

    LeaveDTO leaveEntityToLeaveDTO(LeaveEntity leaveEntity);

    LeaveEntity leaveDTOToLeaveEntity(LeaveDTO leaveDTO);
}
