package com.backend.project.controller;


import com.backend.project.modelDTO.DepartmentDTO;
import com.backend.project.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/departments")
@CrossOrigin(origins = "http://localhost:4200")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;


    @GetMapping("/all")
    public List<DepartmentDTO> getAllDepartments() {
        return departmentService.getAllDepartments();
    }


    @PostMapping("/add")
    public DepartmentDTO createDepartment(@RequestBody DepartmentDTO departmentDTO) {

        return departmentService.createDepartment(departmentDTO);
    }

    @DeleteMapping("/{departmentId}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable Long departmentId) {
        departmentService.deleteDepartment(departmentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{departmentId}")
    public ResponseEntity<DepartmentDTO> getDepartmentById(@PathVariable Long departmentId) {
        DepartmentDTO departmentDTO = departmentService.getDepartmentById(departmentId);

        if (departmentDTO != null) {
            return new ResponseEntity<>(departmentDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/update/{id}")
    public void updateDepartment(@PathVariable long id, @RequestBody Map<String, Object> departmentMap) {
        departmentService.updateDepartment(id, departmentMap);

    }



}
