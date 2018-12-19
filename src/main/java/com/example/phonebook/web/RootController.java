package com.example.phonebook.web;

import com.example.phonebook.AuthorizedUser;
import com.example.phonebook.to.UserTo;
import com.example.phonebook.util.UserUtil;
import com.example.phonebook.web.user.AbstractUserController;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;

@Controller
public class RootController extends AbstractUserController {

    @GetMapping("/")
    public String root() {
        return "redirect:phonebooks";
    }

    @GetMapping(value = "/login")
    public String login() {
        return "login";
    }

    @GetMapping("/profile")
    public String profile(ModelMap model, @AuthenticationPrincipal AuthorizedUser authUser) {
        model.addAttribute("userTo", authUser.getUserTo());
        return "profile";
    }

    @PostMapping("/profile")
    public String updateProfile(@Valid UserTo userTo, BindingResult result, SessionStatus status) {
        if (result.hasErrors()) {
            return "profile";
        }
        try {
            super.update(userTo, SecurityUtil.authUserId());
            SecurityUtil.get().update(userTo);
            status.setComplete();
            return "redirect:phonebooks";
        } catch (DataIntegrityViolationException ex) {
            result.rejectValue("login", "exception.user.duplicateLogin");
            return "profile";
        }
    }

    @GetMapping("/register")
    public String register(ModelMap model) {
        model.addAttribute("userTo", new UserTo());
        model.addAttribute("register", true);
        return "profile";
    }

    @PostMapping("/register")
    public String saveRegister(@Valid UserTo userTo, BindingResult result, SessionStatus status, ModelMap model) {
        if (result.hasErrors()) {
            model.addAttribute("register", true);
            return "profile";
        }
        try {
            super.create(UserUtil.createNewFromTo(userTo));
            status.setComplete();
            return "redirect:login?message=app.registered&username=" + userTo.getLogin();
        } catch (DataIntegrityViolationException ex) {
            result.rejectValue("login", "exception.user.duplicateLogin");
            model.addAttribute("register", true);
            return "profile";
        }
    }

    @GetMapping(value = "/phonebooks")
    public String phonebooks(Model model) {
        model.addAttribute("phonebooks", super.getAll());
        return "phonebooks";
    }

    @GetMapping("/accessDenied")
    public String accessDenied() {
        return "accessDenied";
    }
}
