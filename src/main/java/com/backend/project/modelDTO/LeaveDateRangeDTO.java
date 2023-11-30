package com.backend.project.modelDTO;

import lombok.Data;

import java.sql.Date;

@Data
public class LeaveDateRangeDTO {
    private Long employeeId;
    private Date fromDate;
    private Date toDate;
}
