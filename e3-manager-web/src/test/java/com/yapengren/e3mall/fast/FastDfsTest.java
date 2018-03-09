package com.yapengren.e3mall.fast;

import com.yapengren.e3mall.common.utils.FastDFSClient;
import org.csource.fastdfs.*;
import org.junit.Test;

/**
 * @author renyapeng
 */
public class FastDfsTest {

    /**
     * 图片上传
     * @throws Exception
     */
    @Test
    public void testUpload() throws Exception {
        // 创建一个配置文件。文件名任意，内容就是 tracker 服务器地址
        // 使用全局对象加载配置文件
        ClientGlobal.init("D:\\Develop\\idea_e3mall\\e3-manager-web\\src\\main\\resources\\conf\\client.conf");
        // 创建一个 TrackerClient 对象
        TrackerClient trackerClient = new TrackerClient();
        // 通过 TrackerClient 对象获得一个 TrackerServer 对象
        TrackerServer trackerServer = trackerClient.getConnection();
        // 创建一个 StorageServer 的引用，可以是 null
        StorageServer storageServer = null;
        // 创建一个 StorageClient，参数需要 TrackerServer 和 StorageServer
        StorageClient storageClient = new StorageClient(trackerServer, storageServer);
        // 使用 StorageClient 上传文件
        String[] strings = storageClient.upload_file("E:\\9、黑马就业班32期\\17、项目二：宜立方商城(80-93天）\\01.参考资料\\广告图片\\b463a2b010033397a2dcd09aa6f57d0c.jpg", "jpg", null);

        for (String string : strings) {
            System.out.println(string);
        }
    }

    /**
     * 图片上传-工具类
     * @throws Exception
     */
    @Test
    public void testFastDfsClient() throws Exception {
        FastDFSClient fastDFSClient = new FastDFSClient("D:\\Develop\\idea_e3mall\\e3-manager-web\\src\\main\\resources\\conf\\client.conf");
        String s = fastDFSClient.uploadFile("E:\\9、黑马就业班32期\\17、项目二：宜立方商城(80-93天）\\01.参考资料\\广告图片\\b463a2b010033397a2dcd09aa6f57d0c.jpg");
        System.out.println(s);
    }
}
