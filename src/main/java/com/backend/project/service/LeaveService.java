package com.backend.project.service;

import com.backend.project.Entities.LeaveEntity;
import com.backend.project.Entities.LeavetypeEntity;
import com.backend.project.mapper.LeaveMapper;
import com.backend.project.mapper.LeavetypeMapper;
import com.backend.project.modelDTO.LeaveDTO;
import com.backend.project.modelDTO.LeavetypeDTO;
import com.backend.project.repos.LeaveRepo;
import com.backend.project.repos.LeavetypeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class LeaveService {

    @Autowired
    private LeaveRepo leaveRepo;

    @Autowired
    private LeavetypeRepo leavetypeRepo;

    @Autowired
    private LeaveMapper leaveMapper;

    @Autowired
    private LeavetypeMapper leavetypeMapper;

//    @Override
    public List<LeavetypeDTO> getAllLeaveTypes() {
        List<LeavetypeEntity> leavetypeEntities = leavetypeRepo.findAll();
        return leavetypeEntities.stream()
                .map(leavetypeMapper::leavetypeEntityToLeavetypeDTO)
                .collect(Collectors.toList());
    }

//    @Override
    public LeaveDTO submitLeave(LeaveDTO leaveDTO) {
        LeaveEntity leaveEntity = leaveMapper.leaveDTOToLeaveEntity(leaveDTO);
        // Additional logic/validation can be added here
//        LeaveEntity savedLeaveEntity =
                leaveRepo.save(leaveEntity);
        return leaveDTO;
    }

//    @Override
    public List<LeaveDTO> getLeavesByEmployeeAndDateRange(Long employeeId, Date fromDate, Date toDate) {
        List<LeaveEntity> leaveEntities = leaveRepo.findByEmployeeIdAndLeaveFromBetween(employeeId, fromDate, toDate);
        return leaveEntities.stream()
                .map(leaveMapper::leaveEntityToLeaveDTO)
                .collect(Collectors.toList());
    }

//    @Override
    public Page<LeaveDTO> getLeavesByLeaveTypeAndEmployee(Long leaveTypeId, Long employeeId, Pageable pageable) {
        LeavetypeEntity leavetypeEntity = leavetypeRepo.findById(leaveTypeId).orElse(null);


        List<LeaveEntity> leaveEntities = leaveRepo.findByLeaveTypeIdAndEmployeeId(leavetypeEntity.getId(), employeeId, pageable);
        return new PageImpl<>(leaveEntities.stream()
                .map(leaveMapper::leaveEntityToLeaveDTO)
                .collect(Collectors.toList()), pageable, leaveEntities.size());
    }

//    @Override
    public List<LeaveDTO> getAllLeaves() {
        List<LeaveEntity> leaveEntities = leaveRepo.findAll();
        return leaveEntities.stream()
                .map(leaveMapper::leaveEntityToLeaveDTO)
                .collect(Collectors.toList());
    }

//    @Override
    public void deleteLeave(Long leaveId) {
        leaveRepo.deleteById(leaveId);
    }

//    @Override
    public void patchUpdateLeave(Long leaveId, Map<String, Object> leaveMap) {
        LeaveEntity leaveToUpdate = leaveRepo.findById(leaveId).orElse(null);
        Class<?> leaveToUpdateClass = LeaveEntity.class;
        patchUpdateEntity(leaveMap, leaveToUpdate, leaveToUpdateClass);

        leaveRepo.saveAndFlush(leaveToUpdate);
    }

    public void patchUpdateEntity(Map<String, Object> entityDTO, Object entityToUpdate, Class<?> entityToUpdateClass) {
        entityDTO.forEach((k, v) -> {
            Field field = ReflectionUtils.findField(entityToUpdateClass, k);

            if (field != null && field.getType().equals(Long.class) && v instanceof Integer)
                v = ((Integer) v).longValue();

            // Convert String to Date if needed
            if (field.getType().equals(Date.class) && v instanceof String) {
                try {
                    // Check if the field is of type java.sql.Date
                    if (field.getType().equals(java.sql.Date.class)) {
                        // Parse the string and convert it to java.sql.Date
                        v = java.sql.Date.valueOf((String) v);
                    } else {
                        // Parse the string and convert it to java.util.Date
                        v = new SimpleDateFormat("yyyy-MM-dd").parse((String) v);
                    }
                } catch (ParseException e) {
                    // Handle the parsing exception if needed
                    e.printStackTrace();
                }
            }

            if (field != null) {
                field.setAccessible(true);
                ReflectionUtils.setField(field, entityToUpdate, v);
            }
        });
    }



}
