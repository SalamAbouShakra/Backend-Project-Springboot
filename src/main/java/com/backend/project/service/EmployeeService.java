package com.backend.project.service;


import com.backend.project.Entities.DepartmentEntity;
import com.backend.project.Entities.EmployeeEntity;
import com.backend.project.mapper.DepartmentMapper;
import com.backend.project.mapper.EmployeeMapper;
import com.backend.project.modelDTO.DepartmentDTO;
import com.backend.project.modelDTO.EmployeeDTO;
import com.backend.project.modelDTO.EmployeeDepartmentDTO;
import com.backend.project.repos.DepartmentRepo;
import com.backend.project.repos.EmployeeRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    private final EmployeeRepo employeeRepo;
    private final DepartmentService departmentService;
    private final EmployeeMapper employeeMapper;
    private final DepartmentMapper departmentMapper;
    private final DepartmentRepo departmentRepo;

    //    @Autowired
    public EmployeeService(EmployeeRepo employeeRepo, DepartmentService departmentService, EmployeeMapper employeeMapper, DepartmentMapper departmentMapper, DepartmentRepo departmentRepo) {
        this.employeeRepo = employeeRepo;
        this.departmentService = departmentService;
        this.employeeMapper = employeeMapper;
        this.departmentMapper = departmentMapper;
        this.departmentRepo = departmentRepo;
    }

    public List<EmployeeDTO> getAllEmployees() {
        List<EmployeeEntity> employees = employeeRepo.findAll();
        return employees.stream()
                .map(employeeMapper::employeeEntityToEmployeeDTO)
                .collect(Collectors.toList());
    }

//    public List<EmployeeDTO> getEmployeesByDepartment(Long departmentId) {
//        List<EmployeeEntity> employees = employeeRepo.findByDepartmentId(departmentId);
//        return employees.stream()
//                .map(employeeMapper::employeeEntityToEmployeeDTO)
//                .collect(Collectors.toList());
//    }

    public List<EmployeeDepartmentDTO> getEmployeesByDepartment(Long departmentId) {
        List<EmployeeEntity> employees = employeeRepo.findByDepartmentId(departmentId);
        DepartmentDTO departmentDTO = departmentService.getDepartmentById(departmentId);

        List<EmployeeDTO> employeeDTOs = employees.stream()
                .map(employeeMapper::employeeEntityToEmployeeDTO)
                .collect(Collectors.toList());

        EmployeeDepartmentDTO result = new EmployeeDepartmentDTO();
        result.setDepartment(departmentDTO);
        result.setEmployees(employeeDTOs);

        return Collections.singletonList(result);
    }

    public void addEmployee(EmployeeDTO employeeDTO) {
        EmployeeEntity employeeEntity = employeeMapper.employeeDTOToEmployeeEntity(employeeDTO);
        if (employeeDTO.getDepartmentId() != null) {
            DepartmentDTO departmentDTO = departmentService.getDepartmentById(employeeDTO.getDepartmentId());
            employeeEntity.setDepartmentId(departmentDTO.getId());
        }
        employeeRepo.save(employeeEntity);

    }

    public void patchUpdateEmployee(long id, Map<String, Object> employeeMap) {
        EmployeeEntity employeeToUpdate = employeeRepo.findById(id).orElse(null);
        Class<?> employeeToUpdateClass = EmployeeEntity.class;
        patchUpdateEntity(employeeMap, employeeToUpdate, employeeToUpdateClass);

        // Check if "departmentId" is present in the update map
//        if (employeeMap.containsKey("departmentId")) {
//            // Assuming departmentRepo is your DepartmentRepository
//            Long departmentId = (Long) employeeMap.get("departmentId");
//            DepartmentEntity departmentToUpdate = departmentRepo.findById(departmentId).orElse(null);
//
//            if (departmentToUpdate != null) {
//                // Update the employee's department
//                employeeToUpdate.setDepartmentId(departmentId);
//            }
//        }

        employeeRepo.saveAndFlush(employeeToUpdate);
    }

    public void patchUpdateEntity(Map<String, Object> entityDTO, Object entityToUpdate, Class<?> entityToUpdateClass) {
        // Map key is field name, value is the new value
        entityDTO.forEach((k, v) -> {
            // Use reflection to get the field k on entityToUpdate
            Field field = ReflectionUtils.findField(entityToUpdateClass, k);

            if (field != null && field.getType().equals(Long.class) && v instanceof Integer)
                v = ((Integer) v).longValue();

            if (field != null) {
                field.setAccessible(true);
                ReflectionUtils.setField(field, entityToUpdate, v);
            }
        });
    }


    @Transactional
    public void deleteEmployee(Long employeeId) {
        EmployeeEntity employeeEntity = employeeRepo.findById(employeeId).orElse(null);


        employeeRepo.delete(employeeEntity);
    }





}
