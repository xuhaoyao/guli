package com.scnu.vod;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadVideoRequest;
import com.aliyun.vod.upload.resp.UploadVideoResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.scnu.vod.utils.VodConstantUtil;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class Test01 {

    //根据视频id获取播放地址
    @Test
    public void test1(){
        //1.初始化对象
        DefaultAcsClient client = InitObject.initVodClient(VodConstantUtil.KEYID,VodConstantUtil.KEYSECRET);
        //2.创建获取视频地址request和response
        GetPlayInfoRequest request = new GetPlayInfoRequest();
        GetPlayInfoResponse response = new GetPlayInfoResponse();

        //3.设置视频id
        request.setVideoId("b31d414a2f734e009042f09318f07acc");

        //4.调用初始化对象里面的方法,传递request,获取视频数据
        try {
            response = client.getAcsResponse(request);
            List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
            //播放地址
            for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
                System.out.print("PlayInfo.PlayURL = " + playInfo.getPlayURL() + "\n");
            }
            //Base信息
            System.out.print("VideoBase.Title = " + response.getVideoBase().getTitle() + "\n");
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test2(){
        //1.初始化对象
        DefaultAcsClient client = InitObject.initVodClient("LTAI5tC3sLwXqfk5v9XXbA7R","wcRcLApO9oEI5ExHEoTDVJhdK3R93d");
        //2.创建获取视频凭证request和response
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();
        //3.设置视频id
        request.setVideoId("b31d414a2f734e009042f09318f07acc");
        //4.调用初始化对象里面的方法,传递request,获取视频数据
        try {
            response = client.getAcsResponse(request);
            //播放凭证
            System.out.print("PlayAuth = " + response.getPlayAuth() + "\n");
            //VideoMeta信息
            System.out.print("VideoMeta.Title = " + response.getVideoMeta().getTitle() + "\n");
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test3(){
        testUploadVideo("","","测试上传","C:\\Users\\Administrator\\Desktop\\谷粒学院\\项目资料\\1-阿里云上传测试视频\\6 - What If I Want to Move Faster.mp4");
    }

    private static void testUploadVideo(String accessKeyId, String accessKeySecret, String title, String fileName) {
        UploadVideoRequest request = new UploadVideoRequest(accessKeyId, accessKeySecret, title, fileName);
        /* 可指定分片上传时每个分片的大小，默认为2M字节 */
        request.setPartSize(2 * 1024 * 1024L);
        /* 可指定分片上传时的并发线程数，默认为1，(注：该配置会占用服务器CPU资源，需根据服务器情况指定）*/
        request.setTaskNum(1);
        UploadVideoImpl uploader = new UploadVideoImpl();
        UploadVideoResponse response = uploader.uploadVideo(request);
        System.out.print("RequestId=" + response.getRequestId() + "\n");  //请求视频点播服务的请求ID
        if (response.isSuccess()) {
            System.out.print("VideoId=" + response.getVideoId() + "\n");
        } else {
            /* 如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因 */
            System.out.print("VideoId=" + response.getVideoId() + "\n");
            System.out.print("ErrorCode=" + response.getCode() + "\n");
            System.out.print("ErrorMessage=" + response.getMessage() + "\n");
        }
    }

    @Test
    public void test4(){
        List<String> list = new ArrayList<>();
        list.add("11");
        list.add("22");
        list.add("33");
        System.out.println(String.join(",",list));
    }
}
