package pl.coderslab.warsztaty_7.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import pl.coderslab.warsztaty_7.model.*;
import pl.coderslab.warsztaty_7.service.SecurityService;
import pl.coderslab.warsztaty_7.service.UserService;

@Service
@Primary
public class SecurityServiceJpaImpl implements SecurityService<User, Auditable> {

    private BankAccountServiceJpaImpl bankAccountServiceJpa;
    private BudgetServiceJpaImpl budgetServiceJpa;
    private ExpenseCategoryServiceJpaImpl expenseCategoryServiceJpa;
    private ExpenseServiceJpaImpl expenseServiceJpa;
    private IncomeCategoryServiceJpaImpl incomeCategoryServiceJpa;
    private IncomeServiceJpaImpl incomeServiceJpa;
    private ReceiptServiceJpaImpl receiptServiceJpa;
    private UserService userService;

    @Autowired
    public SecurityServiceJpaImpl(BankAccountServiceJpaImpl bankAccountServiceJpa, BudgetServiceJpaImpl budgetServiceJpa, ExpenseCategoryServiceJpaImpl expenseCategoryServiceJpa, ExpenseServiceJpaImpl expenseServiceJpa, IncomeCategoryServiceJpaImpl incomeCategoryServiceJpa, IncomeServiceJpaImpl incomeServiceJpa, ReceiptServiceJpaImpl receiptServiceJpa) {
        this.bankAccountServiceJpa = bankAccountServiceJpa;
        this.budgetServiceJpa = budgetServiceJpa;
        this.expenseCategoryServiceJpa = expenseCategoryServiceJpa;
        this.expenseServiceJpa = expenseServiceJpa;
        this.incomeCategoryServiceJpa = incomeCategoryServiceJpa;
        this.incomeServiceJpa = incomeServiceJpa;
        this.receiptServiceJpa = receiptServiceJpa;
    }

    @Override
    public Budget findEntityBudget(Auditable entity) {
        final boolean bankAccountInstance = entity instanceof BankAccount;
        final boolean expenseCategoryInstance = entity instanceof ExpenseCategory;
        final boolean expenseInstance = entity instanceof Expense;
        final boolean incomeInstance = entity instanceof Income;
        final boolean incomeCategoryInstance = entity instanceof IncomeCategory;
        final boolean receiptInstance = entity instanceof Receipt;
        User owner;
        if (bankAccountInstance ||
                expenseCategoryInstance ||
                expenseInstance ||
                incomeInstance ||
                incomeCategoryInstance ||
                receiptInstance) {
            owner = userService.findUserById(entity.getCreatedByUserId());
            return owner.getBudget();
        } else {
            throw new IllegalArgumentException("Provided object type is not supported. Object must have field CreatedByUserId");
        }
    }

    @Override
    public boolean canEditEntity(User authenticatedUser, Auditable entity) {
        // ADMIN może wszystko
        for (Role role : authenticatedUser.getRoles()) {
            if(role.getName().equals("ADMIN")) {
                return true;
            }
        }
        Budget budget = findEntityBudget(entity);
        return budget != null && budget.getUsers().contains(authenticatedUser);
    }

    @Override
    public boolean canDeleteEntity(User authenticatedUser, Auditable entity) {
        // ADMIN może wszystko
        for (Role role : authenticatedUser.getRoles()) {
            if(role.getName().equals("ADMIN")) {
                return true;
            }
        }
        if (entity instanceof Budget) {
            return false;
        }
        Budget budget = findEntityBudget(entity);
        return budget != null && budget.getUsers().contains(authenticatedUser);
    }

    @Override
    public boolean canDeleteBudget(User authenticatedUser, Auditable budget) {
        // ADMIN może wszystko
        for (Role role : authenticatedUser.getRoles()) {
            if(role.getName().equals("ADMIN")) {
                return true;
            }
        }
        return budget.getCreatedByUserId().equals(authenticatedUser.getId());
    }
}
