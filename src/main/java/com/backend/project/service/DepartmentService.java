package com.backend.project.service;


import com.backend.project.Entities.DepartmentEntity;
import com.backend.project.mapper.DepartmentMapper;
import com.backend.project.modelDTO.DepartmentDTO;
import com.backend.project.repos.DepartmentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DepartmentService {

    private final DepartmentRepo departmentRepo;
    private final DepartmentMapper departmentMapper;

    //    @Autowired
    public DepartmentService(DepartmentRepo departmentRepo, DepartmentMapper departmentMapper) {
        this.departmentRepo = departmentRepo;
        this.departmentMapper = departmentMapper;
    }

    public List<DepartmentDTO> getAllDepartments() {
        List<DepartmentEntity> departments = departmentRepo.findAll();
        return departments.stream()
                .map(departmentMapper::departmentEntityToDepartmentDTO)
                .collect(Collectors.toList());
    }

    public DepartmentDTO createDepartment(DepartmentDTO departmentDTO) {
        DepartmentEntity departmentEntity = departmentMapper.departmentDTOToDepartmentEntity(departmentDTO);
        departmentEntity = departmentRepo.save(departmentEntity);
        return departmentMapper.departmentEntityToDepartmentDTO(departmentEntity);
    }


    public DepartmentDTO getDepartmentById(Long departmentId) {
        DepartmentEntity departmentEntity = departmentRepo.findById(departmentId).orElse(null);
        return departmentMapper.departmentEntityToDepartmentDTO(departmentEntity);
    }

    @Transactional
    public void deleteDepartment(Long departmentId) {
        DepartmentEntity departmentEntity = departmentRepo.findById(departmentId).orElse(null);

        departmentRepo.delete(departmentEntity);
    }

    public void updateDepartment(long id, Map<String, Object> departmentMap) {
        DepartmentEntity entityToUpdate = departmentRepo.findById(id).orElse(null);
        Class entityToUpdateClass = DepartmentEntity.class;
        updateEntity(departmentMap, entityToUpdate, entityToUpdateClass);
        departmentRepo.saveAndFlush(entityToUpdate);
    }

    public void updateEntity(Map<String, Object> entityDTO, Object entityToUpdate, Class entityToUpdateClass) {
        // Map key is field name, v is value
        entityDTO.forEach((k, v) -> {
            // use reflection to get field k on entityToUpdate and set it to value k
            Field field = ReflectionUtils.findField(entityToUpdateClass, k);
            field.setAccessible(true);
            ReflectionUtils.setField(field, entityToUpdate, v);
        });
    }
}
