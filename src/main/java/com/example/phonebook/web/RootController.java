package com.example.phonebook.web;

import com.example.phonebook.AuthorizedUser;
import com.example.phonebook.to.UserTo;
import com.example.phonebook.util.UserUtil;
import com.example.phonebook.web.user.AbstractUserController;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;

import static com.example.phonebook.web.ExceptionInfoHandler.EXCEPTION_DUPLICATE_LOGIN;

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
    public String updateProfile(@Valid UserTo userTo, BindingResult result, SessionStatus status, @AuthenticationPrincipal AuthorizedUser authUser) {
        if (result.hasErrors()) {
            return "profile";
        }
        try {
            super.update(userTo, authUser.getId());
            authUser.update(userTo);
            status.setComplete();
            return "redirect:phonebooks";
        } catch (DataIntegrityViolationException ex) {
            result.rejectValue("login", EXCEPTION_DUPLICATE_LOGIN);
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
            result.rejectValue("login", EXCEPTION_DUPLICATE_LOGIN);
            model.addAttribute("register", true);
            return "profile";
        }
    }

    @GetMapping(value = "/phonebooks")
    public String phonebooks(@AuthenticationPrincipal AuthorizedUser authUser) {
        return authUser == null ? "redirect:login" : "phonebooks";
    }
}
