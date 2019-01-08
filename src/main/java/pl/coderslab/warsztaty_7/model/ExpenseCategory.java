package pl.coderslab.warsztaty_7.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "expense_categories")

public class ExpenseCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Size(max = 100)
    private String name;

    @Column(name = "is_global")
    private boolean isGlobal;

    @Column(name = "created_by")
    private Long createdBy;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdDate;

    @OneToMany(mappedBy = "expenseCategory", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    List<Expense> expenseList = new ArrayList<>();

    public ExpenseCategory() {}

    public ExpenseCategory(String name, boolean isGlobal, Long createdBy, LocalDateTime createdDate) {
        this.name = name;
        this.isGlobal = isGlobal;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
    }

    //TODO: do usunięcia?
    public ExpenseCategory(Long id, String name, boolean isGlobal, Long createdBy, LocalDateTime createdDate) {
        this.id = id;
        this.name = name;
        this.isGlobal = isGlobal;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
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

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public String toString() {
        return "ExpenseCategory{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", isGlobal=" + isGlobal +
                ", createdBy=" + createdBy +
                ", createdDate=" + createdDate +
                '}';
    }
}
