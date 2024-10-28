package com.m3k.CloudFileStorage.controllers;

import com.m3k.CloudFileStorage.models.dto.CustomerDto;
import com.m3k.CloudFileStorage.repositories.CustomerRepository;
import com.m3k.CloudFileStorage.services.RegistrationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController extends AbstractController {
    private final RegistrationService registrationService;


    @Autowired
    public AuthController(RegistrationService registrationService, CustomerRepository customerRepository) {
        this.registrationService = registrationService;
    }

    @GetMapping("/login")
    public String login(Model model) {
        if (model.getAttribute("isAuthenticated").equals(true)) {
            return "redirect:/";
        }
        return "login";
    }

    @GetMapping("/registration")
    public String registration(@ModelAttribute("customerDto") CustomerDto customerDto,
                               Model model) {
        if (model.getAttribute("isAuthenticated").equals(true)) {
            return "redirect:/";
        }
        return "registration";
    }

    @PostMapping("/registration")
    public String performRegistration(@ModelAttribute("customerDto") @Valid CustomerDto customerDto,
                                      BindingResult bindingResult,
                                      RedirectAttributes redirectAttributes) {
        registrationService.validateCustomer(customerDto);

        if (bindingResult.hasErrors()) {
            return "registration";
        }

        registrationService.register(customerDto);

        redirectAttributes.addFlashAttribute("successMessage", "Registration completed successfully, please login");

        return "redirect:login";
    }
}
