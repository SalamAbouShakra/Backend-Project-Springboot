package com.backend.project.controller;


import com.backend.project.modelDTO.EmployeeDTO;
import com.backend.project.modelDTO.EmployeeDepartmentDTO;
import com.backend.project.service.DepartmentService;
import com.backend.project.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    private final EmployeeService employeeService;
    private final DepartmentService departmentService;

//    @Autowired
    public EmployeeController(EmployeeService employeeService, DepartmentService departmentService) {
        this.employeeService = employeeService;
        this.departmentService = departmentService;
    }

    @GetMapping
    public List<EmployeeDTO> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

//    @GetMapping("/department/{departmentId}")
//    public List<EmployeeDTO> getEmployeesByDepartment(@PathVariable Long departmentId) {
//        return employeeService.getEmployeesByDepartment(departmentId);
//    }

    @GetMapping("/department/{departmentId}")
    public ResponseEntity<List<EmployeeDepartmentDTO>> getEmployeesByDepartment(@PathVariable Long departmentId) {
        List<EmployeeDepartmentDTO> result = employeeService.getEmployeesByDepartment(departmentId);
        return new ResponseEntity<>(result, HttpStatus.OK);

    }

    @PostMapping("/addemp")
    public ResponseEntity<Void> addEmployee(@RequestBody EmployeeDTO employeeDTO) {
        employeeService.addEmployee(employeeDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping("/update/{employeeId}")
    public ResponseEntity<Void> patchUpdateEmployee(@PathVariable long employeeId, @RequestBody Map<String, Object> employeeMap) {
        employeeService.patchUpdateEmployee(employeeId, employeeMap);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @DeleteMapping("/{employeeId}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long employeeId) {
        employeeService.deleteEmployee(employeeId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
