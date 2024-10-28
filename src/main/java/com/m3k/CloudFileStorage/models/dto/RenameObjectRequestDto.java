package com.m3k.CloudFileStorage.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RenameObjectRequestDto {
    private String owner;
    private String sourcePath;
    private String newPath;
}
