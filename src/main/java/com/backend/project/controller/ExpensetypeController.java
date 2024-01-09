package com.backend.project.controller;

import com.backend.project.modelDTO.ExpensetypeDTO;
import com.backend.project.service.ExpensetypeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/expensetypes")
@CrossOrigin(origins = "http://localhost:4200")
public class ExpensetypeController {

    @Autowired
    private ExpensetypeService expensetypeService;

    @PostMapping("/createExpensetype")
    public ResponseEntity<ExpensetypeDTO> createExpensetype(@RequestBody ExpensetypeDTO expensetypeDTO) {
        ExpensetypeDTO createdExpensetype = expensetypeService.createExpensetype(expensetypeDTO);
        return new ResponseEntity<>(createdExpensetype, HttpStatus.CREATED);
    }

    @GetMapping("/getAllExpenseTypes")
    public ResponseEntity<List<ExpensetypeDTO>> getAllExpenseTypes() {
        List<ExpensetypeDTO> expenseTypes = expensetypeService.getAllExpenseTypes();
        return new ResponseEntity<>(expenseTypes, HttpStatus.OK);
    }

    @GetMapping("/{expenseTypeId}")
    public ResponseEntity<ExpensetypeDTO> getExpenseTypeById(@PathVariable Long expenseTypeId) {
        ExpensetypeDTO result = expensetypeService.getExpenseTypeById(expenseTypeId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/{expenseTypeId}")
    public ResponseEntity<Void> deleteExpenseType(@PathVariable Long expenseTypeId) {
        expensetypeService.deleteExpenseType(expenseTypeId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{expenseTypeId}")
    public ResponseEntity<Void> patchUpdateExpenseType(
            @PathVariable Long expenseTypeId,
            @RequestBody Map<String, Object> expenseTypeMap) {
        expensetypeService.patchUpdateExpenseType(expenseTypeId, expenseTypeMap);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
