package com.prashant.smatpersonalfinance.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExpanseDTO {
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
