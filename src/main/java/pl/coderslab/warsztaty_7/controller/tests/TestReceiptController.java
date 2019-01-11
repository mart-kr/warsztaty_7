package pl.coderslab.warsztaty_7.controller.tests;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.warsztaty_7.model.Receipt;
import pl.coderslab.warsztaty_7.service.ReceiptService;

@Controller
@RequestMapping(value = "/home/receipt")
public class TestReceiptController {

    private final ReceiptService receiptService;

    @Autowired
    public TestReceiptController(ReceiptService receiptService) {
        this.receiptService = receiptService;
    }

    @ModelAttribute
    public Receipt createEmptyReceipt() {
        return new Receipt();
    }

    @GetMapping(value = "/all")
    public String allReceipts(Model model) {
        model.addAttribute("receipts", receiptService.findAllOrderedByDate());
        return "test_receipts";
    }

    @GetMapping(value = "/add")
    public String showCreateReceiptForm(Model model) {
        model.addAttribute("action", "home/receipt/add");
        return "test_addReceipt";
    }

    @PostMapping(value = "/add")
    public String createReceipt(@ModelAttribute Receipt receipt) {
        receiptService.create(receipt);
        return "redirect:http://localhost:8080/home/receipt/all";
    }

    @GetMapping(value = "edit/{id}")
    public String showEditReceiptForm(@PathVariable Long id, Model model) {
        model.addAttribute("receipt", receiptService.findById(id));
        return "test_editReceipt";
    }

    @PostMapping(value = "edit/{id}")
    public String editReceipt(@PathVariable Long id, @ModelAttribute Receipt receipt) {
        receiptService.edit(receipt);
        return "redirect:http://localhost:8080/home/receipt/all";
    }

    @GetMapping(value = "delete/{id}")
    public String deleteReceipt(@PathVariable Long id) {
        receiptService.deleteById(id);
        return "redirect:http://localhost:8080/home/receipt/all";
    }


}
