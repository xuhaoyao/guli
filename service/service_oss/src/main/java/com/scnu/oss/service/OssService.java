package com.scnu.oss.service;

import org.springframework.web.multipart.MultipartFile;

public interface OssService {
    String upload(MultipartFile multipartFile);
}
