package com.center.medical.center.common.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.center.common.bean.dto.PickupBrDto;
import com.center.medical.center.common.bean.model.IntPatient;
import com.center.medical.center.common.bean.model.IntPatientFeeItem;
import com.center.medical.center.common.bean.model.PacsResultMiddel;
import com.center.medical.center.common.constant.CenterConstant;
import com.center.medical.center.common.dao.IntPatientFeeItemMapper;
import com.center.medical.center.common.dao.IntPatientMapper;
import com.center.medical.center.common.dao.PacsResultZhongKangMapper;
import com.center.medical.center.common.service.*;
import com.center.medical.common.annotation.DataSource;
import com.center.medical.common.config.LisConfig;
import com.center.medical.common.config.PacsConfig;
import com.center.medical.common.enums.DataSourceType;
import com.center.medical.common.enums.LisType;
import com.center.medical.common.enums.PacsType;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.dao.PeispatientMapper;
import com.center.medical.pacslis.bean.dto.LisDto;
import com.center.medical.pacslis.bean.dto.MiddleDbDto;
import com.center.medical.pacslis.bean.dto.PacsDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author xhp
 * @since 2023-03-02 9:51
 */
@Slf4j
@Service("lisPacsService")
@RequiredArgsConstructor
public class LisPacsServiceImpl extends ServiceImpl<PeispatientMapper, Peispatient> implements LisPacsService {
    @Value("${spring.profiles.active}")
    private String active;
    @Qualifier("lisHqService")
    private final LisService lisHqService;
    @Qualifier("lisRmService")
    private final LisService lisRmService;
    @Qualifier("lisJinweiService")
    private final LisService lisJinweiService;
    @Qualifier("lisZhongHeService")
    private final LisService lisZhongHeService;
    private final AdiconService adiconService;
    private final PacsJinweiService pacsJinweiService;
    private final PacsYiyingService pacsYiyingService;
    private final IntPatientMapper intPatientMapper;
    private final IntPatientFeeItemMapper intPatientFeeItemMapper;
    private final PacsResultZhongKangMapper pacsResultZhongKangMapper;

    /**
     * 插入中间库
     * @param middleDbDto
     */
    @Override
    public void save(MiddleDbDto middleDbDto){
        LisConfig lisConfig=middleDbDto.getLisConfig();
        if(lisConfig==null){
            log.info("没有配置LIS_CONFIG");
            return;
        }
        //是否特殊处理
        Boolean isSpecial=lisConfig.getIsSpecial();
        if(isSpecial!=null&&isSpecial){
            List<String> actives=Arrays.asList(active.split(","));
            if(actives.contains(CenterConstant.JIAONAN)){
                //老系统可能仍在运行，标软同步工具仍在使用，先将东区数据删除
                lisHqService.delete(middleDbDto);
                //插入虹桥lis中间库,虹桥瑞美一样
                lisRmService.save(middleDbDto);
            }
        }else{
            List<String> lisTypes=lisConfig.getLisTypes();
            if(lisTypes==null||lisTypes.size()==0) {
                log.info("没有配置LIS_CONFIG.lisTypes");
                return;
            }
            for(String lisType:lisTypes){
                String lisTypeName=lisType.toUpperCase();
                if(LisType.HONGQIAO.name().equals(lisTypeName)){
                    lisHqService.save(middleDbDto);
                }else if(LisType.RUIMEI.name().equals(lisTypeName)){
                    lisRmService.save(middleDbDto);
                }
            }
        }

//        List<String> actives=Arrays.asList(active.split(","));
//        //虹桥和瑞美的中间库是一样的
//        if(
//                actives.contains(CenterConstant.DONGQU)
//        ){
//            //插入虹桥lis中间库,虹桥瑞美一样
//            lisHqService.save(middleDbDto);
//        }else if(
//                actives.contains(CenterConstant.JINDU)
//        ){
//            //插入虹桥lis中间库,虹桥瑞美一样
//            lisRmService.save(middleDbDto);
//        }else if(
//                actives.contains(CenterConstant.JIAONAN)
//        ){
//            //老系统可能仍在运行，标软同步工具可能仍在使用，先将东区数据删除
//            lisHqService.delete(middleDbDto);
//            //插入虹桥lis中间库,虹桥瑞美一样
//            lisRmService.save(middleDbDto);
//        }
    }

    /**
     * 查询lis结果
     * @param patientcode
     * @return
     */
    @Override
    public List<LisDto> selectList(String patientcode,String loginid,String password,String lisConfigStr){
        LisConfig lisConfig= JSONUtil.toBean(lisConfigStr,LisConfig.class);
        List<String> lisTypes=lisConfig.getLisTypes();
        if(lisTypes==null||lisTypes.size()==0) {
            throw new ServiceException("没有配置LIS_CONFIG.lisTypes");
        }
        List<LisDto> lisDtos=new ArrayList<>();
        for(String lisType:lisTypes){
            String lisTypeName=lisType.toUpperCase();
            List<LisDto> list;
            if(LisType.HONGQIAO.name().equals(lisTypeName)){
                list=lisHqService.selectList(patientcode);
            }else if(LisType.RUIMEI.name().equals(lisTypeName)){
                list=lisRmService.selectList(patientcode);
            }else if(LisType.ADICON.name().equals(lisTypeName)){
                list=adiconService.getAdicon(patientcode,loginid,password);
            }else if(LisType.JINWEI.name().equals(lisTypeName)){
                list=lisJinweiService.selectList(patientcode);
            }else if(LisType.ZHONGHE.name().equals(lisTypeName)){
                list=lisZhongHeService.selectList(patientcode);
            }else{
                log.error("未知lis类型{}",lisTypeName);
                continue;
            }
            lisDtos.addAll(list);
        }
        return lisDtos;

//        List<LisDto> lisDtos;
//        List<String> actives=Arrays.asList(active.split(","));
//        if(actives.contains(CenterConstant.DONGQU)){
//            lisDtos=lisHqService.selectList(patientcode);
//            System.out.println("----查询艾迪康数据中-----");
//            List<LisDto> adicons = adiconService.getAdicon(patientcode,loginid,password);
//            lisDtos.addAll(adicons);
//        }else if(actives.contains(CenterConstant.JINDU)){
//            lisDtos=lisRmService.selectList(patientcode);
//        }else if(actives.contains(CenterConstant.JIAONAN)){
//            lisDtos=lisRmService.selectList(patientcode);
//            lisDtos.addAll(lisHqService.selectList(patientcode));
//            System.out.println("----查询艾迪康数据中-----");
//            List<LisDto> adicons = adiconService.getAdicon(patientcode,loginid,password);
//            lisDtos.addAll(adicons);
//        }else{
//            lisDtos=new ArrayList<>();
//        }
//        return lisDtos;
    }

    /**
     * 查询pacs结果
     */
    @Override
    public List<PacsDto> selectPacsList(String patientcode, String pacsConfigStr){
        PacsConfig pacsConfig=JSONUtil.toBean(pacsConfigStr,PacsConfig.class);
        List<String> pacsTypes=pacsConfig.getPacsTypes();
        if(pacsTypes==null||pacsTypes.size()==0){
            throw new ServiceException("没有配置PACS_CONFIG.pacsTypes");
        }
        List<PacsDto> pacsDtos=new ArrayList<>();
        for(String pacsType:pacsTypes){
            String pacsTypeName=pacsType.toUpperCase();
            List<PacsDto> list;
            if(PacsType.JINWEI.name().equals(pacsTypeName)){
                list=pacsJinweiService.selectList(patientcode);
            }else if(PacsType.YIYING.name().equals(pacsTypeName)){
                list=pacsYiyingService.selectList(patientcode);
            }else{
                log.info("未知pacss类型{}",pacsTypeName);
                continue;
            }
            pacsDtos.addAll(list);
        }
        return pacsDtos;
    }


    /**
     * 抓取中间库职业的体检号
     * @return
     */
    @Override
    @DataSource(DataSourceType.RUIMEISLAVE)
    public List<PickupBrDto> pickupBr() {
        log.info("开始抓取中间库职业的体检号");
        List<PickupBrDto> pickupBrDtoList = new ArrayList<>();
        DateTime dateTime = DateUtil.beginOfDay(new Date());
        List<IntPatient> list = intPatientMapper.selectList(
                new QueryWrapper<IntPatient>()
                        .isNull("F_NEWPACS")
                        .eq("F_Transfered",false)
                        .eq("F_Registered",true)
                        .ge("DateRegister",dateTime)
        );
        for (IntPatient intPatient : list) {
            List<IntPatientFeeItem> intPatientFeeItems = intPatientFeeItemMapper.selectList(new QueryWrapper<IntPatientFeeItem>()
                    .eq("PatientCode", intPatient.getPatientCode()));
            pickupBrDtoList.add(new PickupBrDto(intPatient,intPatientFeeItems));
        }
        return pickupBrDtoList;
    }


    /**
     * 设置已核收
     * @param patientcodes
     * @return
     */
    @Override
    @DataSource(DataSourceType.RUIMEISLAVE)
    public Boolean setFTransfered(List<String> patientcodes) {
        log.error("开始设置已核收，参数：{}",patientcodes);
        IntPatient intPatient = new IntPatient();
        intPatient.setFTransfered(true);
        intPatientMapper.update(intPatient,new LambdaUpdateWrapper<IntPatient>().in(IntPatient::getPatientCode,patientcodes));
        return Boolean.TRUE;
    }


    /**
     * 保存pacs结果
     * @param pacsResult
     * @return
     */
    @Override
    @DataSource(DataSourceType.RUIMEISLAVE)
    public Boolean saveResult(List<PacsResultMiddel> pacsResult) {
        log.error("开始保存pacs结果，参数：{}",pacsResult);
        for (PacsResultMiddel pacsResultMiddel : pacsResult) {
            PacsResultMiddel oldPacsResultMiddel = pacsResultZhongKangMapper.selectOne(new LambdaQueryWrapper<PacsResultMiddel>()
                    .eq(PacsResultMiddel::getPatientCode,pacsResultMiddel.getPatientCode())
                    .eq(PacsResultMiddel::getExamFeeItemCode,pacsResultMiddel.getExamFeeItemCode()));
            if (ObjectUtils.isEmpty(oldPacsResultMiddel)){
                pacsResultZhongKangMapper.insert(pacsResultMiddel);
            }else {
                pacsResultMiddel.setId(oldPacsResultMiddel.getId());
                pacsResultZhongKangMapper.updateById(pacsResultMiddel);
            }
        }
        return Boolean.TRUE;
    }
}
