package finances.finances.domain.expense.service;

import finances.finances.domain.ExpenseCategories.entity.ExpenseCategory;
import finances.finances.domain.ExpenseCategories.repository.ExpenseCategoriesRepository;
import finances.finances.domain.expense.entity.Expense;
import finances.finances.domain.expense.repository.ExpenseRepository;
import finances.finances.domain.user.entity.User;
import finances.finances.dtos.ExpenseFilterRequest;
import finances.finances.dtos.ExpenseRequest;
import finances.finances.dtos.ExpenseResponse;
import finances.finances.security.SecurityUtils;
import finances.finances.specifications.BaseSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final ExpenseCategoriesRepository expenseCategoryRepository;
    private final SecurityUtils securityUtils;

    @Async("taskExecutor")
    public CompletableFuture<ExpenseResponse> create(ExpenseRequest request) {
        ExpenseCategory category = getCategoryOrThrow(request.getExpenseCategoryId());
        securityUtils.assertOwnerOrAdmin(category.getUser().getId());
        Expense expense = new Expense();
        applyRequest(expense, request);
        return CompletableFuture.completedFuture(toResponse(expenseRepository.save(expense)));
    }

    @Async("taskExecutor")
    public CompletableFuture<Page<ExpenseResponse>> findAll(ExpenseFilterRequest filter) {
        User currentUser = securityUtils.getCurrentUser();

        Specification<Expense> ownerFilter = securityUtils.currentUserIsAdmin() ? null :
                (root, query, cb) -> cb.equal(
                        root.get("expenseCategory").get("user").get("id"), currentUser.getId());

        Specification<Expense> spec = Specification.allOf(
                BaseSpecification.equal("isRecurring", filter.getIsRecurring()),
                BaseSpecification.between("amount", filter.getMinAmount(), filter.getMaxAmount()),
                BaseSpecification.dateTimeBetween("expenseDate", filter.getDateFrom(), filter.getDateTo()),
                BaseSpecification.joinEqual("expenseCategory", "id", filter.getCategoryId()),
                BaseSpecification.contains("description", filter.getDescription()),
                ownerFilter
        );

        Sort sort = filter.getSortDir().equalsIgnoreCase("asc")
                ? Sort.by(filter.getSortBy()).ascending()
                : Sort.by(filter.getSortBy()).descending();

        Pageable pageable = PageRequest.of(filter.getPage(), filter.getSize(), sort);

        return CompletableFuture.completedFuture(
                expenseRepository.findAll(spec, pageable).map(this::toResponse)
        );
    }

    @Async("taskExecutor")
    public CompletableFuture<ExpenseResponse> findById(Integer id) {
        Expense expense = getOrThrow(id);
        securityUtils.assertOwnerOrAdmin(expense.getExpenseCategory().getUser().getId());
        return CompletableFuture.completedFuture(toResponse(expense));
    }

    @Async("taskExecutor")
    public CompletableFuture<ExpenseResponse> update(Integer id, ExpenseRequest request) {
        Expense expense = getOrThrow(id);
        securityUtils.assertOwnerOrAdmin(expense.getExpenseCategory().getUser().getId());
        applyRequest(expense, request);
        return CompletableFuture.completedFuture(toResponse(expenseRepository.save(expense)));
    }

    @Async("taskExecutor")
    public CompletableFuture<Void> delete(Integer id) {
        Expense expense = getOrThrow(id);
        securityUtils.assertOwnerOrAdmin(expense.getExpenseCategory().getUser().getId());
        expenseRepository.delete(expense);
        return CompletableFuture.completedFuture(null);
    }

    // ── Private helpers ───────────────────────────────────────────────────────

    private void applyRequest(Expense expense, ExpenseRequest request) {
        ExpenseCategory category = getCategoryOrThrow(request.getExpenseCategoryId());
        expense.setExpenseCategory(category);
        expense.setAmount(request.getAmount());
        expense.setExpenseDate(request.getExpenseDate());
        expense.setIsRecurring(request.getIsRecurring() != null && request.getIsRecurring());
        expense.setDescription(request.getDescription());
    }

    private Expense getOrThrow(Integer id) {
        return expenseRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Expense not found with id: " + id));
    }

    private ExpenseCategory getCategoryOrThrow(Integer categoryId) {
        return expenseCategoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Expense category not found with id: " + categoryId));
    }

    private ExpenseResponse toResponse(Expense expense) {
        ExpenseResponse response = new ExpenseResponse();
        response.setId(expense.getId());
        response.setAmount(expense.getAmount());
        response.setIsRecurring(expense.getIsRecurring());
        response.setExpenseDate(expense.getExpenseDate());
        response.setDescription(expense.getDescription());

        if (expense.getExpenseCategory() != null) {
            response.setExpenseCategoryId(expense.getExpenseCategory().getId());
            response.setExpenseCategoryType(
                    expense.getExpenseCategory().getExpenseType() != null
                            ? expense.getExpenseCategory().getExpenseType().name()
                            : null
            );
        }

        return response;
    }
}