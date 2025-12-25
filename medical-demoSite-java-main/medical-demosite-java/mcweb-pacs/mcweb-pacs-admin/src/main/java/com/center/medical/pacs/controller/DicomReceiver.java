package com.center.medical.pacs.controller;

import com.center.medical.bean.vo.MultUploadResultVo;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.uuid.UUID;
import com.center.medical.dicom.service.DicomService;
import com.center.medical.system.service.ISysConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dcm4che3.data.Attributes;
import org.dcm4che3.data.Tag;
import org.dcm4che3.imageio.plugins.dcm.DicomImageReadParam;
import org.dcm4che3.imageio.plugins.dcm.DicomImageReader;
import org.dcm4che3.imageio.plugins.dcm.DicomImageReaderSpi;
import org.dcm4che3.io.DicomInputStream;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * dicom接收
 *
 * @author xhp
 * @since 2023-04-17 10:42
 * 重复上传相同文件会覆盖目录下同名文件
 * 测试时没有发送任何dicom文件时会报错
 * 需要添加/medical-center-java/lib下的jar包
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DicomReceiver {

    private final ISysConfigService iSysConfigService;
    private final DicomService dicomService;
    /**
     * 接口类型转换
     */
    private Map<String, String> jklxs;//接口类型转换
    //图片后缀
    public static final String IMAGE_SUFFIX = ".jpg";

    /**
     * 科室上传dicom文件
     */
    public MultUploadResultVo upload(List<MultipartFile> files, String feeitemId) {
//        DicomConfig dicomConfig = iSysConfigService.getSysConfigObject(Constants.DICOM_CONFIG, DicomConfig.class);
//        log.info("dicom配置信息：{}", JSONUtil.toJsonStr(dicomConfig));
//        jklxs = dicomConfig.getJklxs();
        MultUploadResultVo multUploadResultVo = new MultUploadResultVo();
        //临时保存到本地根目录
        String baseDir = System.getProperty("user.dir");
        String folderPath = baseDir + File.separator + "dicomUpload" + File.separator + UUID.randomUUID();
        File folder = new File(folderPath);
        folder.mkdirs();
        List<String> successList = new ArrayList<>();
        List<String> failList = new ArrayList<>();
        StringBuilder resultMsg = new StringBuilder();
        for (MultipartFile file : files) {
            String fileName = file.getOriginalFilename();
            //如果文件名包含中文或者特殊字符则将中文转化成拼音，去掉特殊字符
            String dcmName = StringUtils.delSpecialChar(StringUtils.convertToPinyin(fileName));
            //dcm文件临时保存位置
            File dcmFile = new File(folderPath, System.currentTimeMillis() + "_" + dcmName);
            //图片临时保存位置
//            File imageFile = new File(folderPath, fileName.substring(0, fileName.lastIndexOf(".")) + IMAGE_SUFFIX);
            try {
                //保存dcm图片
                file.transferTo(dcmFile);
                //保存Jpeg图片
//                convertDicomToJpeg(dcmFile.getAbsolutePath(), imageFile.getAbsolutePath());
                //读取dcm信息
//                Map<String, String> infos = readInfo(dcmFile);
                //上传并保存
                dicomService.saveDicomPath(dcmFile, feeitemId);

                successList.add(fileName);
                resultMsg.append("文件<font color='green'>" + fileName + "</font>上传成功！</br>");
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                failList.add(fileName);
                resultMsg.append("文件<font color='red'>" + fileName + "</font>上传失败," + e.getMessage() + "</br>");
            } finally {
                dcmFile.delete();
            }
        }
        folder.delete();
        multUploadResultVo.setSuccessList(successList);
        multUploadResultVo.setFailList(failList);
        multUploadResultVo.setResultMsg(resultMsg.toString());
        return multUploadResultVo;
    }

    /**
     * Dicom转Jpeg
     *
     * @param dicomFilePath
     * @param jpegFilePath
     * @throws IOException
     */
    public void convertDicomToJpeg(String dicomFilePath, String jpegFilePath) throws IOException {
        // 创建ImageInputStream
        File dicomFile = new File(dicomFilePath);
        ImageInputStream iis = ImageIO.createImageInputStream(dicomFile);

        // 创建DicomImageReader
        ImageReader reader = new DicomImageReader(new DicomImageReaderSpi());
        reader.setInput(iis);

        // 读取DICOM图像
        BufferedImage dicomImage = reader.read(0, new DicomImageReadParam());

        // 将图像保存为JPEG文件
        File jpegFile = new File(jpegFilePath);
        ImageIO.write(dicomImage, "JPEG", jpegFile);

        // 关闭资源
        reader.dispose();
        iis.close();
    }

    /**
     * 读取信息
     *
     * @param dcmFile
     * @return
     * @throws IOException
     */
    public Map<String, String> readInfo(File dcmFile) throws IOException {
        try (
                DicomInputStream dicomInputStream = new DicomInputStream(dcmFile);
        ) {
            Attributes attributes = dicomInputStream.readDataset();
            String patientID = attributes.getString(Tag.PatientID);
            log.info("patientID:" + patientID);
            String bodyPartExamined = attributes.getString(Tag.BodyPartExamined);
            log.info("bodyPartExamined:" + bodyPartExamined);
            String modality = attributes.getString(Tag.Modality);
            if (jklxs.containsKey(modality)) {
                modality = jklxs.get(modality);
            }
            log.info("modality:" + modality);
            Map<String, String> result = new HashMap<>();
            result.put("patientID", patientID);
            result.put("bodyPartExamined", bodyPartExamined);
            if (jklxs.containsKey(modality)) {
                String jklx = jklxs.get(modality);
//                log.info("接口类型" + modality + "转换为" + jklx);
                modality = jklx;
            }
            result.put("modality", modality);
            return result;
        }
    }

    /**
     * 这个方法会报错
     *
     * @param attr
     */
    @Deprecated
    public void logAttr(Attributes attr) {
        Map<String, Object> map = attr.getProperties();
        for (String key : map.keySet()) {
            log.info(key + ":" + map.get(key));
        }
    }

}
