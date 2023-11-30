package com.backend.project.repos;

import com.backend.project.Entities.EmployeeEntity;
import com.backend.project.Entities.LeaveEntity;
import com.backend.project.Entities.LeavetypeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Date;
import java.util.List;

public interface LeaveRepo extends JpaRepository<LeaveEntity, Long> {
    List<LeaveEntity> findByEmployeeIdAndLeaveFromBetween(Long employeeId, Date fromDate, Date toDate);
    List<LeaveEntity> findByLeaveTypeIdAndEmployeeId(Long leaveTypeId, Long employeeId, Pageable pageable);
}
