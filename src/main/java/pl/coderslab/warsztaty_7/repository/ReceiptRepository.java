package pl.coderslab.warsztaty_7.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.warsztaty_7.model.Receipt;

public interface ReceiptRepository extends JpaRepository<Receipt, Long> {
}
