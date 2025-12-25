package com.center.medical.report.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.PacsResult;
import com.center.medical.common.config.YiyingConfig;
import com.center.medical.common.core.domain.entity.SysDept;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.QrCodeUtils;
import com.center.medical.dao.PacsResultMapper;
import com.center.medical.report.dao.PacsResultThirdMapper;
import com.center.medical.report.bean.model.PacsResultThird;
import com.center.medical.report.service.PacsResultThirdService;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 易影报告回传(PacsResultThird)服务实现类
 *
 * @author makejava
 * @since 2025-08-14 10:15:33
 */
@Slf4j
@Service("pacsResultThirdService")
@RequiredArgsConstructor
public class PacsResultThirdServiceImpl extends ServiceImpl<PacsResultThirdMapper, PacsResultThird> implements PacsResultThirdService {

    private final PacsResultMapper pacsResultMapper;

    /**
     * 获取deps中最后一个有易影结果的科室编号
     * @param patientcode
     * @param deps
     * @return
     */
    @Override
    public String getLastYiyingDeptNo(String patientcode, List<SysDept> deps){
        String lastYiyingDeptNo=null;

        //所有易影结果
        List<PacsResult> pacsResults = pacsResultMapper.selectList(
                new LambdaQueryWrapper<PacsResult>()
                    .eq(PacsResult::getPatientcode,patientcode)
                    .eq(PacsResult::getIsNewPacs,2)
        );
        if(pacsResults.size()==0)return lastYiyingDeptNo;
        //所有有易影结果的科室编号
        Set<String> allYiyingDeptIds=new HashSet<>();
        for(PacsResult pacsResult:pacsResults){
            allYiyingDeptIds.add(pacsResult.getDepId());
        }
        //从后向前遍历deps，取遇到的第一个有易影结果的科室编号。
        for(int i=deps.size()-1;i>=0;i--){
            String deptNo=deps.get(i).getDeptNo();
            if(allYiyingDeptIds.contains(deptNo)){
                lastYiyingDeptNo=deptNo;
                break;
            }
        }

        return lastYiyingDeptNo;
    }

    /**
     * 生成易影云胶片二维码base64
     * @param patientcode
     * @param patientcode8
     * @return
     */
    @Override
    public List<String> createQrCodeBase64(String patientcode,String patientcode8, YiyingConfig yiyingConfig){
        //一个体检号可能有多个二维码
        List<String> qrCodeContents = baseMapper.getQrCodeContentByPatientcode(patientcode,patientcode8);
        List<String> base64s=new ArrayList<>();

        //二维码图片和说明文字分开，可能被分到不同的两页
//        QrConfig qrConfig = new QrConfig();
//        if(yiyingConfig.getQrcodeHeight()!=null)qrConfig.setHeight(yiyingConfig.getQrcodeHeight());
//        if(yiyingConfig.getQrcodeWidth()!=null)qrConfig.setWidth(yiyingConfig.getQrcodeWidth());
//        if(yiyingConfig.getQrcodeMargin()!=null)qrConfig.setMargin(yiyingConfig.getQrcodeMargin());
//        String qrCodeBase64 = QrCodeUtil.generateAsBase64(qrCodeContent,qrConfig, "png");
//        return qrCodeBase64.substring(qrCodeBase64.indexOf("base64,") + "base64,".length());

        for(String qrCodeContent : qrCodeContents){
            String base64=null;
            try{
                base64=QrCodeUtils.generateQRCodeBase64(qrCodeContent
                        ,yiyingConfig.getQrcodeRemark()
                        ,null
                        ,yiyingConfig.getQrcodeWidth()
                        ,yiyingConfig.getQrcodeHeight()
                        ,yiyingConfig.getQrcodeMargin()
                        ,yiyingConfig.getQrcodeFont()
                        ,yiyingConfig.getQrcodeFontSize()
                );
            }catch(Exception e){
                String errorMsg="云胶片二维码生成失败";
                log.error(errorMsg,e);
                throw new ServiceException(errorMsg);
            }
            base64s.add(base64);
        }
        return base64s;
    }
}
