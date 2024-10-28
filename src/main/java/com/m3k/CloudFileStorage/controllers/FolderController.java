package com.m3k.CloudFileStorage.controllers;

import com.m3k.CloudFileStorage.models.dto.CreateEmptyFolderDto;
import com.m3k.CloudFileStorage.models.dto.ObjectRequestDto;
import com.m3k.CloudFileStorage.services.MinioService;
import com.m3k.CloudFileStorage.util.EmptyFolderValidator;
import io.minio.errors.MinioException;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/folder")
public class FolderController {
    private final MinioService minioService;
    private final EmptyFolderValidator emptyFolderValidator;

    public FolderController(MinioService minioService, EmptyFolderValidator emptyFolderValidator) {
        this.minioService = minioService;
        this.emptyFolderValidator = emptyFolderValidator;
    }

    @PostMapping()
    public RedirectView uploadFolder(@RequestParam("folder") MultipartFile[] folder,
                                     @RequestParam(value = "path", defaultValue = "", required = false) String path) {
        List<MultipartFile> list = Arrays.stream(folder).toList();

        try {
            minioService.uploadFolder(list, path);
        } catch (MinioException | IOException | NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException("Error occurred: " + e.getMessage());
        }

        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("/?path=" + URLEncoder.encode(path));
        return redirectView;
    }

    @PostMapping("/create")
    public String createFolder(@ModelAttribute("createEmptyFolderDto") @Valid CreateEmptyFolderDto createEmptyFolderDto,
                               BindingResult bindingResult,
                               @RequestParam(value = "path", defaultValue = "", required = false) String path,
                               RedirectAttributes redirectAttributes) {
        emptyFolderValidator.validate(createEmptyFolderDto, bindingResult);

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("error", bindingResult.getAllErrors().get(0).getDefaultMessage());
            return "redirect:/?path=" + URLEncoder.encode(path);
        }

        minioService.createEmptyFolder(path, createEmptyFolderDto);

        return "redirect:/?path=" + URLEncoder.encode(path);
    }

    @GetMapping()
    public ResponseEntity<byte[]> downloadFolder(@ModelAttribute ObjectRequestDto downloadFolderRequestDto) {

        ByteArrayOutputStream byteArrayOutputStream = minioService.downloadFolder(downloadFolderRequestDto);
        String encodedFolderName = URLEncoder.encode(downloadFolderRequestDto.getName());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + encodedFolderName + ".zip")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE)
                .body(byteArrayOutputStream.toByteArray());
    }

    @DeleteMapping()
    public String deleteFolder(@ModelAttribute ObjectRequestDto deleteFileRequestDto) {
        String redirectPath = "";
        if (deleteFileRequestDto.getPath().contains("/"))
            redirectPath = deleteFileRequestDto.getPath().substring(0, deleteFileRequestDto.getPath().lastIndexOf("/"));

        minioService.deleteFolder(deleteFileRequestDto);
        return "redirect:/?path=" + URLEncoder.encode(redirectPath);
    }

    @PatchMapping()
    public String renameFile(@ModelAttribute ObjectRequestDto renameFolderDto) {
        String redirectPath = "";

        if (renameFolderDto.getPath().contains("/")) {
            redirectPath = renameFolderDto.getPath().substring(0, renameFolderDto.getPath().lastIndexOf("/"));
        }

        minioService.renameFolder(renameFolderDto);

        return "redirect:/?path=" + URLEncoder.encode(redirectPath);
    }
}
