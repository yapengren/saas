package com.yapengren.e3mall.controller;

import com.yapengren.e3mall.common.utils.FastDFSClient;
import com.yapengren.e3mall.common.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * 图片上传处理 Controller
 *
 * @author renyapeng
 */
@RestController
public class PictureController {

    @Value("${IMAGE_SERVER_URL}")
    private String imageServerURL;

    @RequestMapping(value = "/pic/upload", produces = MediaType.TEXT_PLAIN_VALUE + ";charset=utf-8")
    public String uploadFile(MultipartFile uploadFile) {

        try {
            // 把图片上传的图片服务器
            FastDFSClient fastDFSClient = new FastDFSClient("classpath:conf/client.conf");
            // 取文件扩展名
            String originalFilename = uploadFile.getOriginalFilename();
            String extName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
            // 得到一个图片的地址和文件名
            String url = fastDFSClient.uploadFile(uploadFile.getBytes(), extName);
            // 补充为完成的 url
            url = "http://" + imageServerURL + "/" + url;

            // 封装到 map 中返回
            Map result = new HashMap<>();
            result.put("error", 0);
            result.put("url", url);

            // 解决浏览器兼容性问题，把 java 对象转换成字符串
            return JsonUtils.objectToJson(result);
        } catch (Exception e) {
            e.printStackTrace();
            Map result = new HashMap<>();
            result.put("error", 1);
            result.put("message", "图片上传失败");
            return JsonUtils.objectToJson(result);
        }
    }
}
