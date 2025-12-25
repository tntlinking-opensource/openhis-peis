package com.center.medical.dicom.task;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.center.medical.bean.vo.MultUploadResultVo;
import com.center.medical.common.constant.DicomConstants;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.exception.ServiceException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
public class UploadTask {
    /**
     * 主系统地址
     */
    @Value(value = "${dicom.mianApiUrl}")
    private String mianApiUrl;

    /**
     * 每隔5分钟执行一次上传dicom图片功能:5 * 60 * 1000
     */
//    @Scheduled(fixedRate = 10000)
    public void myTask() {
        System.out.println("定时任务执行了：" + new Date());

        // 获取DICOM文件目录
        String imagePath = DicomConstants.FAILED_FILE_PATH;
        File dicomDirectory = new File(imagePath);
        File[] dicomFiles = dicomDirectory.listFiles();

        if (dicomFiles != null) {
            for (File file : dicomFiles) {
                if (file.isFile()) {
                    String fileName = file.getName();
                    if (fileName.endsWith(".dcm") || fileName.endsWith(".jpg")) {
                        log.info("上传文件：{}", mianApiUrl + "/lan/images/uploadDicom", file);
                        try {
                            HttpClient httpClient = HttpClients.createDefault();
                            HttpPost httpPost = new HttpPost(mianApiUrl + "/lan/images/uploadDicom");
                            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
                            builder.addBinaryBody("files", file, ContentType.DEFAULT_BINARY, file.getName());
                            httpPost.setEntity(builder.build());
                            HttpResponse response = httpClient.execute(httpPost);
                            log.info("上传结果response：{}", JSONUtil.toJsonStr(response));
                            String responseBody = EntityUtils.toString(response.getEntity());

                            // 获取响应体的内容
                            log.info("上传结果responseBody：{}", responseBody);
                            // 使用Jackson解析JSON响应
                            ObjectMapper objectMapper = new ObjectMapper();
                            R<MultUploadResultVo> uploadResult = null;
                            try {
                                uploadResult = objectMapper.readValue(responseBody, new TypeReference<R<MultUploadResultVo>>() {
                                });
                            } catch (JsonProcessingException e) {
                                throw new ServiceException("上传失败：" + JSONUtil.toJsonStr(e));
                            }

                            // 现在您可以访问MultUploadResultVo中的字段
                            MultUploadResultVo uploadResultVo = uploadResult.getData();
                            log.info("上传结果uploadResultVo：{}", JSONUtil.toJsonStr(uploadResultVo));
                            List<String> successList = uploadResultVo.getSuccessList();
                            if (CollectionUtil.isNotEmpty(successList)) {
                                successList.forEach(item -> {
                                    //成功的删除本地缓存
                                    log.info("删除本地缓存：{}、{}、{}", file.getName().equals(item), item, file.getName());
                                    if (file.getName().equals(item)) {
                                        file.delete();
                                    }
                                });
                            }
                        } catch (Exception e) {
                            //上传失败，重发发送（最多重发3次）
                            log.error("上传失败：{}", e);
                        }
                    }
                }
            }

        }
    }
}
