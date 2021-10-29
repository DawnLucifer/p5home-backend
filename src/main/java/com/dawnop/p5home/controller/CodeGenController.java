package com.dawnop.p5home.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class CodeGenController {

    @RequestMapping("/codegen")
    public ModelAndView codeGenPage(HttpServletRequest request) {
//        System.out.println(request.getContentType());
        String code = request.getParameter("codes");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("codegen");
//        System.out.println(code);
        mv.addObject("code", code);
        return mv;
    }
}
