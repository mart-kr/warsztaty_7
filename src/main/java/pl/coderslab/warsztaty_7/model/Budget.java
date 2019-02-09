package pl.coderslab.warsztaty_7.model;

import javax.persistence.*;
import java.util.*;

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

    @OneToMany(mappedBy = "budget", cascade = CascadeType.ALL)
    private List<Target> targets = new ArrayList<>();

    @OneToMany(mappedBy = "budget", cascade = CascadeType.ALL)
    private List<GlobalTarget> globalTargets = new ArrayList<>();

    //TODO: transfery


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

    public List<Target> getTargets() {
        return targets;
    }

    public void setTargets(List<Target> targets) {
        this.targets = targets;
    }

    public void addTarget(Target target) {
        targets.add(target);
        target.setBudget(this);
    }

    public void deleteTarget(Target target) {
        targets.remove(target);
        target.setBudget(null);
    }

    public List<GlobalTarget> getGlobalTargets() {
        return globalTargets;
    }

    public void setGlobalTargets(List<GlobalTarget> globalTargets) {
        this.globalTargets = globalTargets;
    }

    public void addGlobalTarget(GlobalTarget globalTarget) {
        globalTargets.add(globalTarget);
        globalTarget.setBudget(this);
    }

    public void deleteGlobalTarget(GlobalTarget globalTarget) {
        targets.remove(globalTarget);
        globalTarget.setBudget(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Budget)) return false;
        if (!super.equals(o)) return false;
        Budget budget = (Budget) o;
        return Objects.equals(getId(), budget.getId()) &&
                Objects.equals(getName(), budget.getName()) &&
                Objects.equals(getBankAccounts(), budget.getBankAccounts()) &&
                Objects.equals(getUsers(), budget.getUsers()) &&
                Objects.equals(getExpenseCategories(), budget.getExpenseCategories()) &&
                Objects.equals(getIncomeCategories(), budget.getIncomeCategories()) &&
                Objects.equals(getTargets(), budget.getTargets()) &&
                Objects.equals(getGlobalTargets(), budget.getGlobalTargets());
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), getId(), getName(), getBankAccounts(), getUsers(), getExpenseCategories(),
                getIncomeCategories(), getTargets(), getGlobalTargets());
    }


}
