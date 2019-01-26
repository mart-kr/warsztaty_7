package pl.coderslab.warsztaty_7.model;

import javax.persistence.*;
import java.math.BigDecimal;

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


    public Target() {
    }

    public Target(BigDecimal amount, ExpenseCategory expenseCategory) {
        this.amount = amount;
        this.expenseCategory = expenseCategory;
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

    public ExpenseCategory getExpenseCategory() {
        return expenseCategory;
    }

    public void setExpenseCategory(ExpenseCategory expenseCategory) {
        this.expenseCategory = expenseCategory;
    }

    @Override
    public String toString() {
        return "Target{" +
                "id=" + id +
                ", amount=" + amount +
                ", expenseCategory=" + expenseCategory +
                ", createdByUserId=" + createdByUserId +
                ", creationDate=" + creationDate +
                ", updatedByUserId=" + updatedByUserId +
                ", updatedDate=" + updatedDate +
                '}';
    }
}
