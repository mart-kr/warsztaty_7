package pl.coderslab.warsztaty_7.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "incomes")
public class Income extends Auditable implements Operation {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private BigDecimal amount;

    @Column(name = "place_of_payment")
    private String placeOfPayment;

    @Column(name = "date_of_payment")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dateOfPayment;

    @Column(name = "income_note")
    private String incomeNote;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "income_category_id")
    private IncomeCategory incomeCategory;

    @ManyToOne
    @JoinColumn(name = "bank_account_id")
    private BankAccount bankAccount;

    @Transient
    private final String TYPE = "Przychód";

    public Income() {}

    public Income(BigDecimal amount, LocalDate dateOfPayment, IncomeCategory incomeCategory) {
        this.amount = amount;
        this.dateOfPayment = dateOfPayment;
        this.incomeCategory = incomeCategory;
    }

    //TODO: Do usunięcia po podmianie repository z podłączeniem do DB
    public Income(Long id, BigDecimal amount, String placeOfPayment, String incomeNote, IncomeCategory incomeCategory) {
        this.id = id;
        this.amount = amount;
        this.placeOfPayment = placeOfPayment;
        this.dateOfPayment = LocalDate.now();
        this.incomeNote = incomeNote;
        this.incomeCategory = incomeCategory;
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

    public String getPlaceOfPayment() {
        return placeOfPayment;
    }

    public void setPlaceOfPayment(String placeOfPayment) {
        this.placeOfPayment = placeOfPayment;
    }

    public LocalDate getDateOfPayment() {
        return dateOfPayment;
    }

    public void setDateOfPayment(LocalDate dateOfPayment) {
        this.dateOfPayment = dateOfPayment;
    }

    public String getIncomeNote() {
        return incomeNote;
    }

    public void setIncomeNote(String incomeNote) {
        this.incomeNote = incomeNote;
    }

    public IncomeCategory getIncomeCategory() {
        return incomeCategory;
    }

    public void setIncomeCategory(IncomeCategory incomeCategory) {
        this.incomeCategory = incomeCategory;
    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    @Override
    public String toString() {
        return "Income{" +
                "id=" + id +
                ", amount=" + amount +
                ", placeOfPayment='" + placeOfPayment +
                ", dateOfPayment=" + dateOfPayment +
                ", incomeNote='" + incomeNote +
                ", incomeCategory=" + incomeCategory +
                ", createdByUserId=" + createdByUserId +
                ", creationDate=" + creationDate +
                ", updatedByUserId=" + updatedByUserId +
                ", updatedDate=" + updatedDate +
                ", bankAccountId=" + bankAccount.getId() +
                ", bankAccountBalance=" + bankAccount.getBalance() +
                '}';
    }

    @Override
    public LocalDate getOperationDate() {
        return dateOfPayment;
    }

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Income)) return false;
        if (!super.equals(o)) return false;
        Income income = (Income) o;
        return Objects.equals(getId(), income.getId()) &&
                Objects.equals(getAmount(), income.getAmount()) &&
                Objects.equals(getPlaceOfPayment(), income.getPlaceOfPayment()) &&
                Objects.equals(getDateOfPayment(), income.getDateOfPayment()) &&
                Objects.equals(getIncomeNote(), income.getIncomeNote()) &&
                Objects.equals(getIncomeCategory(), income.getIncomeCategory()) &&
                Objects.equals(getBankAccount(), income.getBankAccount()) &&
                Objects.equals(TYPE, income.TYPE);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), getId(), getAmount(), getPlaceOfPayment(), getDateOfPayment(), getIncomeNote(), getIncomeCategory(), getBankAccount(), TYPE);
    }
}
