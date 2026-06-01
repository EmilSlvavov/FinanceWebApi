package com.finances.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BudgetResponse {
    private Integer id;
    private String name;
    private Double value;
    private String currency;
    private Boolean isRecurring;
    private LocalDateTime createdAt;
    private Double totalSpent;
    private Double remainingBalance;
}
