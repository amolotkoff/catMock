package com.amolotkoff.mocker.util.controller;

import com.amolotkoff.controllers.ApiController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/mock/service")
public class MockServiceController {
    @Autowired private ApiController apiController;

    @GetMapping
    public String getMainPage() {
        return "index";
    }

    @GetMapping("/delay")
    public String getDelayPage(Model model) {
        model.addAttribute("apis", apiController.fetchApis());

        return "delay";
    }
}