package com.center.medical.dicom.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.enums.FilePathConfigFlag;
import com.center.medical.bean.model.Attachment;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.bean.vo.DicomInfoVo;
import com.center.medical.common.config.ZhongkangConfig;
import com.center.medical.common.constant.CacheConstants;
import com.center.medical.common.constant.Constants;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.ToolUtil;
import com.center.medical.common.utils.ftp.FtpUtil;
import com.center.medical.common.utils.redis.RedisSetUtil;
import com.center.medical.common.utils.redis.RedisUtil;
import com.center.medical.dicom.bean.dto.DicomConfig;
import com.center.medical.dicom.bean.dto.DicomFeeitemDto;
import com.center.medical.dicom.bean.model.FailedDicomFile;
import com.center.medical.dicom.dao.DicomMapper;
import com.center.medical.dicom.service.DicomService;
import com.center.medical.service.AttachmentService;
import com.center.medical.service.PeispatientService;
import com.center.medical.system.config.SystemConfig;
import com.center.medical.system.service.ISysBranchService;
import com.center.medical.system.service.ISysConfigService;
import com.center.medical.system.service.ISysDeptService;
import com.google.common.io.Files;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dcm4che3.data.Attributes;
import org.dcm4che3.data.Tag;
import org.dcm4che3.imageio.plugins.dcm.DicomImageReadParam;
import org.dcm4che3.imageio.plugins.dcm.DicomImageReader;
import org.dcm4che3.imageio.plugins.dcm.DicomImageReaderSpi;
import org.dcm4che3.io.DicomInputStream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * dicom
 *
 * @author xhp
 * @since 2023-04-13 9:00
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DicomServiceImpl extends ServiceImpl<DicomMapper, Attachment> implements DicomService {
    private final ISysBranchService iSysBranchService;
    private final AttachmentService attachmentService;
    private final ISysConfigService iSysConfigService;
    private final SystemConfig systemConfig;
    private final ISysDeptService iSysDeptService;
    private final PeispatientService peispatientService;
    private final ExecutorService executorService = Executors.newFixedThreadPool(6);
    /**
     * 接口类型转换
     */
    private Map<String, String> jklxs;//接口类型转换
    /**
     * wins系统下dcm图片存储的根目录
     */
    @Value(value = "${dicom.fileRoot}")
    private String fileRootWin;
    /**
     * Linux系统下dcm图片存储的根目录
     */
    @Value(value = "${dicom.fileRootLinux}")
    private String fileRootLinux;
    /**
     * 图片后缀
     */
    public static final String IMAGE_SUFFIX = ".jpg";

    //临时文件后缀
    public static final String PART_EXT = ".dcm";

    public static void main(String[] args) {
        String enName = "ZK C-Spine/Neck dd";
        List<String> enNameList = Arrays.stream(enName.split("/")).map(item -> item.toUpperCase(Locale.ROOT)).collect(Collectors.toList());
        System.out.println(JSONUtil.toJsonStr(enNameList));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Attachment saveDicomPath(File dcmFile, String feeItemId) throws IOException {
//        Date startTime = new Date();
        feeItemId = feeItemId.equals("undefined") ? "" : feeItemId;
        DicomConfig dicomConfig = iSysConfigService.getSysConfigObject(Constants.DICOM_CONFIG, DicomConfig.class);
//        log.info("dicom配置信息：{}", JSONUtil.toJsonStr(dicomConfig));
        jklxs = dicomConfig.getJklxs();
        DicomInfoVo dicomInfoVo = readInfo(dcmFile);
//        log.info("dicom图片信息：{}", JSONUtil.toJsonStr(dicomInfoVo));
        String fileSort = System.currentTimeMillis()+"";
        String fileName = fileSort + "_" + (StringUtils.isNotBlank(dicomInfoVo.getSOPInstanceUID())?(dicomInfoVo.getSOPInstanceUID() + PART_EXT):dcmFile.getName());
        String patientcode = null;
        if (StringUtils.isNotBlank(feeItemId)){
            List<DicomFeeitemDto> dicomFeeitemDtoList = baseMapper.getInfoById(feeItemId);
            if(CollectionUtil.isEmpty(dicomFeeitemDtoList)) {
                log.error("体检项目id={}不存在", feeItemId);
                return null;
            }
            patientcode = dicomFeeitemDtoList.get(0).getIdPatient();
        }else {
            patientcode = dicomInfoVo.getPatientcode();
        }


        if (patientcode.startsWith("APP")) {
            patientcode = ZhongkangConfig.getFzxjm() + StrUtil.subSufByLength(patientcode, 10);
        }
        String enName = dicomInfoVo.getEnName().trim();
        String jklx = dicomInfoVo.getJklxs();
        String viewPosition = StringUtils.isNotBlank(dicomInfoVo.getViewPosition()) ? dicomInfoVo.getViewPosition().trim() : "";
        log.info("体检号：" + patientcode + "，部位：" + enName + "，viewPosition：" + viewPosition + "，接口类型：" + jklx+"，图片名称："+fileName);
        String branchFlag = iSysBranchService.getBranchFlag(null);
//        log.info("branchFlag={}", branchFlag);
        patientcode = ToolUtil.patientCode(patientcode, branchFlag);
        //部位列表
        List<String> enNameList = new ArrayList<>();
        if (StringUtils.isNotBlank(enName)) {
            enNameList = Arrays.stream(enName.split("/")).collect(Collectors.toList());
        }
        //部位拼接位置
        List<String> enNameWithViewPointList = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(enNameList) && StringUtils.isNotBlank(viewPosition)) {
            for (String part : enNameList) {
                enNameWithViewPointList.add((part + "-" + viewPosition).trim());
            }
        }

//        log.info("部位：{}", enNameList);
        List<DicomFeeitemDto> dicomFeeitemDtoList = new ArrayList<>();
        if (StringUtils.isBlank(feeItemId)) {
            // 现在需要精准匹配到项目,由于各个中心的机器和维护的部位都不一样，现在需要查三次
            // 先用enname+viewpoint查，没查到在用viewpoint查，再没查到就用enname查
            if (CollectionUtil.isNotEmpty(enNameWithViewPointList)){
                dicomFeeitemDtoList = baseMapper.selectDicomFeeitemList(patientcode, enNameWithViewPointList, jklx);
            }
            if (CollectionUtil.isEmpty(dicomFeeitemDtoList) && StringUtils.isNotBlank(viewPosition)){
                dicomFeeitemDtoList = baseMapper.selectDicomFeeitemList(patientcode, Arrays.asList(viewPosition), jklx);
            }
            if (CollectionUtil.isEmpty(dicomFeeitemDtoList) && CollectionUtil.isNotEmpty(enNameList)){
                dicomFeeitemDtoList = baseMapper.selectDicomFeeitemList(patientcode, enNameList, jklx);
            }
        } else {
            dicomFeeitemDtoList = baseMapper.getInfoById(feeItemId);
        }

        if (dicomFeeitemDtoList.size() == 0) {
            //如果同名文件已经存在未处理的失败记录，就不再保存了
            FailedDicomFile failedDicomFile = new FailedDicomFile();
            failedDicomFile.setBodyPart(enName + "," + dicomInfoVo.getViewPosition());
            failedDicomFile.setCreateTime(new Date());
            failedDicomFile.setFileName(fileName);
            failedDicomFile.setIsProcessed(false);
            failedDicomFile.setJklx(jklx);
            failedDicomFile.setPatientcode(patientcode);
            failedDicomFile.setUpdateTime(new Date());
            failedDicomFile.setRkey(CacheConstants.FAILED_DICOM_FILE_KEY + failedDicomFile.getPatientcode() + failedDicomFile.getBodyPart() + failedDicomFile.getJklx());
            failedDicomFile.setRemark("dicom接收收费项目不存在," + "体检号:" + patientcode + "，需要这些部位中其中一个:" + JSONUtil.toJsonStr(enNameList) + "，需要的接口类型:" + jklx);
            saveFailedDicomFileToRedis(failedDicomFile);
//            }
            log.error("dicom接收收费项目不存在," + "体检号:" + patientcode + "，需要的部位:" + enName + "," + dicomInfoVo.getViewPosition() + "，需要的接口类型:" + jklx);
            return null;
//            throw new ServiceException("dicom接收收费项目不存在," + "体检号:" + patientcode + "，需要的部位:" + enName + "，需要的接口类型:" + jklx);
        }
        DicomFeeitemDto dicomFeeitemDto = dicomFeeitemDtoList.get(0);
        patientcode = dicomFeeitemDto.getIdPatient();
        String deptNo = dicomFeeitemDto.getDeptNo();
        Integer shortCode = dicomFeeitemDto.getShortCode();
        String feeitemId = dicomFeeitemDto.getId();

        //去掉文件名的时间戳前缀
        String dName = fileName.substring(fileName.indexOf("_") + 1);
        String originFileName = dName.substring(0, dName.lastIndexOf(".")) + IMAGE_SUFFIX;
//        log.info("feeitemId={}、originFileName={}", feeitemId, originFileName);
        if (attachmentService.list(new QueryWrapper<Attachment>().eq("memo", originFileName)
                .eq("fee_item_id", feeitemId)).size() > 0
        ) {
            log.error("同名文件已经接收过，不再重复接收，" + fileName);
            return null;
        }

        String baseDir = systemConfig.getFilePathConfig(FilePathConfigFlag.IPP.value())
                + "/"
                + iSysDeptService.getByDeptNo(deptNo).getInputCode();
        String extName = FileUtil.extName(fileName);
        String filePath = ToolUtil.randomFilePath(extName, patientcode + "/" + fileName);
        String dcmFilePath = "/" + baseDir + "/" + filePath;
//        log.info("保存的dcm图片路径：{}", dcmFilePath);
        String fileRoot = fileRootWin;
        String osName = System.getProperty("os.name").toLowerCase();
        if (osName.contains("linux")) {
            // Linux系统
            fileRoot = fileRootLinux;
        }
//        log.info("保存的dcm图片的fileRoot：{}", fileRoot);

        String folderPath1 = StrUtil.subBefore(fileRoot + dcmFilePath, "/", true);
//        log.info("保存的dcm图片路径是否存在1：{}", !FileUtil.isDirectory(folderPath1));
        if (!FileUtil.isDirectory(folderPath1)) {
            File folder = new File(folderPath1);
            folder.mkdirs();
        }
//        log.info("保存的dcm图片路径是否存在1：{}", !FileUtil.isDirectory(folderPath1));
        File dFile = new File(fileRoot + dcmFilePath);
        //保存dicom图片
//        executorService.submit(() -> {
//            try {
//                Files.copy(dcmFile, dFile);
//            } catch (IOException e) {
//                log.info("dicom图片保存失败");
//            }
//        });


        //保存Jpeg图片
        String imageFilePath = "/" + baseDir + "/" + ToolUtil.randomFilePath(IMAGE_SUFFIX, patientcode + "/" + fileName.substring(0, fileName.lastIndexOf(".")) + IMAGE_SUFFIX);
//        log.info("保存的Jpeg图片路径：{}", imageFilePath);
        String folderPath2 = StrUtil.subBefore(fileRoot + imageFilePath, "/", true);
        if (!FileUtil.isDirectory(folderPath2)) {
            File folder = new File(folderPath2);
            folder.mkdirs();
        }

        File imageFile = new File(fileRoot + imageFilePath);
//        executorService.submit(() -> {
//            try {
//                convertDicomToJpeg(dcmFile, imageFile);
//            } catch (IOException e) {
//                log.info("dicom图片保存失败");
//            }
//            dcmFile.delete();
//        });


        Attachment attachment = new Attachment();
        attachment.setBw(enName);
        attachment.setInReport(0);
        attachment.setPatientcode(patientcode);
        attachment.setShortCode(shortCode);
        attachment.setFeeItemId(feeitemId);
        attachment.setPacsFeeItemId(feeitemId);
        attachment.setDepId(deptNo);
        String brandId = StringUtils.isBlank(ZhongkangConfig.getFzxId()) ? iSysBranchService.getDefaultBranch().getBranchId() : ZhongkangConfig.getFzxId();
//        log.info("brandId={}", brandId);
        attachment.setBranchId(brandId);
        attachment.setType(1);
        attachment.setStatus(0);
        attachment.setCreatedate(new Date());
        attachment.setFileType("PACS");
        attachment.setFilePath(Constants.RESOURCE_PREFIX + imageFilePath);
        attachment.setFileSize(Math.toIntExact(dcmFile.length()));
        attachment.setMemo(originFileName);
        if (StringUtils.isNotBlank(dcmFilePath)) {
            //判断体检者名称、性别、年龄是否为空，为空则从体检者里取
            if (StringUtils.isBlank(dicomInfoVo.getPatientSex()) || StringUtils.isBlank(dicomInfoVo.getPatientName()) || StringUtils.isBlank(dicomInfoVo.getAge())) {
                Peispatient peispatient = peispatientService.getByPatientCode(patientcode);
                dicomInfoVo.setPatientSex(peispatient.getIdSex() == 0 ? "M" : "F");
                dicomInfoVo.setPatientName(peispatient.getPatientname());
                dicomInfoVo.setAge("0" + peispatient.getAge());
            }
            String dicomInfo = JSONUtil.toJsonStr(dicomInfoVo);
            dcmFilePath = dcmFilePath + "," + dicomInfo;
        }
        attachment.setDcmPath(Constants.RESOURCE_PREFIX + dcmFilePath);
        attachment.setFileSort(fileSort);
//        log.info("图片接收开始：{}", attachment.getFilePath());
//        if (loadProperties.name.equals("dr")) {
//            try {
//                Files.copy(dcmFile, dFile);
//            } catch (IOException e) {
//                log.info("dicom图片保存失败");
//            }
//            try {
//                convertDicomToJpeg(dcmFile, imageFile);
//            } catch (IOException e) {
//                log.info("dicom图片保存失败");
//            }
//            attachmentService.savePicture(attachment);
//            dcmFile.delete();
//            log.info("图片接收成功，文件大小：{}、用时：{}S", attachment.getFileSize(), DateUtil.between(startTime, new Date(), DateUnit.SECOND));
//        } else {

//        attachmentService.uploadFile(file, attachment, baseDir, extName, "patientcode/" + file.getName(), false, true);

        executorService.submit(() -> {
            try {
                // 确保目录存在
                File targetFile = dFile;
                File parentDir = targetFile.getParentFile();
                if (!parentDir.exists()) {
                    boolean created = parentDir.mkdirs();
                    if (!created) {
                        log.error("目标目录创建失败：{}", parentDir.getAbsolutePath());
                        return;
                    }
                }

                // 拷贝dicom文件
                Files.copy(dcmFile, targetFile);
                log.info("DICOM文件已保存到：{}", targetFile.getAbsolutePath());

                // 转换成JPEG
                convertDicomToJpeg(dcmFile, imageFile);
                log.info("DICOM已转换为JPEG：{}", imageFile.getAbsolutePath());

                // 保存图片信息
                attachmentService.savePicture(attachment);

                // 删除原始dicom文件
                if (dcmFile.exists()) {
                    boolean deleted = dcmFile.delete();
                    if (!deleted) {
                        log.warn("临时DICOM文件删除失败：{}", dcmFile.getAbsolutePath());
                    }
                }

                // 清理Redis记录
                RedisUtil.del(CacheConstants.FAILED_DICOM_FILE_KEY + attachment.getPatientcode() + enName + jklx);

            } catch (IOException e) {
                log.error("DICOM处理异常，文件：{}，错误信息：{}", dcmFile.getAbsolutePath(), e.getMessage(), e);
            } catch (Exception e) {
                log.error("未知异常：{}", e.getMessage(), e);
            }
//            try {
//                Files.copy(dcmFile, dFile);
//            } catch (IOException e) {
//                log.error("dicom图片保存失败:{}", JSONUtil.toJsonStr(e));
//            }
//            try {
//                convertDicomToJpeg(dcmFile, imageFile);
//                attachmentService.savePicture(attachment);
//                dcmFile.delete();
//
//                //上传成功时，如果之前有上传失败的记录，全部修改成已处理
//                RedisUtil.del(CacheConstants.FAILED_DICOM_FILE_KEY + attachment.getPatientcode() + enName + jklx);
//            } catch (IOException e) {
//                log.error("dicom图片保存失败:{}", JSONUtil.toJsonStr(e));
//            }
//            log.info("图片接收成功，文件大小：{}、用时：{}S", attachment.getFileSize(), DateUtil.between(startTime, new Date(), DateUnit.SECOND));
        });
//        }

        //log.info("图片地址：{}、{}", attachment.getDcmPath(), attachment.getFilePath());

//        Date endTime = new Date();
//        log.info("{}传图用时：{}S", fileName, DateUtil.between(startTime, endTime, DateUnit.SECOND));
        return attachment;
    }

    //redis报错不会回滚
    private void saveFailedDicomFileToRedis(FailedDicomFile failedDicomFile) {
        RedisUtil.set(
                CacheConstants.FAILED_DICOM_FILE_KEY + failedDicomFile.getPatientcode() + failedDicomFile.getBodyPart() + failedDicomFile.getJklx()
                , failedDicomFile
                , CacheConstants.FAILED_DICOM_FILE_TIME);
    }


    /**
     * Dicom转Jpeg
     *
     * @param dicomFile
     * @param jpegFile
     * @throws IOException
     */
    public void convertDicomToJpeg(File dicomFile, File jpegFile) throws IOException {
        // 创建ImageInputStream
//        File dicomFile = new File(dicomFilePath);
        ImageInputStream iis = ImageIO.createImageInputStream(dicomFile);

        // 创建DicomImageReader
        ImageReader reader = new DicomImageReader(new DicomImageReaderSpi());
        reader.setInput(iis);

        // 读取DICOM图像
        BufferedImage dicomImage = reader.read(0, new DicomImageReadParam());

        // 将图像保存为JPEG文件
//        File jpegFile = new File(jpegFilePath);
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
    public DicomInfoVo readInfo(File dcmFile) throws IOException {

        //dicom图片信息
        DicomInfoVo dicomInfoVo = new DicomInfoVo();

        try (
                DicomInputStream dicomInputStream = new DicomInputStream(dcmFile);
        ) {

            Attributes attrs = dicomInputStream.readDataset();

//            //窗位
//            double windowCenter = attrs.getDouble(Tag.WindowCenter, 0.0);
//            dicomInfoVo.setWindowCenter(windowCenter + "");
//            //窗位
//            double windowWidth = attrs.getDouble(Tag.WindowWidth, 0.0);
//            dicomInfoVo.setWindowWidth(windowWidth + "");
//            log.info("窗位:{}、窗位：{}", windowCenter, windowWidth);

            dicomInfoVo.setSOPInstanceUID(attrs.getString(Tag.SOPInstanceUID));
            dicomInfoVo.setPatientcode(attrs.getString(Tag.PatientID));

            // 获取 DICOM 指定的字符集
            String specificCharacterSet = attrs.getString(Tag.SpecificCharacterSet);
//            log.info("打印一下字符集：{}",specificCharacterSet);
            Charset charset = StandardCharsets.UTF_8; // 默认 UTF-8
            if (StringUtils.isEmpty(specificCharacterSet)){
                //霸州字符集是空的，默认使用GB18030
                charset = Charset.forName("GB18030");
            } else if (!"GB18030".equalsIgnoreCase(specificCharacterSet)
                    && !"GBK".equalsIgnoreCase(specificCharacterSet)
                    && !"ISO_IR 192".equalsIgnoreCase(specificCharacterSet)) {
                if ("ISO_IR 100".equalsIgnoreCase(specificCharacterSet)) {
                    charset = StandardCharsets.ISO_8859_1;
                }
            } else {
                charset = Charset.forName("GB18030");
            }

            byte[] bytes = attrs.getBytes(1572885);
            if (bytes != null) {
//                log.info("打印一下EnName的bytes：{}",bytes);
                String decodedString = new String(bytes, charset);
                dicomInfoVo.setBodyPart(decodedString);
                dicomInfoVo.setEnName(decodedString);
//                log.info("部位名称BodyPartExamined: {}", decodedString);
            }else {
                dicomInfoVo.setEnName(attrs.getString(1577008));
            }

            dicomInfoVo.setProtocolName(attrs.getString(1577008));
//            log.info("检查协议名称ProtocolName: {}", dicomInfoVo.getProtocolName());

//            log.info("部位名称1：{}", attrs.getString(Tag.ProtocolName));
//            log.info("部位名称2：{}", attrs.getString(Tag.BodyPartExamined));
//            if (StringUtils.isBlank(attrs.getString(Tag.BodyPartExamined))) {
////                log.info("部位名称1：{}", attrs.getString(Tag.ProtocolName));
//                dicomInfoVo.setEnName(attrs.getString(Tag.ProtocolName));
//            } else {
//                byte[] bytes = attrs.getBytes(Tag.BodyPartExamined);
//                // 使用默认编码获取字符串
//                String defaultEncodedString = new String(bytes);
//                // 将默认编码的字符串转换为UTF-8编码
//                String utf8String = new String(defaultEncodedString.getBytes(StandardCharsets.UTF_8), charset);
//                log.info("部位名称21：{}", utf8String);
//                dicomInfoVo.setEnName(utf8String);
//            }

            String modality = attrs.getString(Tag.Modality);
            if (jklxs.containsKey(modality)) {
                modality = jklxs.get(modality);
            }
            dicomInfoVo.setJklxs(modality);

            String studyDate = DateUtil.format(DateUtil.parse(attrs.getString(Tag.ContentDate), "yyyyMMdd"), "yyyy-MM-dd");
            dicomInfoVo.setContentDate(studyDate);

//            log.error("Tag.StudyTime：{}", studyTime);
            if (StringUtils.isNotBlank(attrs.getString(Tag.StudyTime))) {
                long studyTime = (long) (Double.parseDouble(attrs.getString(Tag.StudyTime)) * 1000);
                dicomInfoVo.setContentTime(DateUtil.format(new Date(studyTime), "HH:mm:ss"));
            }
            // dicomInfoVo.setModality(attrs.getString(Tag.Modality));
            // dicomInfoVo.setManufacturer(attrs.getString(Tag.Manufacturer));
            dicomInfoVo.setInstitutionName(attrs.getString(Tag.InstitutionName));
            // dicomInfoVo.setSeriesDesc(attrs.getString(Tag.SeriesDescription));
            // dicomInfoVo.setInstitutionalDeptName(attrs.getString(Tag.InstitutionalDepartmentName));
            dicomInfoVo.setPatientName(attrs.getString(Tag.PatientName));
            dicomInfoVo.setPatientcode(attrs.getString(Tag.PatientID));
            dicomInfoVo.setPatientSex(attrs.getString(Tag.PatientSex));
            dicomInfoVo.setBodyPart(attrs.getString(Tag.BodyPartExamined));
            dicomInfoVo.setExposureTime(attrs.getString(Tag.ExposureTime) + "ms");
            // dicomInfoVo.setExposure(attrs.getString(Tag.Exposure));
            // dicomInfoVo.setExposureIndex(attrs.getString(Tag.ExposureIndex));
            dicomInfoVo.setWindowCenter(attrs.getString(Tag.WindowCenter));
            dicomInfoVo.setWindowWidth(attrs.getString(Tag.WindowWidth));
            dicomInfoVo.setMag(attrs.getString(Tag.MagneticFieldStrength));
            dicomInfoVo.setKv(attrs.getString(Tag.KVP));
            dicomInfoVo.setPatienOrientation(attrs.getString(Tag.PatientOrientation));
            dicomInfoVo.setViewPosition(attrs.getString(Tag.ViewPosition));
            dicomInfoVo.setMa(attrs.getString(Tag.XRayTubeCurrent));
//            dicomInfoVo.setMa(attrs.getString(Tag.XRayTubeCurrent));
            dicomInfoVo.setAge(attrs.getString(Tag.PatientAge));
//            log.info("dicomInfoVo:{}", dicomInfoVo);

            // // 内容日期
            // String contentDate = attrs.getString(524323);
            // // 内容时间
            // String contentTime = attrs.getString(524339);
            // // 研究日期
            // String studyDate = attrs.getString(524320);
            // // 研究时间
            // String studyTime = attrs.getString(524336);
            // // 系列日期
            // String seriesDate = attrs.getString(524321);
            // // 系列时间
            // String seriesTime = attrs.getString(524337);
            // // 获取日期
            // String acqDate = attrs.getString(524322);
            // // 获取时间
            // String acqTime = attrs.getString(524338);
            // log.info(dcmFilePath + "_时间信息，内容：{}、研究{}、系列：{}、获取：{}", contentDate+" "+contentTime, studyDate+" "+studyTime, seriesDate+" "+seriesTime, acqDate+" "+acqTime);
            // 实例号
            // String intanceNum = attrs.getString(2097171);

            dicomInputStream.close();
            return dicomInfoVo;
        }
    }





    /**
     * 将接收的dicom文件保存图片存储
     */
    //会回滚
    @Override
    public void uploadDicomFileTask() throws ExecutionException, InterruptedException {
        RedisSetUtil.addToSortedSet(CacheConstants.DICOM_FILE_SET_KEY, "images/image/01/FSKCT/20240105/010230016113/1704417564035_1.2.840.113704.7.1.1.9836.1704417208.1.jpg", DateUtil.currentSeconds());
        RedisSetUtil.addToSortedSet(CacheConstants.DICOM_FILE_SET_KEY, "images/image/01/FSKCT/20240105/010230016113/1704417565556_1.2.840.113704.1.111.5624.1704417211.3451.jpg", DateUtil.currentSeconds());
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<String> callable = new Callable<String>() {
            public String call() throws Exception {
                log.info("开始异步");
                // 创建异步执行任务:
                ExecutorService threadPool = Executors.newFixedThreadPool(5);
                String root = System.getProperty("user.dir") + "/images/";
                while (true) {
                    Set<Object> set = RedisSetUtil.queryByRange(CacheConstants.DICOM_FILE_SET_KEY, 0L, 9L);
                    RedisSetUtil.removeRange(CacheConstants.DICOM_FILE_SET_KEY, 0L, 9L);
                    if (set.size() > 0) {
                        //执行上传操作
                        int j = 0;
                        for (Object item : set) {
//                            log.info("执行pacs传图：{}", j++);
                            threadPool.submit(() -> {
                                Date start = new Date();
                                String filePath = Constants.RESOURCE_PREFIX + "/" + (String) item;
                                String localPath = root + StrUtil.subAfter(filePath, "/", true);
                                log.info("上传图片线程开始{}、{}", localPath, filePath);
                                try {
                                    FileInputStream fileInputStream = new FileInputStream(localPath);//线下系统将文件储存至指定的硬盘中位置中
                                    FtpUtil.upload(StrUtil.subBefore(filePath, "/", true), StrUtil.subAfter(filePath, "/", true), fileInputStream, false);
                                } catch (FileNotFoundException e) {
                                    log.error("图片不存在！", filePath);
                                }
                                log.info("上传图片线程结束{},耗时:{}", filePath, DateUtil.between(start, new Date(), DateUnit.MS));
                            });
                        }
                    }
                }

            }
        };
        Future<String> future = executor.submit(callable);
        log.info("返回结果");
    }


    @Override
    @Transactional
    public void testPrivateRollback() {
        FailedDicomFile failedDicomFile = new FailedDicomFile();
        failedDicomFile.setBodyPart("测试");
        failedDicomFile.setCreateTime(new Date());
        failedDicomFile.setFileName("测试");
        failedDicomFile.setIsProcessed(false);
        failedDicomFile.setJklx("9");
        failedDicomFile.setPatientcode("测试");
        failedDicomFile.setUpdateTime(new Date());
        //新建一个线程事务仍然会生效
//        new Thread(() -> saveFailedDicomFile(failedDicomFile)).run();
        saveFailedDicomFileToRedis(failedDicomFile);
        int err = 1 / 0;
    }


    /**
     * 分类dicom文件
     * @param folderPath
     * @return
     */
    @Override
    public Boolean classification(String folderPath) throws IOException {
        DicomConfig dicomConfig = iSysConfigService.getSysConfigObject(Constants.DICOM_CONFIG, DicomConfig.class);
        jklxs = dicomConfig.getJklxs();
        String fileRoot = "E:\\prod";
//        String folderPath = "/path/to/your/folder";
        // 获取文件夹中的所有文件
        File folders = new File(folderPath);
        File[] files = folders.listFiles();
        if (files != null) {
            for (File dcmFile : files) {
                // 判断文件是否存在并且是文件而非目录
                if (dcmFile.isFile()) {
                    log.info("开始处理文件,文件名：{}",dcmFile.getName());
                    DicomInfoVo dicomInfoVo = new DicomInfoVo();
                    try {
                        dicomInfoVo = readInfo(dcmFile);
                    }catch (Exception e){
                        log.info("读取文件失败,文件名：{}",dcmFile.getName());
                        continue;
                    }
                    log.info("dicomInfoVo:{}", dicomInfoVo);
                    if (!"CT".equals(dicomInfoVo.getJklxs())){
                        log.info("文件名：{}不是CT直接跳过",dcmFile.getName());
                        continue;
                    }
                    //查询附件表里的信息
                    List<Attachment> attachments = attachmentService.list(new LambdaQueryWrapper<Attachment>()
                            .eq(Attachment::getDepId, "173")
                            .eq(Attachment::getMemo, dicomInfoVo.getSOPInstanceUID() + ".jpg")
                    );
                    if (CollectionUtil.isEmpty(attachments)){
                        log.info("文件名：{}没有找到附件跳过",dcmFile.getName());
                        continue;
                    }
                    Attachment attachment = attachments.get(0);
                    String folderPath1 = StrUtil.subBefore(fileRoot + attachment.getFilePath(), "/", true);
                    if (!FileUtil.isDirectory(folderPath1)) {
                        File folderpath = new File(folderPath1);
                        folderpath.mkdirs();
                    }

                    String[] parts = attachment.getDcmPath().split(",", 2);
                    String dcmFilePath = parts[0];
                    log.info("dcmFilePath:{}", dcmFilePath);
                    File dFile = new File(fileRoot + dcmFilePath);

                    //保存Jpeg图片
                    String imageFilePath = attachment.getFilePath();
                    String folderPath2 = StrUtil.subBefore(fileRoot + imageFilePath, "/", true);
                    if (!FileUtil.isDirectory(folderPath2)) {
                        File folder = new File(folderPath2);
                        folder.mkdirs();
                    }
                    File imageFile = new File(fileRoot + imageFilePath);
                    executorService.submit(() -> {
                        try {
                            // 确保目录存在
                            File targetFile = dFile;
                            File parentDir = targetFile.getParentFile();
                            if (!parentDir.exists()) {
                                boolean created = parentDir.mkdirs();
                                if (!created) {
                                    log.error("目标目录创建失败：{}", parentDir.getAbsolutePath());
                                    return;
                                }
                            }

                            // 拷贝dicom文件
                            Files.copy(dcmFile, targetFile);
                            log.info("DICOM文件已保存到：{}", targetFile.getAbsolutePath());

                            // 转换成JPEG
                            convertDicomToJpeg(dcmFile, imageFile);
                            log.info("DICOM已转换为JPEG：{}", imageFile.getAbsolutePath());
                        } catch (IOException e) {
                            log.error("DICOM处理异常，文件：{}，错误信息：{}", dcmFile.getAbsolutePath(), e.getMessage(), e);
                        } catch (Exception e) {
                            log.error("未知异常：{}", e.getMessage(), e);
                        }
                    });

                }
            }
        } else {
            System.out.println("文件夹为空或路径无效");
        }
        return Boolean.TRUE;
    }


    /**
     * 读取dicom文件的信息
     * @param folderPath
     * @return
     */
    @Override
    public DicomInfoVo readInfo(String folderPath) throws IOException {
        DicomConfig dicomConfig = iSysConfigService.getSysConfigObject(Constants.DICOM_CONFIG, DicomConfig.class);
        jklxs = dicomConfig.getJklxs();
        File file = new File(folderPath);
        DicomInfoVo dicomInfoVo = readInfo(file);

        log.info("dicomInfoVo:{}", dicomInfoVo);
        return dicomInfoVo;
    }
}
