package com.prashant.smatpersonalfinance.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IncomeDTO {
    private Long id;
    private BigDecimal amount;
    private String name;
    private Long profileId;
    private Long categoryId;
    private String categoryName;
    private LocalDate date;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


}
