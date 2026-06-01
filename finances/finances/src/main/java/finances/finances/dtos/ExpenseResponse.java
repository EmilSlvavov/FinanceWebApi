package finances.finances.dtos;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "Expense details returned by the API")
public class ExpenseResponse {

    @Schema(description = "Expense ID")
    private Integer id;

    @Schema(description = "ID of the linked expense category")
    private Integer expenseCategoryId;

    @Schema(description = "Name of the expense type from the linked category")
    private String expenseCategoryType;

    @Schema(description = "Expense amount")
    private Double amount;

    @Schema(description = "Whether this is a recurring expense")
    private Boolean isRecurring;

    @Schema(description = "Date and time the expense occurred")
    private LocalDateTime expenseDate;

    @Schema(description = "Optional description")
    private String description;
}