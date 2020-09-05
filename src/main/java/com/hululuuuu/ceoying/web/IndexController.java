package com.hululuuuu.ceoying.web;

import com.hululuuuu.ceoying.config.auth.LoginUser;
import com.hululuuuu.ceoying.config.auth.dto.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@RequiredArgsConstructor
@Controller
public class IndexController {

    @GetMapping("/")
    public ModelAndView index(@LoginUser SessionUser user) {
        ModelAndView mav = new ModelAndView();
        if (user != null) {
            mav.addObject("userName", user.getName());
            RedirectView redirectView = new RedirectView();
            redirectView.setUrl("/main");
            redirectView.setExposeModelAttributes(false);
            mav.setView(redirectView);
        }else {
            mav.setViewName("index");
        }
        return mav;
    }



}
