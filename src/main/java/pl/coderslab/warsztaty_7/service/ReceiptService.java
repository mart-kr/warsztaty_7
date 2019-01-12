package pl.coderslab.warsztaty_7.service;

import pl.coderslab.warsztaty_7.model.Receipt;

import java.util.List;

public interface ReceiptService {

    List<Receipt> findAllOrderedByDate();
    Receipt findById(Long id);
    Receipt create(Receipt receipt);
    Receipt edit(Receipt receipt);
    void deleteById(Long id);
    Receipt findByExpenseId(Long id);

}
