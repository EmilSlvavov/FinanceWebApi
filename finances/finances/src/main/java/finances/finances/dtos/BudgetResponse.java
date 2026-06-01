package finances.finances.dtos;

import finances.finances.enums.CurrencyType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "Budget details returned by the API")
public class BudgetResponse {

    @Schema(description = "Budget ID")
    private Integer id;

    @Schema(description = "Budget name")
    private String name;

    @Schema(description = "Total budget amount")
    private Double value;

    @Schema(description = "ISO 4217 currency code")
    private CurrencyType currency;

    @Schema(description = "Whether this budget auto-renews")
    private Boolean isRecurring;

    @Schema(description = "Timestamp when the budget was created")
    private LocalDateTime createdAt;

    @Schema(description = "ID of the user this budget belongs to")
    private Integer userId;

    private Double spentAmount;       // total spent across all user's expenses
    private Double remainingAmount;   // value - spentAmount
    private Double spentPercent;      // (spentAmount / value) * 100
    private Boolean overBudget;       // true if spentAmount > value
    private String warning;           // human readable message, null if fine
}