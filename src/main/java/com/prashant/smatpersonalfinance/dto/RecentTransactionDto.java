package com.prashant.smatpersonalfinance.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecentTransactionDto {
    private Long id;
    private String name;
    private BigDecimal amount;
    private String type;
    private LocalDate date;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
