package com.backend.project.service;

import com.backend.project.Entities.ExpenseclaimEntity;
import com.backend.project.Entities.ExpenseclaimentryEntity;
import com.backend.project.Entities.ExpensetypeEntity;
import com.backend.project.mapper.ExpenseclaimMapper;
import com.backend.project.mapper.ExpenseclaimentryMapper;
import com.backend.project.modelDTO.ExpenseTypeTotalDTO;
import com.backend.project.modelDTO.ExpenseclaimDTO;
import com.backend.project.modelDTO.ExpenseclaimentryDTO;
import com.backend.project.repos.ExpenseclaimRepo;
import com.backend.project.repos.ExpenseclaimentryRepo;
import com.backend.project.repos.ExpensetypeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ExpenseclaimService {
    @Autowired
    private ExpenseclaimRepo expenseclaimRepo;

    @Autowired
    private ExpenseclaimentryRepo expenseclaimentryRepo;

    @Autowired
    private ExpensetypeRepo expensetypeRepo;


    @Transactional
    public ExpenseclaimDTO applyExpense(ExpenseclaimDTO expenseclaimDTO) {
        // Save the expense claim
        ExpenseclaimEntity expenseclaimEntity = new ExpenseclaimEntity();
        expenseclaimEntity.setDate(expenseclaimDTO.getDate());
        expenseclaimEntity.setDescription(expenseclaimDTO.getDescription());
        expenseclaimEntity.setStatus("Submitted"); // Set initial status
        expenseclaimEntity.setEmployeeId(expenseclaimDTO.getEmployeeId());
        expenseclaimRepo.save(expenseclaimEntity);

        // Return the created expense claim DTO without entries
        return ExpenseclaimMapper.INSTANCE.expenseclaimEntityToExpenseclaimDTO(expenseclaimEntity);

    }

    @Transactional
    public ExpenseclaimentryDTO createExpenseEntry(Long expenseClaimId, ExpenseclaimentryDTO entryDTO) {
        // Check if the expense claim exists
        ExpenseclaimEntity expenseclaimEntity = expenseclaimRepo.findById(expenseClaimId).orElse(null);


        // Save the expense entry and associate it with the expense claim
        entryDTO.setExpenseClaimId(expenseClaimId);
        ExpenseclaimentryEntity entryEntity = ExpenseclaimentryMapper.INSTANCE.expenseclaimentryDTOToExpeneseclaimentryEntity(entryDTO);
        expenseclaimentryRepo.save(entryEntity);

        // Update the total amount in the expense claim
        BigDecimal totalAmount = expenseclaimEntity.getTotal().add(entryDTO.getTotal());
        expenseclaimEntity.setTotal(totalAmount);
        expenseclaimRepo.save(expenseclaimEntity);

        // Return the created expense entry DTO
        return ExpenseclaimentryMapper.INSTANCE.expenseclaimentryEntityToExpenseclaimentryDTO(entryEntity);
    }

    @Transactional(readOnly = true)
    public List<ExpenseclaimentryDTO> getAllExpenseEntries() {
        List<ExpenseclaimentryEntity> entryEntities = expenseclaimentryRepo.findAll();
        return entryEntities.stream()
                .map(ExpenseclaimentryMapper.INSTANCE::expenseclaimentryEntityToExpenseclaimentryDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ExpenseclaimDTO> getAllExpenseClaims() {
        List<ExpenseclaimEntity> expenseclaimEntities = expenseclaimRepo.findAll();
        return expenseclaimEntities.stream()
                .map(ExpenseclaimMapper.INSTANCE::expenseclaimEntityToExpenseclaimDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ExpenseTypeTotalDTO> getTotalClaimsPerTypePerEmployee(Long employeeId) {
        List<ExpensetypeEntity> expenseTypes = expensetypeRepo.findAll();
        List<ExpenseTypeTotalDTO> result = new ArrayList<>();

        for (ExpensetypeEntity expenseType : expenseTypes) {
            BigDecimal totalAmount = expenseclaimentryRepo.getTotalAmountByTypeAndEmployee(expenseType.getId(), employeeId);
            ExpenseTypeTotalDTO dto = new ExpenseTypeTotalDTO(expenseType.getName(), totalAmount);
            result.add(dto);
        }

        return result;
    }

    @Transactional(readOnly = true)
    public ExpenseclaimDTO getExpenseClaimById(Long expenseClaimId) {
        ExpenseclaimEntity expenseClaimEntity = expenseclaimRepo.findById(expenseClaimId).orElse(null);

        return ExpenseclaimMapper.INSTANCE.expenseclaimEntityToExpenseclaimDTO(expenseClaimEntity);
    }

    @Transactional(readOnly = true)
    public ExpenseclaimentryDTO getExpenseEntryById(Long expenseEntryId) {
        ExpenseclaimentryEntity expenseEntryEntity = expenseclaimentryRepo.findById(expenseEntryId).orElse(null);

        return ExpenseclaimentryMapper.INSTANCE.expenseclaimentryEntityToExpenseclaimentryDTO(expenseEntryEntity);
    }

    @Transactional
    public void deleteExpenseClaim(Long expenseClaimId) {
        // Check if the expense claim exists
        ExpenseclaimEntity expenseClaimEntity = expenseclaimRepo.findById(expenseClaimId).orElse(null);


        // Delete the expense claim
        expenseclaimRepo.delete(expenseClaimEntity);
    }

    @Transactional
    public void deleteExpenseEntry(Long expenseEntryId) {
        // Check if the expense entry exists
        ExpenseclaimentryEntity expenseEntryEntity = expenseclaimentryRepo.findById(expenseEntryId).orElse(null);


        // Delete the expense entry
        expenseclaimentryRepo.delete(expenseEntryEntity);
    }

    @Transactional
    public void patchUpdateExpenseClaim(Long expenseClaimId, Map<String, Object> expenseClaimMap) {
        ExpenseclaimEntity expenseClaimToUpdate = expenseclaimRepo.findById(expenseClaimId).orElse(null);


        patchUpdateEntity(expenseClaimMap, expenseClaimToUpdate, ExpenseclaimEntity.class);

        expenseclaimRepo.saveAndFlush(expenseClaimToUpdate);
    }

    @Transactional
    public void patchUpdateExpenseEntry(Long expenseEntryId, Map<String, Object> expenseEntryMap) {
        ExpenseclaimentryEntity expenseEntryToUpdate = expenseclaimentryRepo.findById(expenseEntryId).orElse(null);


        patchUpdateEntity(expenseEntryMap, expenseEntryToUpdate, ExpenseclaimentryEntity.class);

        expenseclaimentryRepo.saveAndFlush(expenseEntryToUpdate);
    }

    private void patchUpdateEntity(Map<String, Object> entityDTO, Object entityToUpdate, Class<?> entityToUpdateClass) {
        entityDTO.forEach((fieldName, value) -> {
            Field field = ReflectionUtils.findField(entityToUpdateClass, fieldName);

            if (field != null && field.getType().equals(Long.class) && value instanceof Integer) {
                value = ((Integer) value).longValue();
            }

            if (field.getType().equals(BigDecimal.class) && value instanceof Double) {
                value = BigDecimal.valueOf((Double) value);
            }

            // Convert String to Date if needed
            if (field != null && field.getType().equals(Date.class) && value instanceof String) {
                try {
                    // Check if the field is of type java.sql.Date
                    if (field.getType().equals(java.sql.Date.class)) {
                        // Parse the string and convert it to java.sql.Date
                        value = java.sql.Date.valueOf((String) value);
                    } else {
                        // Parse the string and convert it to java.util.Date
                        value = new SimpleDateFormat("yyyy-MM-dd").parse((String) value);
                    }
                } catch (ParseException e) {
                    // Handle the parsing exception if needed
                    e.printStackTrace();
                }
            }

            if (field != null) {
                field.setAccessible(true);
                ReflectionUtils.setField(field, entityToUpdate, value);

                // Handle updating total in claim when updating total in entry
                if (fieldName.equals("total") && entityToUpdate instanceof ExpenseclaimentryEntity) {
                    updateTotalInClaim((ExpenseclaimentryEntity) entityToUpdate);
                }
            }
        });

        // Save the changes to the entity
        // Note: Depending on your application, you may need to adjust this part based on your persistence strategy.
        if (entityToUpdate instanceof ExpenseclaimentryEntity) {
            expenseclaimentryRepo.saveAndFlush((ExpenseclaimentryEntity) entityToUpdate);
        } else if (entityToUpdate instanceof ExpenseclaimEntity) {
            expenseclaimRepo.saveAndFlush((ExpenseclaimEntity) entityToUpdate);
        }

    }

    private void updateTotalInClaim(ExpenseclaimentryEntity expenseEntryToUpdate) {
        // Check if the expense claim exists
        ExpenseclaimEntity expenseClaimEntity = expenseclaimRepo.findById(expenseEntryToUpdate.getExpenseClaimId()).orElse(null);


        // Get all entries associated with the expense claim
        List<ExpenseclaimentryEntity> expenseEntries = expenseclaimentryRepo.findByExpenseClaimId(expenseClaimEntity.getId());

        // Sum the total amounts of all entries
        BigDecimal totalInClaim = expenseEntries.stream()
                .map(ExpenseclaimentryEntity::getTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Update the total in the expense claim
        expenseClaimEntity.setTotal(totalInClaim);
        expenseclaimRepo.saveAndFlush(expenseClaimEntity);
    }


}



