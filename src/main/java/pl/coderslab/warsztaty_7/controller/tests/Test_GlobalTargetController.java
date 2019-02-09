package pl.coderslab.warsztaty_7.controller.tests;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.coderslab.warsztaty_7.model.GlobalTarget;
import pl.coderslab.warsztaty_7.model.User;
import pl.coderslab.warsztaty_7.service.GlobalTargetService;
import pl.coderslab.warsztaty_7.service.SecurityService;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping(value = "home/globalTarget")
public class Test_GlobalTargetController {

    private final GlobalTargetService globalTargetService;
    private final SecurityService securityService;

    @Autowired
    public Test_GlobalTargetController(GlobalTargetService globalTargetService, SecurityService securityService) {
        this.globalTargetService = globalTargetService;
        this.securityService = securityService;
    }


    @PreAuthorize("hasRole('USER')")
    @ModelAttribute(name = "globalTarget")
    public GlobalTarget createEmptyGlobalTarget() {
        return new GlobalTarget();
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping(value = "/all" )
    public String allGlobalTargets(@AuthenticationPrincipal User user, Model model) {
        if (user.getBudget() != null) {
            model.addAttribute("monthGlobalTarAll", globalTargetService.findAllByBudget(user.getBudget()));
            return "globalTargetsHistory";
        } else {
            return "redirect:/home/budget/add";
        }
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping(value = "/add")
    public String showCreateGlobalTargetForm(@AuthenticationPrincipal User user, Model model) {
        if (user.getBudget() != null) {
            LocalDate today = LocalDate.now();
            model.addAttribute("action", "/home/globalTarget/add");
            model.addAttribute("today", today);
            return "globalTargetForm";
        } else {
            return "redirect:/home/budget/add";
        }
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping(value = "/add")
    public String createGlobalTarget(@AuthenticationPrincipal @Valid User user, @ModelAttribute GlobalTarget globalTarget,
                               BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (user.getBudget() != null){
            List<GlobalTarget> globalTargets = globalTargetService.findAllByBudget(user.getBudget());
            if (globalTargets.size() > 0) {
                GlobalTarget nullGlobalTarget = globalTargetService.findGlobalTargetWithEndDateIsNull(globalTargets, user.getBudget());
                if (nullGlobalTarget != null) {
                    if (nullGlobalTarget.getStartDate().isAfter(globalTarget.getStartDate())) {
                        bindingResult.rejectValue("startDate", "error message");
                    } else {
                        globalTargetService.edit(globalTargetService.setEndDateInNullGlobalTarget(nullGlobalTarget, globalTarget));
                    }
                }
            }
            if (bindingResult.hasErrors()) {
                return "globalTargetForm";
            } else {
                globalTarget.setBudget(user.getBudget());
                globalTarget.setStartDate(globalTarget.getStartDate().withDayOfMonth(01));
                globalTargetService.create(globalTarget);
                return "redirect:/home/target/thisMonth";
            }
        } else {
            return "redirect:/home/budget/add";
        }
    }

//    @PreAuthorize("hasRole('USER')")
//    @GetMapping(value = "/thisMonth")
//    public String targetsInThisMonth(@AuthenticationPrincipal User user, Model model) {
//        if (user.getBudget() != null) {
//            model.addAttribute("monthTarSum", targetService.sumAllFromThisMonth(user.getBudget()));
//            return "targets";
//        } else {
//            return "redirect:/home/budget/add";
//        }
//    }

}