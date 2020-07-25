package com.hululuuuu.ceoying.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/stats")
public class StatsController {

    @GetMapping("/statsMain")
    public ModelAndView main() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("stats/statsMain");
        return mav;
    }

}
