package com.api.app.controller;

import com.api.app.controller.security.AuthProvider;
import com.api.app.controller.security.Principal;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@AllArgsConstructor
public class AuthenticationController {
    private final AuthProvider provider;

    @GetMapping("login")
    public String loginPage(Model model) {
        model.addAttribute("principal", new Principal());
        return "login";
    }

    @PostMapping("authenticate")
    public RedirectView authenticate(@ModelAttribute Principal principal) {
        provider.authenticate(principal);
        return new RedirectView("/");
    }

    @PostMapping("logout")
    public RedirectView logout() {
        provider.clearSession();
        return new RedirectView("/login");
    }
}
