package com.prashant.smatpersonalfinance.dto;




import lombok.*;

import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class RequestDto {
 private String  type;
   private LocalDate startDate;
    private LocalDate endDate;
    private String   sortField;
    private String   sortOrder;
    private String   search;
}
