package com.scnu.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.scnu.oss.service.OssService;
import com.scnu.oss.utils.OssConstantUtil;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Service
public class OssServiceImpl implements OssService {
    /**
     *  注意:上传文件的文件名如果一样的话,会进行覆盖
     *  此处通过以
     *  当前日期(年/月/日)创建目录
     *  uuid+文件名
     *  解决冲突问题
     */
    @Override
    public String upload(MultipartFile multipartFile) {
        // yourEndpoint填写Bucket所在地域对应的Endpoint。以华东1（杭州）为例，Endpoint填写为https://oss-cn-hangzhou.aliyuncs.com。
        String endpoint = OssConstantUtil.ENDPOINT;
        // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
        String accessKeyId = OssConstantUtil.KEYID;
        String accessKeySecret = OssConstantUtil.KEYSECRET;
        String bucketName = OssConstantUtil.BUCKETNAME;
        String fileName = multipartFile.getOriginalFilename();

        //String format1 = new DateTime().toString("yyyy/MM/dd"); //joda-time工具封装
        String uuid = UUID.randomUUID().toString().replaceAll("-","");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String format = sdf.format(new Date());
        fileName = format + "/" + uuid + fileName;

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 填写本地文件的完整路径。如果未指定本地路径，则默认从示例程序所属项目对应本地路径中上传文件流。
        InputStream inputStream = null;
        try {
            inputStream = multipartFile.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 依次填写Bucket名称（例如examplebucket）和Object完整路径（例如exampledir/exampleobject.txt）。Object完整路径中不能包含Bucket名称。
        ossClient.putObject(bucketName, fileName, inputStream);

        // 关闭OSSClient。
        ossClient.shutdown();
        //返回格式:https://xhy-2021.oss-cn-beijing.aliyuncs.com/test.png
        String url = "https://" + bucketName + "." + endpoint + "/" + fileName;
        return url;
    }
}
