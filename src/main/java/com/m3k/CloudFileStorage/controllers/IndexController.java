package com.m3k.CloudFileStorage.controllers;

import com.m3k.CloudFileStorage.models.dto.CreateEmptyFolderDto;
import com.m3k.CloudFileStorage.models.dto.MinioResponseObjectDto;
import com.m3k.CloudFileStorage.models.dto.ObjectRequestDto;
import com.m3k.CloudFileStorage.services.MinioService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class IndexController extends AbstractController {
    private final MinioService minioService;

    public IndexController(MinioService minioService) {
        this.minioService = minioService;
    }

    @GetMapping("/")
    public String homePage(Model model,
                           @RequestParam(value = "path", required = false, defaultValue = "") String path) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<MinioResponseObjectDto> objects = minioService.getFiles(authentication.getName(), path);

        model.addAttribute("createEmptyFolderDto", new CreateEmptyFolderDto());
        model.addAttribute("objectRequestDto", new ObjectRequestDto());
        model.addAttribute("objects", objects);

        return "index";
    }
}
