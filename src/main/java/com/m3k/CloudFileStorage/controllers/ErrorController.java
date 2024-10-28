package com.m3k.CloudFileStorage.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorController extends AbstractController {

    @GetMapping("/error")
    public String error() {
        return "error";
    }
}
