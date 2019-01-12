package pl.coderslab.warsztaty_7.model;


import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "expenses")
public class Expense {

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

    @CreatedBy
    @Column(name = "created_by", insertable = false, updatable = false)
    private Long createdUserId;

    @CreationTimestamp
    //@CreatedDate
    @Column(name = "created_date", nullable = false, updatable = false )
    private LocalDateTime createdDate;

    @LastModifiedBy
    @Column(name = "updated_by")
    private Long lastUpdatedUserId;

    @UpdateTimestamp
    //@LastModifiedDate
    @Column(name = "updated_date")
    private LocalDateTime lastModifiedDate;

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
                '}';
    }
}
