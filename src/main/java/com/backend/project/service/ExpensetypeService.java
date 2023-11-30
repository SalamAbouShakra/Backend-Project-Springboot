package com.backend.project.service;

import com.backend.project.Entities.ExpensetypeEntity;
import com.backend.project.mapper.ExpensetypeMapper;
import com.backend.project.modelDTO.ExpensetypeDTO;
import com.backend.project.repos.ExpensetypeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ExpensetypeService {
    @Autowired
    private ExpensetypeRepo expensetypeRepo;

    @Autowired
    private ExpensetypeMapper expensetypeMapper;

//    @Override
    public ExpensetypeDTO createExpensetype(ExpensetypeDTO expensetypeDTO) {
        ExpensetypeEntity expensetypeEntity = expensetypeMapper.expensetypeDTOToExpenesetypeEntity(expensetypeDTO);
        ExpensetypeEntity savedExpensetypeEntity = expensetypeRepo.save(expensetypeEntity);
        return expensetypeMapper.expensetypeEntityToExpensetypeDTO(savedExpensetypeEntity);
    }

//    @Override
    public List<ExpensetypeDTO> getAllExpenseTypes() {
        List<ExpensetypeEntity> expensetypeEntities = expensetypeRepo.findAll();
        return expensetypeEntities.stream()
                .map(expensetypeMapper::expensetypeEntityToExpensetypeDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ExpensetypeDTO getExpenseTypeById(Long expenseTypeId) {
        ExpensetypeEntity expensetypeEntity = expensetypeRepo.findById(expenseTypeId).orElse(null);

        return expensetypeMapper.expensetypeEntityToExpensetypeDTO(expensetypeEntity);
    }

    @Transactional
    public void deleteExpenseType(Long expenseTypeId) {
        // Check if the expense type exists
        ExpensetypeEntity expensetypeEntity = expensetypeRepo.findById(expenseTypeId).orElse(null);

        // Delete the expense type
        expensetypeRepo.delete(expensetypeEntity);
    }

    @Transactional
    public void patchUpdateExpenseType(Long expenseTypeId, Map<String, Object> expenseTypeMap) {
        ExpensetypeEntity entityToUpdate = expensetypeRepo.findById(expenseTypeId).orElse(null);

        updateEntity(expenseTypeMap, entityToUpdate, ExpensetypeEntity.class);

        expensetypeRepo.saveAndFlush(entityToUpdate);
    }

    private void updateEntity(Map<String, Object> entityDTO, Object entityToUpdate, Class entityToUpdateClass) {
        entityDTO.forEach((fieldName, value) -> {
            Field field = ReflectionUtils.findField(entityToUpdateClass, fieldName);
            if (field != null) {
                field.setAccessible(true);
                ReflectionUtils.setField(field, entityToUpdate, value);
            }
        });
    }

}
