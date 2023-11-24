package com.example.myspring.controller;

import com.example.myspring.config.LogAround;
import com.example.myspring.config.MinioConfig;
import com.example.myspring.config.MinioUtil;
import com.example.myspring.config.Result;
import io.minio.messages.Bucket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Description
 * @Date 2023/9/28 13:39
 * @Created by hugh
 */
@RestController
@RequestMapping("/minio")
public class MinioFileController {

    @Autowired
    private MinioUtil minioUtil;

    @Autowired
    private MinioConfig prop;

    @LogAround(value = "查看存储bucket是否存在")
    @GetMapping("/bucketExists")
    public Result bucketExists(@RequestParam("bucketName") String bucketName) {
        return Result.ok().put("bucketName",minioUtil.bucketExists(bucketName));
    }

    @LogAround(value = "创建存储bucket")
    @GetMapping("/makeBucket")
    public Result makeBucket(String bucketName) {
        return Result.ok().put("bucketName",minioUtil.makeBucket(bucketName));
    }

    @LogAround(value = "删除存储bucket")
    @GetMapping("/removeBucket")
    public Result removeBucket(String bucketName) {
        return Result.ok().put("bucketName",minioUtil.removeBucket(bucketName));
    }

    @LogAround(value = "获取全部bucket")
    @GetMapping("/getAllBuckets")
    public Result getAllBuckets() {
        List<Bucket> allBuckets = minioUtil.getAllBuckets();
        return Result.ok().put("allBuckets",allBuckets);
    }

    @LogAround(value = "文件上传返回url")
    @PostMapping("/upload")
    public Result upload(@RequestParam("file") MultipartFile file) {
        String objectName = minioUtil.upload(file);
        if (null != objectName) {
            return Result.ok().put("url",(prop.getEndpoint() + "/" + prop.getBucketName() + "/" + objectName));
        }
        return Result.error();
    }

    @LogAround(value = "图片/视频预览")
    @GetMapping("/preview")
    public Result preview(@RequestParam("fileName") String fileName) {
        return Result.ok().put("filleName",minioUtil.preview(fileName));
    }

    @LogAround(value = "文件下载")
    @GetMapping("/download")
    public Result download(@RequestParam("fileName") String fileName, HttpServletResponse res) {
        minioUtil.download(fileName,res);
        return Result.ok();
    }

    @LogAround(value = "删除文件")
    @PostMapping("/delete")
    public Result remove(String url) {
        String objName = url.substring(url.lastIndexOf(prop.getBucketName()+"/") + prop.getBucketName().length()+1);
        minioUtil.remove(objName);
        return Result.ok().put("objName",objName);
    }

}
