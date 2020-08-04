package com.hululuuuu.ceoying.web;

import com.hululuuuu.ceoying.config.auth.dto.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final HttpSession httpSession;

    @GetMapping("/")
    public ModelAndView index() {
        ModelAndView mav = new ModelAndView();
        SessionUser user = (SessionUser) httpSession.getAttribute("user");
        if (user != null) {
            mav.addObject("userName", user.getName());
        }
        mav.setViewName("index");
        return mav;
    }



}
