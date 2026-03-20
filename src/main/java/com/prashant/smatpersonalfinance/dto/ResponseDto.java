package com.prashant.smatpersonalfinance.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.*;




@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class ResponseDto {
    private  String name;
    private LocalDate date;
    private String type;
    private BigDecimal amount;
}
