package com.backend.project.controller;

import com.backend.project.modelDTO.ExpenseTypeTotalDTO;
import com.backend.project.modelDTO.ExpenseclaimDTO;
import com.backend.project.modelDTO.ExpenseclaimentryDTO;
import com.backend.project.service.ExpenseclaimService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/expense-claims")
public class ExpenseclaimController {
    @Autowired
    private ExpenseclaimService expenseclaimService;

    @PostMapping("/apply-expense")
    public ResponseEntity<ExpenseclaimDTO> applyExpense(@RequestBody ExpenseclaimDTO expenseclaimDTO) {
        ExpenseclaimDTO result = expenseclaimService.applyExpense(expenseclaimDTO);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PostMapping("/{expenseClaimId}/create-entry")
    public ResponseEntity<ExpenseclaimentryDTO> createExpenseEntry(
            @PathVariable Long expenseClaimId,
            @RequestBody ExpenseclaimentryDTO entryDTO) {
        ExpenseclaimentryDTO result = expenseclaimService.createExpenseEntry(expenseClaimId, entryDTO);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping("/entries")
    public ResponseEntity<List<ExpenseclaimentryDTO>> getAllExpenseEntries() {
        List<ExpenseclaimentryDTO> entries = expenseclaimService.getAllExpenseEntries();
        return new ResponseEntity<>(entries, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ExpenseclaimDTO>> getAllExpenseClaims() {
        List<ExpenseclaimDTO> expenseclaims = expenseclaimService.getAllExpenseClaims();
        return new ResponseEntity<>(expenseclaims, HttpStatus.OK);
    }

    @GetMapping("/total-claims/{employeeId}")
    public ResponseEntity<List<ExpenseTypeTotalDTO>> getTotalClaimsPerTypePerEmployee(
            @PathVariable Long employeeId) {
        List<ExpenseTypeTotalDTO> result = expenseclaimService.getTotalClaimsPerTypePerEmployee(employeeId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/{expenseClaimId}")
    public ResponseEntity<ExpenseclaimDTO> getExpenseClaimById(@PathVariable Long expenseClaimId) {
        ExpenseclaimDTO result = expenseclaimService.getExpenseClaimById(expenseClaimId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/entries/{expenseEntryId}")
    public ResponseEntity<ExpenseclaimentryDTO> getExpenseEntryById(@PathVariable Long expenseEntryId) {
        ExpenseclaimentryDTO result = expenseclaimService.getExpenseEntryById(expenseEntryId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/{expenseClaimId}")
    public ResponseEntity<Void> deleteExpenseClaim(@PathVariable Long expenseClaimId) {
        expenseclaimService.deleteExpenseClaim(expenseClaimId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/entries/{expenseEntryId}")
    public ResponseEntity<Void> deleteExpenseEntry(@PathVariable Long expenseEntryId) {
        expenseclaimService.deleteExpenseEntry(expenseEntryId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{expenseClaimId}")
    public ResponseEntity<Void> patchUpdateExpenseClaim(
            @PathVariable Long expenseClaimId,
            @RequestBody Map<String, Object> expenseClaimMap) {
        expenseclaimService.patchUpdateExpenseClaim(expenseClaimId, expenseClaimMap);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/entries/{expenseEntryId}")
    public ResponseEntity<Void> patchUpdateExpenseEntry(
            @PathVariable Long expenseEntryId,
            @RequestBody Map<String, Object> expenseEntryMap) {
        expenseclaimService.patchUpdateExpenseEntry(expenseEntryId, expenseEntryMap);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
