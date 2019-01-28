package pl.coderslab.warsztaty_7.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface Operation {

    LocalDate getOperationDate();
    String getType();
    BigDecimal getAmount();
}
