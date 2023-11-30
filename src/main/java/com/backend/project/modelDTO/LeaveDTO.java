package com.backend.project.modelDTO;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.Data;

import java.sql.Date;

@Data
public class LeaveDTO {

    private long id;

    private Long leaveTypeId;

    private Date leaveFrom;

    private Date leaveTo;

    private int numberOfDays;

    private String note;

    private Long employeeId;
}
