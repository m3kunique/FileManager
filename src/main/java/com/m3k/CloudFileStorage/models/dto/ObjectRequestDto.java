package com.m3k.CloudFileStorage.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ObjectRequestDto {
    private String owner;
    private String path;
    private String name;
}
