package com.backend.project.controller;

import com.backend.project.modelDTO.LeavetypeDTO;
import com.backend.project.service.LeavetypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/leavetypes")
public class LeavetypeController {
    @Autowired
    private LeavetypeService leavetypeService;

    @PostMapping("/createLeaveType")
    public ResponseEntity<LeavetypeDTO> createLeaveType(@RequestBody LeavetypeDTO leavetypeDTO) {
        LeavetypeDTO createdLeaveType = leavetypeService.createLeaveType(leavetypeDTO);
        return new ResponseEntity<>(createdLeaveType, HttpStatus.CREATED);
    }

    @GetMapping("/{leaveTypeId}")
    public ResponseEntity<LeavetypeDTO> getLeaveTypeById(@PathVariable Long leaveTypeId) {
        LeavetypeDTO leaveType = leavetypeService.getLeaveTypeById(leaveTypeId);
        return new ResponseEntity<>(leaveType, HttpStatus.OK);
    }

    @DeleteMapping("/deleteLeaveType/{leaveTypeId}")
    public ResponseEntity<Void> deleteLeaveType(@PathVariable Long leaveTypeId) {
        leavetypeService.deleteLeaveType(leaveTypeId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/patchUpdateLeavetype/{leaveTypeId}")
    public ResponseEntity<Void> patchUpdateLeavetype(@PathVariable Long leaveTypeId, @RequestBody Map<String, Object> leavetypeMap) {
        leavetypeService.patchUpdateLeavetype(leaveTypeId, leavetypeMap);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
