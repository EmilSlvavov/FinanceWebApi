package finances.finances.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ExpenseFilterRequest {
    private Integer categoryId;
    private Boolean isRecurring;
    private Double minAmount;
    private Double maxAmount;
    private LocalDateTime dateFrom;
    private LocalDateTime dateTo;
    private String description;

    private int page = 0;
    private int size = 10;          // fallback only — caller overrides with ?size=5
    private String sortBy = "expenseDate";
    private String sortDir = "desc";
}