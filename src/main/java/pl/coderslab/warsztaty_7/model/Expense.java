package pl.coderslab.warsztaty_7.model;


import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "expenses")
public class Expense {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private BigDecimal value;

    @ManyToOne
    @JoinColumn(name = "expense_categories_id")
    private ExpenseCategory expenseCategory;

    //TODO: dodać relację do Receipt

    public Expense() {}

    public Expense(BigDecimal value, ExpenseCategory expenseCategory) {
        this.value = value;
        this.expenseCategory = expenseCategory;
    }

    //TODO: do usunięcia?
    public Expense(Long id, BigDecimal value, ExpenseCategory expenseCategory) {
        this.id = id;
        this.value = value;
        this.expenseCategory = expenseCategory;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public ExpenseCategory getExpenseCategory() {
        return expenseCategory;
    }

    public void setExpenseCategory(ExpenseCategory expenseCategory) {
        this.expenseCategory = expenseCategory;
    }

    @Override
    public String toString() {
        return "Expense{" +
                "id=" + id +
                ", value=" + value +
                ", expenseCategory=" + expenseCategory.toString() +
                '}';
    }
}
