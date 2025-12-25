package com.center.medical.reservation.task;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.bean.model.Report;
import com.center.medical.common.config.Domain;
import com.center.medical.common.config.PingAnConfig;
import com.center.medical.common.config.ZhongkangConfig;
import com.center.medical.common.constant.Constants;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.MD5;
import com.center.medical.common.utils.ZipUtils;
import com.center.medical.report.service.ReportService;
import com.center.medical.reservation.bean.dto.GetPingAnCodeDto;
import com.center.medical.reservation.service.PingAnService;
import com.center.medical.service.PeispatientService;
import com.center.medical.system.service.ISysConfigService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author: ay
 * @date: 2024-06-29 10:05
 * @description: 平安好医生 医疗机构上传单个 PDF 影像
 */
@Slf4j
@Component("pingAnTask")
public class PingAnTask {

    @Resource
    private PingAnService pingAnService;
    @Resource
    private PeispatientService peispatientService;
    @Resource
    private ReportService reportService;
    @Resource
    private ISysConfigService iSysConfigService;

    /**
     * 平安好医生 医疗机构上传单个 PDF 影像
     *
     * @return
     */
    public void start() {
        log.info("-----开始执行：平安好医生 医疗机构上传单个 PDF 影像");
        //获取平安好医生需要上传pdf的体检号
        List<GetPingAnCodeDto> dtos = pingAnService.getPingAnCode();
        for (GetPingAnCodeDto dto : dtos) {
            try {
                uploadPdf(dto.getId());
                log.info("上传平安PDF成功:{}",dto.getPatientcode());
            }catch (Exception e) {
                e.printStackTrace();
                log.info("上传平安PDF失败:{}",dto.getPatientcode());
            }
        }

        log.info("-----平安好医生上传单个PDF影像结束");
    }

    /**
     * 上传平安PDF
     * @param id
     */
    private void uploadPdf(String id) throws IOException {
        Peispatient patient = peispatientService.getInfoById(id);
        Report report = reportService.getOne(new LambdaQueryWrapper<Report>()
                .eq(Report::getPatientcode,patient.getPatientcode())
                .eq(Report::getDiseaseHealth,0)
        );
        String pdfUrl = report.getUrlPdf();
        Domain domain = iSysConfigService.getDomain();
        String prefix = ZhongkangConfig.isOnline() ? domain.getRsPfDomain() : domain.getRsLcDomain();
        String filePath = prefix + pdfUrl;
        File target = new File(filePath);
        Map<String,String> params = new HashMap<String, String>();
        //获取平安配置
        PingAnConfig pingAnConfig = iSysConfigService.getSysConfigObject(Constants.PINGAN_CONFIG, PingAnConfig.class);
        String timestamp=new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        params.put("fileName",target.getName() );
        params.put("pajkKey",pingAnConfig.getPajkKey());
        params.put("sign", MD5.encode(pingAnConfig.getPajkKey() + pingAnConfig.getPajkPwd() + timestamp));
        params.put("timestamp", timestamp);
        params.put("hospitalOrderId",patient.getPatientbizno() );
        params.put("customerName",patient.getPatientname() );
        params.put("customerIdentityType"
                ,patient.getCountreportoccupationxml()==null?null:patient.getCountreportoccupationxml().toString() );
        params.put("customerIdentityNo",patient.getIdcardno() );
        params.put("customerGender",0 == patient.getIdSex()?"M":1 == patient.getIdSex()?"F":"" );
        params.put("customerBirthday",patient.getBirthdate()==null?"":new SimpleDateFormat("yyyyMMdd").format(patient.getBirthdate()) );
        params.put("captcha", patient.getInstancetag());


        URL url = new URL(filePath);
        URLConnection connection = url.openConnection();
        InputStream fileInputStream = connection.getInputStream();
        Map<String, String> map = doPost(pingAnConfig.getPdfUrl(), params, "file", fileInputStream, new File(filePath).getName());
        log.info("平安上传响应信息:{}",map.get("msg"));

        if("success".equals(map.get("status"))){
            String json = map.get("msg");
            Map<String,Object> m = JSONUtil.toBean(json, Map.class);
            if("200".equals(m.get("status"))
                    ||"当前报告已回传不能重复回传".equals(m.get("resultMsg"))){
                patient.setFWordprinted(1);
                peispatientService.updateById(patient);
            }else{
                report.setIsNuclein(ObjectUtils.isEmpty(report.getIsNuclein()) ? 1 : report.getIsNuclein() + 1);
                reportService.updateById(report);
                throw new ServiceException("平安报告上传失败！");
            }
        }else{
            report.setIsNuclein(ObjectUtils.isEmpty(report.getIsNuclein()) ? 1 : report.getIsNuclein() + 1);
            reportService.updateById(report);
            throw new ServiceException(map.get("msg"));
        }

    }




    public static Map<String,String> doPost(String url,Map<String,String> params,String fileKey,InputStream fileInputStream, String fileName){
        Map<String, String> map = new HashMap<>();
        CloseableHttpClient httpclient = null;
        CloseableHttpResponse response = null;
        try {
            httpclient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(url);
            MultipartEntityBuilder entity = MultipartEntityBuilder.create();
            for (String key : params.keySet()) {
                String value = params.get(key);
                if (value != null) {
                    entity.addPart(key, new StringBody(value, ContentType.TEXT_PLAIN.withCharset(ZipUtils.CHARSET)));
                }
            }
            entity.addPart(fileKey, new InputStreamBody(fileInputStream, ContentType.APPLICATION_OCTET_STREAM, fileName));
            HttpEntity e = entity.build();
            httpPost.setEntity(e);
            response = httpclient.execute(httpPost);
            HttpEntity resEntity = response.getEntity();
            if (resEntity != null) {
                map.put("status", "success");
                map.put("msg", EntityUtils.toString(resEntity, ZipUtils.CHARSET));
            } else {
                map.put("status", "error");
                map.put("msg", "连接失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", "error");
            map.put("msg", e.getMessage());
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                if (httpclient != null) {
                    httpclient.close();
                }
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return map;
    }
}
