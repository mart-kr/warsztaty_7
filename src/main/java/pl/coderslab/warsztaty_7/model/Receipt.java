package pl.coderslab.warsztaty_7.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "receipts")
public class Receipt extends Auditable implements Operation{

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
    private List<Expense> expenses = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "bank_account_id")
    private BankAccount bankAccount;

    @Transient
    private final String TYPE = "Wydatek";

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

    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    @Override
    public LocalDate getOperationDate() {
        return dateOfPayment;
    }

    @Override
    public String getType() {
        return TYPE;
    }

//    @Override
//    public String toString() {
//        return "Receipt{" +
//                "id=" + id +
//                ", amount=" + amount +
//                ", dateOfPayment=" + dateOfPayment +
//                ", placeOfPayment='" + placeOfPayment + '\'' +
//                ", note='" + note + '\'' +
//                ", expenses=" + expenses +
//                ", bankAccount=" + bankAccount +
//                ", TYPE='" + TYPE + '\'' +
//                ", createdByUserId=" + createdByUserId +
//                ", creationDate=" + creationDate +
//                ", updatedByUserId=" + updatedByUserId +
//                ", updatedDate=" + updatedDate +
//                '}';
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Receipt)) return false;
        if (!super.equals(o)) return false;
        Receipt receipt = (Receipt) o;
        return Objects.equals(getId(), receipt.getId()) &&
                Objects.equals(getAmount(), receipt.getAmount()) &&
                Objects.equals(getDateOfPayment(), receipt.getDateOfPayment()) &&
                Objects.equals(getPlaceOfPayment(), receipt.getPlaceOfPayment()) &&
                Objects.equals(getNote(), receipt.getNote()) &&
                Objects.equals(getExpenses(), receipt.getExpenses()) &&
                Objects.equals(getBankAccount(), receipt.getBankAccount()) &&
                Objects.equals(TYPE, receipt.TYPE);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), getId(), getAmount(), getDateOfPayment(), getPlaceOfPayment(), getNote(), getExpenses(), getBankAccount(), TYPE);
    }
}
