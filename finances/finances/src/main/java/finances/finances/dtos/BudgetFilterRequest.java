package finances.finances.dtos;

import finances.finances.enums.CurrencyType;
import lombok.Data;

@Data
public class BudgetFilterRequest {
    private CurrencyType currency;
    private Boolean isRecurring;
    private Double minValue;
    private Double maxValue;

    private int page = 0;
    private int size = 10;
    private String sortBy = "createdAt";
    private String sortDir = "desc";
}