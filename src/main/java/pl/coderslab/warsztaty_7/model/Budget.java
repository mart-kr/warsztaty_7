package pl.coderslab.warsztaty_7.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "budgets")
public class Budget extends Auditable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @OneToMany(mappedBy = "budget", cascade = CascadeType.ALL)
    private List<BankAccount> bankAccounts = new ArrayList<>();

    @OneToMany(mappedBy = "budget", cascade = CascadeType.ALL, fetch = FetchType.EAGER) //TODO: pomyśleć nad lepszym rozwiązaniem (problem przy ponownym logowaniu użytkownika)
    private List<User> users = new ArrayList<>();

    @OneToMany(mappedBy = "budget", cascade = CascadeType.ALL)
    private List<ExpenseCategory> expenseCategories = new ArrayList<>();

    @OneToMany(mappedBy = "budget", cascade = CascadeType.ALL)
    private List<IncomeCategory> incomeCategories = new ArrayList<>();

    //TODO: transfery

    //TODO: targety


    public Budget() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<BankAccount> getBankAccounts() {
        return bankAccounts;
    }

    public void setBankAccounts(List<BankAccount> bankAccounts) {
        this.bankAccounts = bankAccounts;
    }

    public void addBankAccount(BankAccount bankAccount){
        bankAccounts.add(bankAccount);
        bankAccount.setBudget(this);
    }

    public void deleteBankAccount(BankAccount bankAccount){
        bankAccounts.remove(bankAccount);
        bankAccount.setBudget(null); //ToDO: zmienić usuwanie na zmianę flagi
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public void addUser(User user) {
        users.add(user);
        user.setBudget(this);
    }

    public void deleteUser(User user) {
       users.remove(user);
       user.setBudget(null);
    }

    public List<ExpenseCategory> getExpenseCategories() {
        return expenseCategories;
    }

    public void setExpenseCategories(List<ExpenseCategory> expenseCategories) {
        this.expenseCategories = expenseCategories;
    }

    public void addExpenseCategory(ExpenseCategory expenseCategory) {
        expenseCategories.add(expenseCategory);
        expenseCategory.setBudget(this);
    }

    public void deleteExpenseCategory(ExpenseCategory expenseCategory) {
        expenseCategories.remove(expenseCategory);
        expenseCategory.setBudget(null);
    }

    public List<IncomeCategory> getIncomeCategories() {
        return incomeCategories;
    }

    public void setIncomeCategories(List<IncomeCategory> incomeCategories) {
        this.incomeCategories = incomeCategories;
    }

    public void addIncomeCategory(IncomeCategory incomeCategory) {
        incomeCategories.add(incomeCategory);
        incomeCategory.setBudget(this);
    }

    public void deleteIncomeCategory(IncomeCategory incomeCategory) {
        incomeCategories.remove(incomeCategory);
        incomeCategory.setBudget(null);
    }
}
