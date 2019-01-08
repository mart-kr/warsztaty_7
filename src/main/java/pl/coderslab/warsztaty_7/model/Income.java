package pl.coderslab.warsztaty_7.model;

import org.springframework.boot.actuate.endpoint.jmx.DataEndpointMBean;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;

@Entity
@Table(name = "incomes")
public class Income {

    @Id
    @GeneratedValue
    @Column(name = "income_id")
    private Long id;

    @Column(name = "amounts")
    private BigDecimal amount;

    @Column(name = "places_of_payments")
    private String placeOfPayment;

    @Column(name = "dates_of_payments")
    private Date dateOfPayment;

    private String incomeNote;


    //pole na relację z kontem

    //pole na relację z userem

    //pole na relacje z budzetem?

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "income_category_id")
//    private IncomeCategory incomeCategory;

}
