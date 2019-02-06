package pl.coderslab.warsztaty_7.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "targets")
public class Target extends Auditable {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private BigDecimal amount;

    @ManyToOne
    @JoinColumn(name = "expense_category_id")
    private ExpenseCategory expenseCategory;

    @Column(name = "start_date")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate startDate;

    @Column(name = "end_date")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate endDate;

    @ManyToOne
    @JoinColumn(name = "budget_id")
    private Budget budget;

    public Target() {
    }

    public Target(BigDecimal amount, LocalDate startDate, ExpenseCategory expenseCategory, Budget budget) {
        this.amount = amount;
        this.startDate = startDate;
        this.expenseCategory = expenseCategory;
        this.budget = budget;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public ExpenseCategory getExpenseCategory() {
        return expenseCategory;
    }

    public void setExpenseCategory(ExpenseCategory expenseCategory) {
        this.expenseCategory = expenseCategory;
    }

    public Budget getBudget() {
        return budget;
    }

    public void setBudget(Budget budget) {
        this.budget = budget;
    }

    @Override
    public String toString() {
        return "Target{" +
                "id=" + id +
                ", amount=" + amount +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", expenseCategory=" + expenseCategory +
                ", budget=" + budget +
                ", createdByUserId=" + createdByUserId +
                ", creationDate=" + creationDate +
                ", updatedByUserId=" + updatedByUserId +
                ", updatedDate=" + updatedDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Target)) return false;
        if (!super.equals(o)) return false;
        Target target = (Target) o;
        return Objects.equals(getId(), target.getId()) &&
                Objects.equals(getAmount(), target.getAmount()) &&
                Objects.equals(getExpenseCategory(), target.getExpenseCategory()) &&
                Objects.equals(getStartDate(), target.getStartDate()) &&
                Objects.equals(getEndDate(), target.getEndDate()) &&
                Objects.equals(getBudget().getId(), target.getBudget().getId());
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), getId(), getAmount(), getExpenseCategory());
    }
}
