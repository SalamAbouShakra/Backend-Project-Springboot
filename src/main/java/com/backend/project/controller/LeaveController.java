package com.backend.project.controller;

import com.backend.project.modelDTO.LeaveDTO;
import com.backend.project.modelDTO.LeaveDateRangeDTO;
import com.backend.project.modelDTO.LeaveTypeEmployeeDTO;
import com.backend.project.modelDTO.LeavetypeDTO;
import com.backend.project.service.LeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/leaves")
@CrossOrigin(origins = "http://localhost:4200")
public class LeaveController {
    @Autowired
    private LeaveService leaveService;

    @GetMapping("/leaveTypes")
    public ResponseEntity<List<LeavetypeDTO>> getAllLeaveTypes() {
        List<LeavetypeDTO> leaveTypes = leaveService.getAllLeaveTypes();
        return new ResponseEntity<>(leaveTypes, HttpStatus.OK);
    }

    @PostMapping("/submitLeave")
    public ResponseEntity<LeaveDTO> submitLeave(@RequestBody LeaveDTO leaveDTO) {
        LeaveDTO submittedLeave = leaveService.submitLeave(leaveDTO);
        return new ResponseEntity<>(submittedLeave, HttpStatus.CREATED);
    }

    @PostMapping("/employeeLeaves")
    public ResponseEntity<List<LeaveDTO>> getLeavesByEmployeeAndDateRange(@RequestBody LeaveDateRangeDTO dateRangeDTO) {
        List<LeaveDTO> leaves = leaveService.getLeavesByEmployeeAndDateRange(
                dateRangeDTO.getEmployeeId(),
                dateRangeDTO.getFromDate(),
                dateRangeDTO.getToDate());
        return new ResponseEntity<>(leaves, HttpStatus.OK);
    }

    @GetMapping("/leavesByLeaveTypeAndEmployee")
    public ResponseEntity<Page<LeaveDTO>> getLeavesByLeaveTypeAndEmployee(
            @RequestParam Long leaveTypeId,
            @RequestParam Long employeeId,
            @RequestParam int page,
            @RequestParam int size
    ) {
        Pageable pageable = PageRequest.of(page, size);

        Page<LeaveDTO> leaves = leaveService.getLeavesByLeaveTypeAndEmployee(leaveTypeId, employeeId, pageable);

        return new ResponseEntity<>(leaves, HttpStatus.OK);
    }

    @GetMapping("/allLeaves")
    public ResponseEntity<List<LeaveDTO>> getAllLeaves() {
        List<LeaveDTO> leaves = leaveService.getAllLeaves();
        return new ResponseEntity<>(leaves, HttpStatus.OK);
    }

    @DeleteMapping("/deleteLeave/{leaveId}")
    public ResponseEntity<Void> deleteLeave(@PathVariable Long leaveId) {
        leaveService.deleteLeave(leaveId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/patchUpdateLeave/{leaveId}")
    public ResponseEntity<Void> patchUpdateLeave(@PathVariable Long leaveId, @RequestBody Map<String, Object> leaveMap) {
        leaveService.patchUpdateLeave(leaveId, leaveMap);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
