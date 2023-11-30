package com.backend.project.modelDTO;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import lombok.Data;

@Data
public class ExpensetypeDTO {
    private long id;

    private String name;
}
