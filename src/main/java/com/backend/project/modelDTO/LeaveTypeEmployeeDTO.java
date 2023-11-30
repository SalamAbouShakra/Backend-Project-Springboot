package com.backend.project.modelDTO;

import lombok.Data;

@Data
public class LeaveTypeEmployeeDTO {
    private Long leaveTypeId;
    private Long employeeId;
    private int page;
    private int size;
}
