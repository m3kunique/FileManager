package com.m3k.CloudFileStorage.controllers;

import com.m3k.CloudFileStorage.models.Breadcrumb;
import com.m3k.CloudFileStorage.services.BreadcrumbService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@ControllerAdvice
public class BreadcrumbAdvice {
    private final BreadcrumbService breadcrumbService;

    public BreadcrumbAdvice(BreadcrumbService breadcrumbService) {
        this.breadcrumbService = breadcrumbService;
    }

    @ModelAttribute("breadcrumbs")
    public List<Breadcrumb> populateBreadcrumbs(HttpServletRequest request) {
        return breadcrumbService.generateBreadcrumbs(request);
    }
}
