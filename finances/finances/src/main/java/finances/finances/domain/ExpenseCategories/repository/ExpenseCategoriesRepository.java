package finances.finances.domain.ExpenseCategories.repository;

import finances.finances.domain.ExpenseCategories.entity.ExpenseCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ExpenseCategoriesRepository extends JpaRepository<ExpenseCategory, Integer>, JpaSpecificationExecutor<ExpenseCategory> {

}
