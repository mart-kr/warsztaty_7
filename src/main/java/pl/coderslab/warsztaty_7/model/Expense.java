package pl.coderslab.warsztaty_7.model;


import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "expenses")
public class Expense extends Auditable {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private BigDecimal amount;

    @ManyToOne
    @JoinColumn(name = "expense_category_id")
    private ExpenseCategory expenseCategory;

    @ManyToOne
    @JoinColumn(name = "receipt_id")
    private Receipt receipt;

    public Expense() {}

    public Expense(BigDecimal amount, ExpenseCategory expenseCategory) {
        this.amount = amount;
        this.expenseCategory = expenseCategory;
    }

    //TODO: do usunięcia?
    public Expense(Long id, BigDecimal amount, ExpenseCategory expenseCategory, Receipt receipt) {
        this.id = id;
        this.amount = amount;
        this.expenseCategory = expenseCategory;
        this.receipt = receipt;
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

    public Receipt getReceipt() {
        return receipt;
    }

    public void setReceipt(Receipt receipt) {
        this.receipt = receipt;
    }

    @Override
    public String toString() {
        return "Expense{" +
                "id=" + id +
                ", amount=" + amount +
                ", expenseCategory=" + expenseCategory +
                ", receipt=" + receipt +
                ", createdByUserId=" + createdByUserId +
                ", creationDate=" + creationDate +
                ", updatedByUserId=" + updatedByUserId +
                ", updatedDate=" + updatedDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Expense)) return false;
        if (!super.equals(o)) return false;
        Expense expense = (Expense) o;
        return Objects.equals(getId(), expense.getId()) &&
                Objects.equals(getAmount(), expense.getAmount()) &&
                Objects.equals(getExpenseCategory(), expense.getExpenseCategory()) &&
                Objects.equals(getReceipt(), expense.getReceipt());
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), getId(), getAmount(), getExpenseCategory(), getReceipt());
    }
}
