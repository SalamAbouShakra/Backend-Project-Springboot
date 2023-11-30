package com.backend.project.service;

import com.backend.project.Entities.LeavetypeEntity;
import com.backend.project.mapper.LeavetypeMapper;
import com.backend.project.modelDTO.LeavetypeDTO;
import com.backend.project.repos.LeavetypeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Map;

@Service
public class LeavetypeService {
    @Autowired
    private LeavetypeRepo leavetypeRepo;

    @Autowired
    private LeavetypeMapper leavetypeMapper;

//    @Override
    public LeavetypeDTO createLeaveType(LeavetypeDTO leavetypeDTO) {
        LeavetypeEntity leavetypeEntity = leavetypeMapper.leavetypeDTOToLeavetypeEntity(leavetypeDTO);
        LeavetypeEntity savedLeavetypeEntity = leavetypeRepo.save(leavetypeEntity);
        return leavetypeMapper.leavetypeEntityToLeavetypeDTO(savedLeavetypeEntity);
    }

//    @Override
    public LeavetypeDTO getLeaveTypeById(Long leaveTypeId) {
        LeavetypeEntity leavetypeEntity = leavetypeRepo.findById(leaveTypeId).orElse(null);

        return leavetypeMapper.leavetypeEntityToLeavetypeDTO(leavetypeEntity);
    }

//    @Override
    public void deleteLeaveType(Long leaveTypeId) {
        leavetypeRepo.deleteById(leaveTypeId);
    }

//    @Override
    public void patchUpdateLeavetype(Long leaveTypeId, Map<String, Object> leavetypeMap) {
        LeavetypeEntity leavetypeToUpdate = leavetypeRepo.findById(leaveTypeId).orElse(null);
        Class<?> leavetypeToUpdateClass = LeavetypeEntity.class;
        patchUpdateEntity(leavetypeMap, leavetypeToUpdate, leavetypeToUpdateClass);

        leavetypeRepo.saveAndFlush(leavetypeToUpdate);
    }

    public void patchUpdateEntity(Map<String, Object> entityDTO, Object entityToUpdate, Class<?> entityToUpdateClass) {
        entityDTO.forEach((k, v) -> {
            Field field = ReflectionUtils.findField(entityToUpdateClass, k);

            if (field != null && field.getType().equals(Long.class) && v instanceof Integer)
                v = ((Integer) v).longValue();

            if (field != null) {
                field.setAccessible(true);
                ReflectionUtils.setField(field, entityToUpdate, v);
            }
        });
    }
}
