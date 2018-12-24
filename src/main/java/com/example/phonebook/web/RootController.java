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
@RequestMapping("/")
public class RootController extends AbstractUserController {
    private final static String PROFILE = "profile";
    private final static String REGISTER = "register";
    private final static String LOGIN = "login";
    private final static String PHONEBOOKS = "phonebooks";

    @GetMapping
    public String root() {
        return "redirect:phonebooks";
    }

    @GetMapping(value = LOGIN)
    public String login() {
        return LOGIN;
    }

    @GetMapping(PROFILE)
    public String profile(ModelMap model, @AuthenticationPrincipal AuthorizedUser authUser) {
        model.addAttribute("userTo", authUser.getUserTo());
        return PROFILE;
    }

    @PostMapping(PROFILE)
    public String updateProfile(@Valid UserTo userTo, BindingResult result, SessionStatus status, @AuthenticationPrincipal AuthorizedUser authUser) {
        if (result.hasErrors()) {
            return PROFILE;
        }
        try {
            super.update(userTo, authUser.getId());
            authUser.update(userTo);
            status.setComplete();
            return "redirect:phonebooks";
        } catch (DataIntegrityViolationException ex) {
            result.rejectValue(LOGIN, EXCEPTION_DUPLICATE_LOGIN);
            return PROFILE;
        }
    }

    @GetMapping(REGISTER)
    public String register(ModelMap model) {
        model.addAttribute("userTo", new UserTo());
        model.addAttribute(REGISTER, true);
        return PROFILE;
    }

    @PostMapping(REGISTER)
    public String saveRegister(@Valid UserTo userTo, BindingResult result, SessionStatus status, ModelMap model) {
        if (result.hasErrors()) {
            model.addAttribute(REGISTER, true);
            return PROFILE;
        }
        try {
            super.create(UserUtil.createNewFromTo(userTo));
            status.setComplete();
            return "redirect:login?message=app.registered&username=" + userTo.getLogin();
        } catch (DataIntegrityViolationException ex) {
            result.rejectValue(LOGIN, EXCEPTION_DUPLICATE_LOGIN);
            model.addAttribute(REGISTER, true);
            return PROFILE;
        }
    }

    @GetMapping(value = PHONEBOOKS)
    public String phonebooks(@AuthenticationPrincipal AuthorizedUser authUser) {
        return authUser == null ? "redirect:login" : PHONEBOOKS;
    }
}
