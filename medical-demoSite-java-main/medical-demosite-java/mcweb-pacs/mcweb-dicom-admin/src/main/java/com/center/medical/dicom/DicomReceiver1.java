package com.center.medical.dicom;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONUtil;
import cn.hutool.system.SystemUtil;
import com.center.medical.bean.vo.MultUploadResultVo;
import com.center.medical.common.constant.Constants;
import com.center.medical.common.constant.DicomConstants;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.dicom.bean.dto.DicomConfig;
import com.center.medical.system.service.ISysConfigService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dcm4che3.data.Attributes;
import org.dcm4che3.data.Tag;
import org.dcm4che3.data.VR;
import org.dcm4che3.io.DicomInputStream;
import org.dcm4che3.io.DicomOutputStream;
import org.dcm4che3.net.*;
import org.dcm4che3.net.pdu.PresentationContext;
import org.dcm4che3.net.service.BasicCEchoSCP;
import org.dcm4che3.net.service.BasicCStoreSCP;
import org.dcm4che3.net.service.DicomServiceException;
import org.dcm4che3.net.service.DicomServiceRegistry;
import org.dcm4che3.util.SafeClose;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.StandardCopyOption;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
public class DicomReceiver1 {

    private final ISysConfigService iSysConfigService;

    @Value(value = "${dicom.ip}")
    private String ip;
    /**
     * 端口号
     */
    @Value(value = "${dicom.port}")
    private int port;
    /**
     * 设备的应用实体名称
     */
    @Value(value = "${dicom.callingAET}")
    private String callingAET;
    /**
     * 主系统地址
     */
    @Value(value = "${dicom.mianApiUrl}")
    private String mianApiUrl;
    /**
     * 是否开启接收
     */
    private Integer isEnabled;
    /**
     * 接收到dicom文件的临时存储目录
     */
    private String baseDir;
    /**
     * 接口类型转换
     */
    private Map<String, String> jklxs;//接口类型转换

    private Device device;
    private final ApplicationEntity ae = new ApplicationEntity("*");
    private final Connection conn = new Connection();

    ExecutorService executorService = Executors.newCachedThreadPool();

    //临时文件后缀
    public static final String PART_EXT = ".dcm";

    private final BasicCStoreSCP cstoreSCP = new BasicCStoreSCP("*") {

        @Override
        protected void store(Association as, PresentationContext pc,
                             Attributes rq, PDVInputStream data, Attributes rsp)
                throws IOException {
//            log.info("进入store方法");

            rsp.setInt(Tag.Status, VR.US, 0);

            String cuid = rq.getString(Tag.AffectedSOPClassUID);
            //文件名,与原文件名一样
            String iuid = rq.getString(Tag.AffectedSOPInstanceUID);
            String tsuid = pc.getTransferSyntax();

            //dcm文件临时保存位置
            File dcmFile = new File(baseDir, System.currentTimeMillis() + "_" + iuid + PART_EXT);

            try {
                Attributes fmi = as.createFileMetaInformation(iuid, cuid, tsuid);
                //先临时保存到本地读取数据，然后调用通用方法将文件保存到正确的位置
                //保存dcm文件
                storeTo(fmi, data, dcmFile);
                doUpload(dcmFile, 0);
            } catch (Exception e) {
                throw new DicomServiceException(Status.ProcessingFailure, e);
            }
        }
    };

    /**
     * 上传图片
     *
     * @param dcmFile
     * @param flag    重发次数
     */
    private void doUpload(File dcmFile, Integer flag) {
        try {
            HttpResponse response = HttpRequest.post(mianApiUrl + "/lan/images/uploadDicom")
                    .form("files", dcmFile) // 发送图片文件
                    .execute();
            // 检查响应是否成功
            if (response.isOk()) {
                // 获取响应体的内容
                String responseBody = response.body();
                log.info("请求成功：{}", responseBody);
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
                List<String> failList = uploadResultVo.getFailList();
                if (CollectionUtil.isNotEmpty(failList)) {
                    log.error("上传失败的文件：{}", failList);
                    failList.forEach(item -> {
                        if (dcmFile.getName().equals(item)) {
                            //保存到保存至失败文件夹中
                            File failFile = new File(DicomConstants.FAILED_FILE_PATH, dcmFile.getName());
                            FileUtil.copyFile(dcmFile, failFile, StandardCopyOption.REPLACE_EXISTING);
                        }
                    });
                }
            } else {
                //上传失败，重发发送（最多重发3次）
                log.error("上传失败，请求失败！");
                if (flag > 2) {
                    doUpload(dcmFile, ++flag);
                } else {
                    //保存到保存至失败文件夹中
                    File failFile = new File(DicomConstants.FAILED_FILE_PATH, dcmFile.getName());
                    FileUtil.copyFile(dcmFile, failFile, StandardCopyOption.REPLACE_EXISTING);
                }
            }
        } catch (Exception e) {
            //上传失败，重发发送（最多重发3次）
            log.error("上传失败：{}", e);
            if (flag > 2) {
                doUpload(dcmFile, ++flag);
            } else {
                //保存到保存至失败文件夹中
                File failFile = new File(DicomConstants.FAILED_FILE_PATH, dcmFile.getName());
                FileUtil.copyFile(dcmFile, failFile, StandardCopyOption.REPLACE_EXISTING);
            }
        } finally {
            //删除临时文件
            if (FileUtil.exist(dcmFile)) {
                dcmFile.delete();
            }
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

    public void startService() throws IOException, GeneralSecurityException {
        device.setExecutor(executorService);
        device.bindConnections();
    }

    private DicomServiceRegistry createServiceRegistry() {
        DicomServiceRegistry serviceRegistry = new DicomServiceRegistry();
        serviceRegistry.addDicomService(new BasicCEchoSCP());
        serviceRegistry.addDicomService(cstoreSCP);
        return serviceRegistry;
    }

    private void storeTo(Attributes fmi, PDVInputStream data, File file) throws IOException {
//        log.info("进入storeTo方法");
//        log.info("file:" + file.getAbsolutePath());
        file.getParentFile().mkdirs();
        DicomOutputStream out = new DicomOutputStream(file);
        try {
            out.writeFileMetaInformation(fmi);
            data.copyTo(out);
        } finally {
            SafeClose.close(out);
        }
    }

    //    @PostConstruct
    public void init() {
        try {
            if (StrUtil.isEmpty(callingAET)) {
                log.error("dicom接收初始化失败，没有配置PACS_CONFIG.dicom.CallingAET");
                return;
            }
            DicomConfig dicomConfig = iSysConfigService.getSysConfigObject(Constants.DICOM_CONFIG, DicomConfig.class);
//            log.info("dicom接收初始化DicomReceiver.init：{}、{}、{}、{}", port, callingAET, isEnabled, dicomConfig);
            if (Objects.nonNull(dicomConfig)) {
                //是否开启接收
                if (dicomConfig.getIsEnabled() == null || dicomConfig.getIsEnabled() != 1) {
                    log.error("dicom接收初始化停止,配置项PACS_CONFIG.dicom.isEnabled的值不是1");
                    return;
                }
                isEnabled = dicomConfig.getIsEnabled();
                jklxs = dicomConfig.getJklxs();
            } else {
                log.error("dicom接收初始化停止,DicomConfig配置为空！");
                return;
            }
            //临时保存到本地根目录
            baseDir = System.getProperty("user.dir") + "/images";

            device = new Device(callingAET);
            device.setDimseRQHandler(createServiceRegistry());
            device.addConnection(conn);
            device.addApplicationEntity(ae);
            ae.setAssociationAcceptor(true);
            ae.addConnection(conn);

            //使用本机Ip
//            log.info("本机Ip:{}、配置文件的IP：{}", SystemUtil.getHostInfo().getAddress(), ip);
            ip = StrUtil.isBlank(ip) ? SystemUtil.getHostInfo().getAddress() : ip;
            log.info("Dicom初始化参数：ip：{}、port：{}、callingAET：{}、baseDir：{}", ip, port, callingAET, baseDir);
            conn.setHostname(ip);
            conn.setPort(port);
            ae.setAETitle(callingAET);
            ae.addTransferCapability(new TransferCapability(null, "*", TransferCapability.Role.SCP, "*"));
            startService();
            log.info("dicom接收初始化成功");

        } catch (Exception e) {
            log.error("dicom接收初始化失败", e);
        }

    }

    public static void main1(String[] args) {
        String dicomUrl = "http://XXX.XXX.XXX.XXX:8080/newimage/images/image/01/FSKCT/20230810/010230000104/202371/10/1.2.840.113704.1.111.10352.1691560611.3.dcm";

        try {
            URL url = new URL(dicomUrl);
            InputStream inputStream = url.openStream();

            DicomInputStream dicomInputStream = new DicomInputStream(inputStream);
            Attributes attributes = dicomInputStream.readDataset(-1, Tag.PixelData);

            double windowCenter = attributes.getDouble(Tag.WindowCenter, 0.0);
            double windowWidth = attributes.getDouble(Tag.WindowWidth, 0.0);

            System.out.println("Window Center: " + windowCenter);
            System.out.println("Window Width: " + windowWidth);

            dicomInputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
