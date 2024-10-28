package com.m3k.CloudFileStorage.services;

import com.m3k.CloudFileStorage.models.Breadcrumb;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BreadcrumbService {
    public List<Breadcrumb> generateBreadcrumbs(HttpServletRequest request) {
        List<Breadcrumb> breadcrumbs = new ArrayList<>();
        breadcrumbs.add(new Breadcrumb("Home", ""));
        if (request.getParameter("path") == null) {
            return breadcrumbs;
        }

        String[] pathElements = request.getParameter("path").split("/");

        StringBuilder url = new StringBuilder();
        for (String element : pathElements) {
            if (!element.isEmpty()) {
                url.append(element).append("/");
                breadcrumbs.add(new Breadcrumb(element, url.toString()));
            }
        }

        return breadcrumbs;
    }
}
