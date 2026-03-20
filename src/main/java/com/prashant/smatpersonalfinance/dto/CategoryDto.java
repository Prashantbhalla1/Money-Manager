package com.prashant.smatpersonalfinance.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDto {
    private Long id;
    private Long profileId;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String type;
    private String icon;

}
