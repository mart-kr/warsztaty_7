package pl.coderslab.warsztaty_7.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import pl.coderslab.warsztaty_7.repository.ReceiptRepository;

@Service
@Primary
public class ReceiptServiceJpaImpl {

    private ReceiptRepository receiptRepository;

    @Autowired
    public ReceiptServiceJpaImpl(ReceiptRepository receiptRepository) {
        this.receiptRepository = receiptRepository;
    }


}
