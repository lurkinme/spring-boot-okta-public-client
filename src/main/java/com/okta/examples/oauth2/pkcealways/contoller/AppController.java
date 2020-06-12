package com.okta.examples.oauth2.pkcealways.contoller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AppController {

    @Autowired
    AppService appService;

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/profile")
    public ModelAndView profile(@AuthenticationPrincipal OidcUser user) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("user", user);
        mav.setViewName("profile");
        return mav;
    }

    @GetMapping("/accessToken")
    public ModelAndView accessToken() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("accessToken", appService.getAccessToken());
        mav.setViewName("token");
        return mav;
    }

}
