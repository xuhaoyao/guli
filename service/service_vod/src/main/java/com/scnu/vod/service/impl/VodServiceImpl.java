package com.scnu.vod.service.impl;

import com.aliyun.oss.ClientException;
import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.DeleteVideoResponse;
import com.scnu.exceptions.VodException;
import com.scnu.vod.service.VodService;
import com.scnu.vod.utils.VodConstantUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class VodServiceImpl implements VodService {

    private static DefaultAcsClient initVodClient(String accessKeyId, String accessKeySecret) throws ClientException {
        String regionId = "cn-shanghai";  // 点播服务接入区域
        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
        DefaultAcsClient client = new DefaultAcsClient(profile);
        return client;
    }

    /**
     * 删除视频
     * @param client 发送请求客户端
     * @param videoId 要删除视频的id
     * @return DeleteVideoResponse 删除视频响应数据
     * @throws Exception
     */
    private static DeleteVideoResponse deleteVideo(DefaultAcsClient client,String videoId) throws Exception {
        DeleteVideoRequest request = new DeleteVideoRequest();
        //支持传入多个视频ID，多个用逗号分隔
        request.setVideoIds(videoId);
        return client.getAcsResponse(request);
    }

    @Override
    public void deleteVideo(String id) {
        DefaultAcsClient client = initVodClient(VodConstantUtil.KEYID, VodConstantUtil.KEYSECRET);
        DeleteVideoResponse response = new DeleteVideoResponse();
        try {
            response = deleteVideo(client,id);
        } catch (Exception e) {
            throw new VodException(e.getMessage());
        }
    }

    @Override
    public void deleteVideoBatch(List<String> ids) {
        DefaultAcsClient client = initVodClient(VodConstantUtil.KEYID, VodConstantUtil.KEYSECRET);
        DeleteVideoResponse response = new DeleteVideoResponse();
        String id = String.join(",",ids);   // "1,2,3,4"
        try {
            response = deleteVideo(client,id);
        } catch (Exception e) {
            throw new VodException(e.getMessage());
        }
    }

    @Override
    public String uploadVideo(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        String title = fileName.substring(0,fileName.lastIndexOf("."));  //在vod平台的视频名称
        InputStream inputStream = null;
        String videoId = null;
        try {
            inputStream = file.getInputStream();
            videoId = testUploadStream(VodConstantUtil.KEYID,VodConstantUtil.KEYSECRET,title,fileName, inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(videoId == null){
            throw new VodException("视频上传失败");
        }
        return videoId;
    }

    /**
     * 流式上传接口
     *
     */
    private static String testUploadStream(String accessKeyId, String accessKeySecret, String title, String fileName, InputStream inputStream) {
        UploadStreamRequest request = new UploadStreamRequest(accessKeyId, accessKeySecret, title, fileName, inputStream);
        UploadVideoImpl uploader = new UploadVideoImpl();
        UploadStreamResponse response = uploader.uploadStream(request);
        return response.getVideoId();
    }
}
