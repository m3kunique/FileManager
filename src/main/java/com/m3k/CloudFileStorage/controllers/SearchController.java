package com.m3k.CloudFileStorage.controllers;

import com.m3k.CloudFileStorage.models.dto.MinioResponseObjectDto;
import com.m3k.CloudFileStorage.services.SearchService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/search")

public class SearchController extends AbstractController {
    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping()
    public String search(@RequestParam String query,
                         Model model){
        String owner = SecurityContextHolder.getContext().getAuthentication().getName();

        List<MinioResponseObjectDto> searchResult = searchService.searchObjects(owner, query);
        model.addAttribute("searchResult", searchResult);
        model.addAttribute("query", query);

        return "search";
    }
}
