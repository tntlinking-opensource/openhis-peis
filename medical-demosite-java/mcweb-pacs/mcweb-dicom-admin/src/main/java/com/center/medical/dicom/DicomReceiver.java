package com.center.medical.dicom;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import cn.hutool.system.SystemUtil;
import com.center.medical.common.constant.Constants;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.dicom.bean.dto.DicomConfig;
import com.center.medical.dicom.service.DicomService;
import com.center.medical.system.service.ISysConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dcm4che3.data.Attributes;
import org.dcm4che3.data.Tag;
import org.dcm4che3.data.VR;
import org.dcm4che3.imageio.plugins.dcm.DicomImageReadParam;
import org.dcm4che3.imageio.plugins.dcm.DicomImageReader;
import org.dcm4che3.imageio.plugins.dcm.DicomImageReaderSpi;
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

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.util.HashMap;
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
public class DicomReceiver {

    private final ISysConfigService iSysConfigService;
    private final DicomService dicomService;
    /**
     * 接口类型转换
     */
    private Map<String, String> jklxs;//接口类型转换
    //图片后缀
    public static final String IMAGE_SUFFIX = ".jpg";

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
     * dcm图片存储的根目录
     */
    @Value(value = "${dicom.fileRoot}")
    private String fileRoot;
    /**
     * 是否开启接收
     */
    private Integer isEnabled;
    /**
     * 接收到dicom文件的临时存储目录
     */
    private String baseDir;

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
            log.info("进入store方法");

            rsp.setInt(Tag.Status, VR.US, 0);

            String cuid = rq.getString(Tag.AffectedSOPClassUID);
            //文件名,与原文件名一样
            String iuid = rq.getString(Tag.AffectedSOPInstanceUID);
            String tsuid = pc.getTransferSyntax();

            //dcm文件临时保存位置
            //如果文件名包含中文或者特殊字符则将中文转化成拼音，去掉特殊字符
            String dcmName = StringUtils.delSpecialChar(StringUtils.convertToPinyin(iuid));
            String fileSort = System.currentTimeMillis()+"";
            File dcmFile = new File(baseDir, fileSort + "_" + dcmName + PART_EXT);

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
            //DicomConfig dicomConfig = iSysConfigService.getSysConfigObject(Constants.DICOM_CONFIG, DicomConfig.class);
//        log.info("dicom配置信息：{}", JSONUtil.toJsonStr(dicomConfig));
            //jklxs = dicomConfig.getJklxs();


            //保存到本地目录
            //String fileName = dcmFile.getName();

//            try {
            //读取dcm信息
//                Map<String, String> infos = readInfo(dcmFile);
//                String patientcode = infos.get("patientID");
//                String enName = infos.get("bodyPartExamined");
//                String jklx = infos.get("modality");

//                log.info("体检号：" + patientcode, "，部位：" + enName, "，接口类型：" + jklx);
//                patientcode = ToolUtil.patientCode(patientcode, iSysBranchService.getBranchFlag(null));
//
//                List<DicomFeeitemDto> dicomFeeitemDtoList = dicomMapper.selectDicomFeeitemList(patientcode, enName, jklx);
//                if (dicomFeeitemDtoList.size() == 0) {
//                    //如果同名文件已经存在未处理的失败记录，就不再保存了
//                    //去掉文件名的时间戳前缀
//                    FailedDicomFile failedDicomFile = new FailedDicomFile();
//                    failedDicomFile.setBodyPart(enName);
//                    failedDicomFile.setCreateTime(new Date());
//                    failedDicomFile.setFileName(fileName);
//                    failedDicomFile.setIsProcessed(false);
//                    failedDicomFile.setJklx(jklx);
//                    failedDicomFile.setPatientcode(patientcode);
//                    failedDicomFile.setUpdateTime(new Date());
//                    failedDicomFile.setRkey(CacheConstants.FAILED_DICOM_FILE_KEY + failedDicomFile.getPatientcode() + failedDicomFile.getBodyPart() + failedDicomFile.getJklx());
//                    failedDicomFile.setRemark("dicom接收收费项目不存在," + "体检号:" + patientcode + "，需要的部位:" + enName + "，需要的接口类型:" + jklx);
//                    saveFailedDicomFileToRedis(failedDicomFile);
////            }
//                    throw new ServiceException("dicom接收收费项目不存在," + "体检号:" + patientcode + "，需要的部位:" + enName + "，需要的接口类型:" + jklx);
//                }
//                DicomFeeitemDto dicomFeeitemDto = dicomFeeitemDtoList.get(0);
//                String deptNo = dicomFeeitemDto.getDeptNo();
//                String baseDir = systemConfig.getFilePathConfig(FilePathConfigFlag.IPP.value())
//                        + "/"
//                        + iSysDeptService.getByDeptNo(deptNo).getInputCode();
//                String extName = FileUtil.extName(dcmFile.getName());
//                String filePath = ToolUtil.randomFilePath(extName, patientcode + "/" + dcmFile.getName());
//                String dcmFilePath = "/" + baseDir + "/" + filePath;
//                log.info("保存的dcm图片路径：{}", dcmFilePath);
//                //保存dicom图片
//                Files.copy(dcmFile, new File(fileRoot + dcmFilePath));

            //保存Jpeg图片
//                String imageFilePath = fileRoot + "/" + baseDir + "/" + ToolUtil.randomFilePath(IMAGE_SUFFIX, patientcode + "/" + fileName.substring(0, fileName.lastIndexOf(".")) + IMAGE_SUFFIX);
//                log.info("保存的Jpeg图片路径：{}", dcmFilePath);
//                File imageFile = new File(imageFilePath);
//                convertDicomToJpeg(dcmFile.getAbsolutePath(), imageFile.getAbsolutePath());
            //保存
//                infos.put("shortCode", String.valueOf(dicomFeeitemDto.getShortCode()));
//                infos.put("feeitemId", dicomFeeitemDto.getId());
//                infos.put("deptNo", deptNo);
            dicomService.saveDicomPath(dcmFile, "");

            //删除本地临时dcm文件
//                dcmFile.delete();

//                resultMsg.append("文件<font color='green'>" + fileName + "</font>上传成功！</br>");
//            } catch (Exception e) {
//                log.error(e.getMessage(), e);
////                resultMsg.append("文件<font color='red'>" + fileName + "</font>上传失败," + e.getMessage() + "</br>");
//            }
        } catch (Exception e) {
            //上传失败，重发发送（最多重发3次）
            log.error("dcm图片保存失败：{}", e);
        }
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
//            log.info("patientID:" + patientID);
            String bodyPartExamined = attributes.getString(Tag.BodyPartExamined);
//            log.info("bodyPartExamined:" + bodyPartExamined);
            String modality = attributes.getString(Tag.Modality);
            if (jklxs.containsKey(modality)) {
                modality = jklxs.get(modality);
            }
//            log.info("modality:" + modality);
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

    @PostConstruct
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

    public static void main(String[] args) {
//        String dicomUrl = "http://XXX.XXX.XXX.XXX/resours_host/newimage/images/image/01/FSKCT/20240219/010230107454/1708307869395_1.2.840.113704.1.111.8060.1708307825.4986.dcm";
        String dicomUrl = "http://XXX.XXX.XXX.XXX/resours_host/newimage/images/test/ANKE1.DCM";
        File file = new File(dicomUrl);
        System.out.println(file.getName() + "-------" + file.getAbsoluteFile());

        try {
            URL url = new URL(dicomUrl);
            InputStream inputStream = url.openStream();

            DicomInputStream dicomInputStream = new DicomInputStream(inputStream);
            Attributes attributes = dicomInputStream.readDataset(-1, Tag.PixelData);

            Tag tag = new Tag();

            // 获取类的所有字段
            Field[] fields = Tag.class.getDeclaredFields();

//            // 遍历字段并打印属性名和值
            for (Field field : fields) {
                try {
                    // 设置字段可访问，即使是私有字段也可以获取其值
                    field.setAccessible(true);

                    // 获取字段的值
                    Object value = field.get(tag);

                    // 打印属性名和值

                    if (field.getType() == int.class) {
                        if (!attributes.getString((Integer) value, "null").equals("null"))
                            System.out.println(field.getName() + ": " + attributes.getString((Integer) value, "null"));
                    } else if (field.getType() == long.class) {
                        if (Objects.nonNull(attributes.getDate("null", (Long) value)))
                            System.out.println(field.getName() + ": " + attributes.getDate("null", (Long) value));
                    }

                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }

            System.out.println("dicom图片名称：" + attributes.getString(Tag.SOPInstanceUID, "null"));

            System.out.println("dicom图片信息：" + JSONUtil.toJsonStr(attributes));
            System.out.println("ProtocolName：" + attributes.getString(Tag.ProtocolName, "null"));
            System.out.println("dicom图片信息检查部位AnatomicRegionSequence：" + attributes.getString(Tag.AnatomicRegionSequence, "null"));

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
