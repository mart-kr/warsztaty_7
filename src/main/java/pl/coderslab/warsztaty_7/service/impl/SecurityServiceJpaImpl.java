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

    private UserService userService;

    @Autowired
    public SecurityServiceJpaImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Budget findEntityBudget(Auditable entity) {
        final boolean bankAccountInstance = entity instanceof BankAccount;
        final boolean expenseCategoryInstance = entity instanceof ExpenseCategory;
        final boolean expenseInstance = entity instanceof Expense;
        final boolean incomeInstance = entity instanceof Income;
        final boolean incomeCategoryInstance = entity instanceof IncomeCategory;
        final boolean receiptInstance = entity instanceof Receipt;
        final boolean targetInstance = entity instanceof Target;
        final boolean budgetInstance = entity instanceof Budget;
//        final boolean transferInstance = entity instanceof Transfer;
        if (bankAccountInstance) {
            BankAccount bankAccount = (BankAccount) entity;
            return bankAccount.getBudget();
        } else if (expenseCategoryInstance) {
            ExpenseCategory expenseCategory = (ExpenseCategory) entity;
            return expenseCategory.getBudget();
        } else if (expenseInstance) {
            Expense expense = (Expense) entity;
            return expense.getExpenseCategory().getBudget();
        } else if (incomeInstance) {
            Income income = (Income) entity;
            return income.getBankAccount().getBudget();
        } else if (incomeCategoryInstance) {
            IncomeCategory incomeCategory = (IncomeCategory) entity;
            return incomeCategory.getBudget();
        } else if (receiptInstance) {
            Receipt receipt = (Receipt) entity;
            return receipt.getBankAccount().getBudget();
        } else if (targetInstance) {
            Target target = (Target) entity;
            return target.getExpenseCategory().getBudget();
//        } else if (transferInstance) {
//            Transfer transfer = (Transfer) entity;
//            return transfer.getBankAccount().getBudget();
        } else if (budgetInstance) {
            return (Budget) entity;
        } else {
            throw new IllegalArgumentException("This object type is not supported.");
        }
    }

    @Override
    public boolean canEditEntity(User authenticatedUser, Auditable entity) {
        if (authenticatedUser == null || entity == null) {
            return false;
        }
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
        if (authenticatedUser == null || entity == null) {
            return false;
        }
        if (entity instanceof Budget) {
            return false;
        }
        for (Role role : authenticatedUser.getRoles()) {
            if(role.getName().equals("ADMIN")) {
                return true;
            }
        }
        Budget budget = findEntityBudget(entity);
        return budget != null && budget.getUsers().contains(authenticatedUser);
    }

    @Override
    public boolean canDeleteBudget(User authenticatedUser, Auditable budget) {
        if (authenticatedUser == null || budget == null) {
            return false;
        }
        for (Role role : authenticatedUser.getRoles()) {
            if(role.getName().equals("ADMIN")) {
                return true;
            }
        }
        Budget budgetInstance = (Budget) budget;
        if (budget.getCreatedByUserId().equals(authenticatedUser.getId())) {
            return true;
        } else {
            return budgetInstance.getUsers().contains(authenticatedUser) && budgetInstance.getUsers().size() == 1;
        }
    }
}
