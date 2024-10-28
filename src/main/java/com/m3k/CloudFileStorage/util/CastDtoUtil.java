package com.m3k.CloudFileStorage.util;

import com.m3k.CloudFileStorage.exceptions.IllegalFileNameException;
import com.m3k.CloudFileStorage.models.dto.RenameObjectRequestDto;
import com.m3k.CloudFileStorage.models.dto.ObjectRequestDto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CastDtoUtil {

    public RenameObjectRequestDto castToRenameFileDto(ObjectRequestDto dto) {
        if (dto.getName().contains("/")) {
            throw new IllegalFileNameException("File name must not contain '/'");
        }

        RenameObjectRequestDto result = new RenameObjectRequestDto();
        result.setOwner(dto.getOwner());
        result.setSourcePath(dto.getPath());

        String newPath = dto.getName();

        if (dto.getPath().contains("/")) {
            int lastSlashIndex = dto.getPath().lastIndexOf("/");
            newPath = dto.getPath().substring(0, lastSlashIndex) + "/" + dto.getName();
        }
        result.setNewPath(newPath);
        return result;
    }
}
