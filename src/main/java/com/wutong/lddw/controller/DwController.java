package com.wutong.lddw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Index Controller 首页面Controller
 *
 * @author Administrator
 * @date 2020-03-08
 */
@Controller
@RequestMapping()
public class DwController {

    @GetMapping()
    public String index(HttpServletRequest request) {
        return "dw";
    }

}