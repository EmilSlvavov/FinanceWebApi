package finances.finances.dtos;
import finances.finances.enums.CurrencyType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Payload for creating or updating a budget")
public class BudgetRequest {

    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name must not exceed 100 characters")
    @Schema(description = "Descriptive name for the budget", example = "June 2024 Budget")
    private String name;

    @NotNull(message = "Value is required")
    @Positive(message = "Value must be positive")
    @Schema(description = "Total budget amount", example = "5000.00")
    private Double value;

    @NotNull(message = "Currency is required")
    private CurrencyType currency;

    @NotNull(message = "isRecurring is required")
    @Schema(description = "Whether this budget auto-renews each period", example = "false")
    private Boolean isRecurring;
}
