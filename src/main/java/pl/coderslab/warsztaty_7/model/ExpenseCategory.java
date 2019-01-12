package pl.coderslab.warsztaty_7.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "expense_categories")
public class ExpenseCategory {

    @Id
    @GeneratedValue
    private Long id;

    @NotEmpty
    @Size(max = 100)
    private String name;

    @Column(name = "is_global")
    private boolean isGlobal;

    @Column(name = "created_by")
    private Long createdBy;

    @OneToMany(mappedBy = "expenseCategory", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    List<Expense> expenseList = new ArrayList<>();

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

    public ExpenseCategory() {}

    public ExpenseCategory(String name, boolean isGlobal, Long createdBy) {
        this.name = name;
        this.isGlobal = isGlobal;
        this.createdBy = createdBy;
    }

    //TODO: do usunięcia?
    public ExpenseCategory(Long id, String name, boolean isGlobal, Long createdBy) {
        this.id = id;
        this.name = name;
        this.isGlobal = isGlobal;
        this.createdBy = createdBy;
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

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
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


    @Override
    public String toString() {
        return "ExpenseCategory{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", isGlobal=" + isGlobal +
                ", createdBy=" + createdBy +
                '}';
    }
}
