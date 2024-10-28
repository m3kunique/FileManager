package com.m3k.CloudFileStorage.services;

import com.m3k.CloudFileStorage.exceptions.FileStorageException;
import com.m3k.CloudFileStorage.models.dto.MinioResponseObjectDto;
import io.minio.ListObjectsArgs;
import io.minio.MinioClient;
import io.minio.Result;
import io.minio.messages.Item;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SearchService {
    @Value("user-files")
    private String bucketName;

    private final MinioClient minioClient;

    public SearchService(MinioClient minioClient) {
        this.minioClient = minioClient;
    }

    public List<MinioResponseObjectDto> searchObjects(String owner, String query) {

        Set<String> resultPaths = new HashSet<>();

        Iterable<Result<Item>> results = minioClient.listObjects(ListObjectsArgs.builder()
                .bucket(bucketName)
                .prefix(owner + "/")
                .recursive(true)
                .build());

        for(Result<Item> result : results){
            try {
                String path = result.get().objectName();
                String pathWithoutOwner = path.substring(path.indexOf("/") + 1);
                String[] parts = pathWithoutOwner.split("/");

                for (int i = 0; i < parts.length; i++) {
                    if (parts[i].contains(query)){
                        StringBuilder resultPath = new StringBuilder();
                        for (int j = 0; j <= i; j++) {
                            resultPath.append(parts[j]).append("/");
                        }
                        resultPaths.add(resultPath.toString());
                    }
                }
            }catch (Exception e){
                throw new FileStorageException("Something wrong with file storage");
            }
        }
        List<MinioResponseObjectDto> searchResult = new ArrayList<>();

        for (String resultPath: resultPaths){
            String objectName = resultPath.substring(0, resultPath.length() -1 );

            if (objectName.contains("/")){
                int lastSlashIndex = objectName.lastIndexOf("/");
                objectName = objectName.substring(lastSlashIndex + 1);
            }
                if (resultPath.contains(".")){
                    resultPath = resultPath.substring(0, resultPath.length() - 1);
                    if (resultPath.contains("/")){
                        int lastSlashIndex = resultPath.lastIndexOf("/");
                        resultPath=resultPath.substring(0, lastSlashIndex);
                    }else {
                        resultPath="";
                    }
                    searchResult.add(new MinioResponseObjectDto(owner,resultPath, objectName, true));
                } else {
                    searchResult.add(new MinioResponseObjectDto(owner,resultPath, objectName, false));
                }
        }

        return searchResult;
    }
}
