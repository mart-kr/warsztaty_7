package pl.coderslab.warsztaty_7.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "receipts")
public class Receipt {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private BigDecimal amount;

    @Column(name = "date_of_payment")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dateOfPayment;

    @Column(name = "place_of_payment")
    private String placeOfPayment;

    @Column
    private String note;

    @OneToMany(mappedBy = "receipt", cascade = CascadeType.ALL)
    private List<Expense> expenses;

    //TODO: relacja z Account

    //TODO: relacja z User

    public Receipt() {}

    public Receipt(BigDecimal amount, LocalDate dateOfPayment, String placeOfPayment, String note) {
        this.amount = amount;
        this.dateOfPayment = dateOfPayment;
        this.placeOfPayment = placeOfPayment;
        this.note = note;
    }

    //TODO: do usunięcia?
    public Receipt(Long id, BigDecimal amount, LocalDate dateOfPayment, String placeOfPayment, String note) {
        this.id = id;
        this.amount = amount;
        this.dateOfPayment = dateOfPayment;
        this.placeOfPayment = placeOfPayment;
        this.note = note;
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

    public LocalDate getDateOfPayment() {
        return dateOfPayment;
    }

    public void setDateOfPayment(LocalDate dateOfPayment) {
        this.dateOfPayment = dateOfPayment;
    }

    public String getPlaceOfPayment() {
        return placeOfPayment;
    }

    public void setPlaceOfPayment(String placeOfPayment) {
        this.placeOfPayment = placeOfPayment;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public List<Expense> getExpenses() {
        return expenses;
    }

    public void setExpenses(List<Expense> expenses) {
        this.expenses = expenses;
    }

    public void addExpense(Expense expense) {
        this.expenses.add(expense);
        expense.setReceipt(this);
    }

    @Override
    public String toString() {
        return "Receipt{" +
                "id=" + id +
                ", amount=" + amount +
                ", dateOfPayment=" + dateOfPayment +
                ", placeOfPayment='" + placeOfPayment + '\'' +
                ", note='" + note + '\'' +
                '}';
    }
}
