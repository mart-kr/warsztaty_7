package pl.coderslab.warsztaty_7.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "transfers")
public class Transfer extends Auditable implements Operation{

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private BigDecimal amount;

    @Column(name = "date_of_transfer")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dateOfTransfer;

    @Column
    private String note;

    @ManyToOne
    @JoinColumn(name = "from_account_id")
    private BankAccount fromAccount;

    @ManyToOne
    @JoinColumn(name = "to_account_id")
    private BankAccount toAccount;

    @Transient
    private final String TYPE = "Transfer";

    public Transfer() {}

    public Transfer(BigDecimal amount, LocalDate dateOfTransfer, String note, BankAccount fromAccount, BankAccount toAccount) {
        this.amount = amount;
        this.dateOfTransfer = dateOfTransfer;
        this.note = note;
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
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

    public LocalDate getDateOfTransfer() {
        return dateOfTransfer;
    }

    public void setDateOfTransfer(LocalDate dateOfTransfer) {
        this.dateOfTransfer = dateOfTransfer;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public BankAccount getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(BankAccount fromAccount) {
        this.fromAccount = fromAccount;
    }

    public BankAccount getToAccount() {
        return toAccount;
    }

    public void setToAccount(BankAccount toAccount) {
        this.toAccount = toAccount;
    }

    public String getTYPE() {
        return TYPE;
    }

    @Override
    public LocalDate getOperationDate() {
        return dateOfTransfer;
    }

    @Override
    public String getType() {
        return TYPE;
    }
}
