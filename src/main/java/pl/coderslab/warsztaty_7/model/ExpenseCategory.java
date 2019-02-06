package pl.coderslab.warsztaty_7.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "expense_categories")
public class ExpenseCategory extends Auditable {

    @Id
    @GeneratedValue
    private Long id;

    @NotEmpty
    @Size(max = 100)
    private String name;

    @Column(name = "is_global")
    private boolean isGlobal;

    @OneToMany(mappedBy = "expenseCategory", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Expense> expenseList = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "budget_id")
    private Budget budget;

    @OneToMany(mappedBy = "expenseCategory", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Target> targetList = new ArrayList<>();

    public ExpenseCategory() {}

    public ExpenseCategory(String name, boolean isGlobal) {
        this.name = name;
        this.isGlobal = isGlobal;
    }

    //TODO: do usunięcia?
    public ExpenseCategory(Long id, String name, boolean isGlobal) {
        this.id = id;
        this.name = name;
        this.isGlobal = isGlobal;
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

    public boolean isGlobal() {
        return isGlobal;
    }

    public void setGlobal(boolean global) {
        isGlobal = global;
    }

    public List<Expense> getExpenseList() {
        return expenseList;
    }

    public void setExpenseList(List<Expense> expenseList) {
        this.expenseList = expenseList;
    }

    public void addExpense(Expense expense) {
        this.expenseList.add(expense);
        expense.setExpenseCategory(this);
    }

    public Budget getBudget() {
        return budget;
    }

    public void setBudget(Budget budget) {
        this.budget = budget;
    }

    public List<Target> getTargetList() {
        return targetList;
    }

    public void setTargetList(List<Target> targetList) {
        this.targetList = targetList;
    }

    public void addTarget(Target target) {
        this.targetList.add(target);
        target.setExpenseCategory(this);
    }

    @Override
    public String toString() {
        return "ExpenseCategory{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", isGlobal=" + isGlobal +
                ", createdByUserId=" + createdByUserId +
                ", creationDate=" + creationDate +
                ", updatedByUserId=" + updatedByUserId +
                ", updatedDate=" + updatedDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ExpenseCategory)) return false;
        if (!super.equals(o)) return false;
        ExpenseCategory that = (ExpenseCategory) o;
        return isGlobal() == that.isGlobal() &&
                Objects.equals(getId(), that.getId()) &&
                Objects.equals(getName(), that.getName()) &&
                Objects.equals(getExpenseList(), that.getExpenseList()) &&
                Objects.equals(getBudget().getId(), that.getBudget().getId()) &&
                Objects.equals(getTargetList(), that.getTargetList());
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), getId(), getName(), isGlobal(), getExpenseList(), getBudget(), getTargetList());
    }
}
