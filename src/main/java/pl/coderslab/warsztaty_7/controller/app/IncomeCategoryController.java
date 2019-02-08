package pl.coderslab.warsztaty_7.controller.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.warsztaty_7.model.IncomeCategory;
import pl.coderslab.warsztaty_7.model.User;
import pl.coderslab.warsztaty_7.service.IncomeCategoryService;
import pl.coderslab.warsztaty_7.service.SecurityService;

@Controller
@RequestMapping(value = "home/incomeCategory")
public class IncomeCategoryController {

    private final IncomeCategoryService incomeCategoryService;
    private final SecurityService securityService;

    @Autowired
    public IncomeCategoryController(IncomeCategoryService incomeCategoryService, SecurityService securityService){
        this.incomeCategoryService = incomeCategoryService;
        this.securityService = securityService;
    }

    @PreAuthorize("hasRole('USER')")
    @ModelAttribute
    public IncomeCategory createEmptyIncomeCategory(){
        return new IncomeCategory();
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping(value = "/all")
    public String allIncomeCategories(@AuthenticationPrincipal final User user, Model model){
        if (user.getBudget() == null) {
            return "redirect:/home/budget/add";
        }
        model.addAttribute("incomeCategories", incomeCategoryService.findAllForBudgetId(user.getBudget().getId()));
        return "incomeCategories";
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping(value = "/add")
    public String showCreateIncomeCategoryForm(@AuthenticationPrincipal final User user, Model model) {
        if (user.getBudget() == null) {
            return "redirect:/home/budget/add";
        } else {
            model.addAttribute("action", "/home/incomeCategory/add");
            return "incomeCategoryForm";
        }
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping(value = "/add")
    public String createIncomeCategory(@AuthenticationPrincipal final User user, @ModelAttribute final IncomeCategory incomeCategory) {
        if (user.getBudget() == null) {
            return "redirect:/home/budget/add";
        } else {
            incomeCategory.setBudget(user.getBudget());
            incomeCategoryService.create(incomeCategory);
            return "redirect:/home/incomeCategory/all";
        }
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping(value = "/edit/{id}")
    public String showEditIncomeCategoryForm(@AuthenticationPrincipal final User user, @PathVariable final Long id, Model model) {
        if (securityService.canViewOrEditEntity(user, incomeCategoryService.findById(id))) {
            model.addAttribute("action", "/home/incomeCategory/edit/" + id);
            model.addAttribute("incomeCategory", incomeCategoryService.findById(id));
            return "incomeCategoryForm";
        } else {
            throw new AccessDeniedException("You can't edit this entity");
        }
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping(value = "/edit/{id}")
    public String editIncomeCategory(@AuthenticationPrincipal final User user, @PathVariable final Long id,
                                     @ModelAttribute final IncomeCategory incomeCategory) {
        if (securityService.canViewOrEditEntity(user, incomeCategory) && incomeCategory.getId().equals(id)) {
            incomeCategoryService.edit(incomeCategory);
            return "redirect:/home/incomeCategory/all";
        } else {
            throw new AccessDeniedException("You can't edit this entity");
        }
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping(value = "delete/{id}")
    public String deleteIncomeCategory(@AuthenticationPrincipal final User user, @PathVariable final Long id) {
        if (securityService.canDeleteEntity(user, incomeCategoryService.findById(id))) {
            incomeCategoryService.deleteById(id);
            return "redirect:/home/incomeCategory/all";
        } else {
            throw new AccessDeniedException("You can't delete this entity");
        }
    }
}
