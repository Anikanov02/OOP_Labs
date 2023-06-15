package com.anikanov.controller;

import com.anikanov.service.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("api/v1/ui")
public class ViewController {
    private static final String LOGIN_PAGE = "login";
    private static final String REGISTRATION_PAGE = "registration";
    private static final String MAIN_PAGE = "main";
    private static final String ADMIN_PAGE = "adminPanel";

    private final PermissionService permissionService;

    @GetMapping("/login")
    public ModelAndView loginScreen() {
        final ModelAndView mav = new ModelAndView(LOGIN_PAGE);
        return mav;
    }

    @GetMapping("/registration")
    public ModelAndView registrationScreen() {
        final ModelAndView mav = new ModelAndView(REGISTRATION_PAGE);
        return mav;
    }

    @GetMapping("/main")
    public ModelAndView mainPage(Principal principal) {
        final ModelAndView mav = new ModelAndView(MAIN_PAGE);
        return mav;
    }

    @GetMapping("/adminPanel")
    public ModelAndView adminPanel(Principal principal) {
        if (permissionService.isAdmin(principal.getName())) {
            final ModelAndView mav = new ModelAndView(ADMIN_PAGE);
            return mav;
        } else {
            final ModelAndView mav = new ModelAndView();
            mav.setStatus(HttpStatus.FORBIDDEN);
            return mav;
        }
    }
}
