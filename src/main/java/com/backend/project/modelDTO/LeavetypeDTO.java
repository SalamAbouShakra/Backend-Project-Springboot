package com.backend.project.modelDTO;


import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import lombok.Data;

@Data
public class LeavetypeDTO {
    private long id;

    private String name;
}
