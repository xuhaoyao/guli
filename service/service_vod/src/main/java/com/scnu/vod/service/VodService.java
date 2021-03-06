package com.scnu.vod.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VodService {
    String uploadVideo(MultipartFile file);

    void deleteVideo(String id);

    void deleteVideoBatch(List<String> ids);

    String getPlayAuth(String id);
}
