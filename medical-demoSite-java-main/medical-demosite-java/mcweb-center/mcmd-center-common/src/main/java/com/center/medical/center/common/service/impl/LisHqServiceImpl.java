package com.center.medical.center.common.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.Peispatientexamitem;
import com.center.medical.center.common.bean.dto.LisHqDto;
import com.center.medical.center.common.bean.model.*;
import com.center.medical.center.common.dao.*;
import com.center.medical.center.common.service.LisService;
import com.center.medical.common.annotation.DataSource;
import com.center.medical.common.enums.DataSourceType;
import com.center.medical.common.utils.CodeUtil;
import com.center.medical.pacslis.bean.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.Charset;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 虹桥lis(开发区)
 */
@Slf4j
@Service("lisHqService")
@RequiredArgsConstructor
public class LisHqServiceImpl extends ServiceImpl<LisHqMapper, Peispatientexamitem> implements LisService {
    private final IntPatientExamItemMapper intPatientExamItemMapper;
    private final IntPatientFeeItemMapper intPatientFeeItemMapper;
    private final IntPatientTransFeeItemMapper intPatientTransFeeItemMapper;
    private final IntPatientTransTargetMapper intPatientTransTargetMapper;
    private final IntPatientMapper intPatientMapper;

    /**
     * 虹桥和瑞美一样
     * 插入中间库
     * @param middleDbDto
     */
    @Override
    @Transactional
    @DataSource(DataSourceType.HONGQIAOSLAVE)
    public void save(MiddleDbDto middleDbDto){
        saveCommon(middleDbDto,true);
    }

    /**
     * 中间库删除
     * 一般情况，只有一个中间库slave
     * 但是西区自己有一个中间库，同时还要用东区的中间库。因为西区的项目，一部分西区做，一部分东区做。
     * 老系统使用一个标软开发的程序，将西区中间库的数据同步到东区中间库。但这个软件只同步新增，不同步删除。如果同一个体检号插入中间库多次，会出现重复项目。
     * 新系统不使用这个程序，直接连接两个中间库。
     * 由于老系统依赖于同步程序，老系统下线前，新系统只对东区中间库做删除操作，老系统下线后，新系统再放开对东区中间库的插入操作。
     * @param middleDbDto
     */
    @Override
    @Transactional
    @DataSource(DataSourceType.HONGQIAOSLAVE)
    public void delete(MiddleDbDto middleDbDto){
        saveCommon(middleDbDto,false);
    }

    /**
     * 虹桥和瑞美一样
     * 插入中间库
     * @param middleDbDto
     * @param isDoInsert 是否做插入操作
     */
    @Transactional
    public void saveCommon(MiddleDbDto middleDbDto,boolean isDoInsert){
        log.info(JSONUtil.toJsonStr(middleDbDto));
        MiddleDbPatientDto middleDbPatientDto=middleDbDto.getMiddleDbPatientDto();
        String patientcode=middleDbPatientDto.getStrIdpatient();
        String patientcode8=middleDbPatientDto.getPatientCode();

        //删除以前上传的数据
        List<IntPatient> intPatients=intPatientMapper.selectList(
                new QueryWrapper<IntPatient>()
//                        .eq("StrIDPatient",patientcode)
                        .eq("PatientCode",patientcode8)
        );
        if(intPatients.size()>0){
            //idPatient 8位体检号有唯一索引，如果数据库里已经有了，必须删掉才能插入
            intPatientMapper.delete(
                    new QueryWrapper<IntPatient>()
                            .eq("PatientCode",patientcode8)
//                            .eq("StrIDPatient",patientcode)
            );
            intPatientTransTargetMapper.delete(
                    new QueryWrapper<IntPatientTransTarget>()
//                            .eq("StrIDPatient",patientcode)
                            .eq("PatientCode",patientcode8)
            );
            intPatientFeeItemMapper.delete(
                    new QueryWrapper<IntPatientFeeItem>()
//                            .eq("StrIDPatient",patientcode)
                            .eq("PatientCode",patientcode8)
            );
            intPatientTransFeeItemMapper.delete(
                    new QueryWrapper<IntPatientTransFeeItem>()
                            .eq("PatientCode",patientcode8)
            );
            intPatientExamItemMapper.delete(
                    new QueryWrapper<IntPatientExamItem>()
                            .eq("PatientCode",patientcode8)
//                            .eq("StrIDPatient",patientcode)
            );
        }

        //如果老系统仍在运行，标软自动同步工具仍在使用，就只做删除操作，防止同一个体检号出现重复项目
        if(!isDoInsert)return;

        Integer idPatient=middleDbPatientDto.getIdPatient();
        String patientName=middleDbPatientDto.getPatientName();
        Timestamp now=new Timestamp(System.currentTimeMillis());
        int numDay= CodeUtil.getNumDay();
        String serial=CodeUtil.getPatientCodeHiden(numDay);//流水号

        //intpatient
        IntPatient intPatient= BeanUtil.toBean(middleDbPatientDto,IntPatient.class);
        intPatient.setNewpacs(true);
        intPatient.setIdPatientclass(intPatient.getIdPatientclass()==null?null:(intPatient.getIdPatientclass().intValue()-1));
        intPatient.setAgeUnit("岁");
        intPatient.setAgeOfReal(intPatient.getAge()==null?null:intPatient.getAge().doubleValue());
        intPatient.setFRegistered(true);
        intPatient.setDtLastByPeis(now);
        intPatient.setHospitalCode("SDQDZK");
        intPatient.setFUsePatientAsOrg(false);
        intPatient.setPatientCodeHiden(serial);
        intPatient.setFUseCodeHiden(false);
        intPatient.setDateRegister(now);//登记时间
        intPatient.setDateCreatedPeisPatient(now);//最初创建时间
        intPatient.setFPaused(false);
        intPatient.setFTransfered(false);
        intPatient.setFFeeCharged(true);
        intPatient.setNumDay(numDay);
        intPatient.setFTransfered(false);
        intPatient.setFFeeCharged(true);
        intPatient.setDateCreatedPeisPatient(now);//最初创建时间
        intPatient.setFPaused(false);
        intPatient.setFTransfered(false);
        intPatient.setInputCode(checkLength(intPatient.getInputCode(),8));
        intPatientMapper.insert(intPatient);

        //IntPatientTransTarget
        List<MiddleDbPatientTransTargetDto> middleDbPatientTransTargetDtos=middleDbDto.getMiddleDbPatientTransTargetDtos();
        for(MiddleDbPatientTransTargetDto middleDbPatientTransTargetDto:middleDbPatientTransTargetDtos){
            IntPatientTransTarget intPatientTransTarget=BeanUtil.toBean(middleDbPatientTransTargetDto,IntPatientTransTarget.class);
            intPatientTransTarget.setFReturned(false);
            intPatientTransTarget.setFTransfered(false);
            intPatientTransTarget.setFResultTransfered(false);
            intPatientTransTarget.setHospitalCode("SDQDZK");
            intPatientTransTarget.setIdPatient(idPatient);
            intPatientTransTarget.setStrIdpatient(patientcode);
            intPatientTransTarget.setPatientCode(patientcode8);
            intPatientTransTarget.setPatientCodeHiden(serial);
            intPatientTransTarget.setPatientName(patientName);
            intPatientTransTargetMapper.insert(intPatientTransTarget);
        }

        //IntPatientFeeItem
        List<MiddleDbItemDto> middleDbItemDtos=middleDbDto.getMiddleDbItemDtos();
        int count=1;
        for(MiddleDbItemDto middleDbItemDto:middleDbItemDtos){
            IntPatientFeeItem intPatientFeeItem=BeanUtil.toBean(middleDbItemDto,IntPatientFeeItem.class);
            intPatientFeeItem.setHospitalCode("SDQDZK");
            intPatientFeeItem.setIdPatient(idPatient);
            intPatientFeeItem.setStrIdpatient(patientcode);
            intPatientFeeItem.setPatientCode(patientcode8);
            intPatientFeeItem.setPatientCodeHiden(serial);
            intPatientFeeItem.setFFeeCharged(true);
            intPatientFeeItem.setFTransfered(false);
            intPatientFeeItem.setFReturned(false);
            intPatientFeeItem.setFResultTransfered(false);
            intPatientFeeItem.setFTransferedOk(false);
            intPatientFeeItem.setFBackExamFinished(false);
            intPatientFeeItem.setFBackExamStarted(false);
            intPatientFeeItem.setFBackTransfered(false);
            intPatientFeeItem.setFIsDeleted(false);
            intPatientFeeItem.setPatientName(patientName);
            intPatientFeeItem.setDtLastByPeis(now);
            Integer idPatientFeeItem=CodeUtil.getHqIdPatientFeeItem();
            intPatientFeeItem.setIdPatientFeeItem(idPatientFeeItem);
            intPatientFeeItemMapper.insert(intPatientFeeItem);

            //IntPatientTransFeeItem
            if("4".equals(middleDbItemDto.getXsdyfl())){
                IntPatientTransFeeItem transFeeItem=BeanUtil.toBean(middleDbItemDto,IntPatientTransFeeItem.class);
                transFeeItem.setDepartRegCode("TJ");
                transFeeItem.setDepartRegName("体检中心");
                transFeeItem.setDepartExeCode("TJ");
                transFeeItem.setDepartExeName("体检中心");
                transFeeItem.setHospitalCode("SDQDZK");
                transFeeItem.setIdPatient(idPatient);
                transFeeItem.setPatientCode(patientcode8);
                Integer idPatientTraceExamDepart= CodeUtil.getHqIdPatientTraceExamDepart();
                transFeeItem.setIdPatientTraceExamDepart(idPatientTraceExamDepart);
                transFeeItem.setQty(1.00);
                transFeeItem.setIdPatientFeeItem(count++);
                transFeeItem.setApprovedDate(transFeeItem.getFeeChargeTime());
                transFeeItem.setApprovedTime(transFeeItem.getFeeChargeTime());
                transFeeItem.setIdExamFeeItemTrans(middleDbItemDto.getIdExamFeeItem());
                transFeeItem.setExamFeeItemTransName(middleDbItemDto.getExamFeeItemName());
                transFeeItem.setExamFeeItemTransCode(middleDbItemDto.getExamFeeItemCode());
                intPatientTransFeeItemMapper.insert(transFeeItem);
            }
        }

        //IntPatientExamItem
        List<MiddleDbExamDto> middleDbExamDtos=middleDbDto.getMiddleDbExamDtos();
        for(MiddleDbExamDto middleDbExamDto:middleDbExamDtos){
            IntPatientExamItem patientExamItem= BeanUtil.toBean(middleDbExamDto,IntPatientExamItem.class);
            patientExamItem.setHospitalCode("SDQDZK");
            patientExamItem.setFTransfered(false);
            patientExamItem.setFReturned(false);
            patientExamItem.setFResultTransfered(false);
            patientExamItem.setIdPatient(idPatient);
            patientExamItem.setStrIdpatient(patientcode);
            patientExamItem.setPatientCode(patientcode8);
            patientExamItem.setPatientCodeHiden(serial);
            patientExamItem.setPatientName(patientName);
            patientExamItem.setDtLastByPeis(now);
            Integer idPatientExamItem=CodeUtil.getHqIdPatientExamItem();
            patientExamItem.setIdPatientExamItem(idPatientExamItem);
            intPatientExamItemMapper.insert(patientExamItem);
        }
    }

    /**
     * 当字段长度超出中间库长度限制时截断字符串
     * @param origin
     * @param length
     * @return
     */
    public static String checkLength(String origin, int length) {
        if (StrUtil.isEmpty(origin)) return origin;
        String result = origin;

        // 获取字符串的字节长度
        byte[] bytes = origin.getBytes(Charset.forName("UTF-8"));

        // 如果字节长度超过最大长度
        if (bytes.length > length) {
            int byteCount = 0;
            int charCount = 0;

            // 按字符遍历，累积字节长度
            while (charCount < origin.length()) {
                String s = origin.substring(0, charCount + 1);  // 获取当前字符的前缀
                byte[] tempBytes = s.getBytes(Charset.forName("UTF-8"));
                byteCount = tempBytes.length;

                // 如果当前累积字节长度不超过目标字节长度
                if (byteCount <= length) {
                    charCount++;
                } else {
                    break;
                }
            }

            // 截取符合字节长度要求的子字符串
            result = origin.substring(0, charCount);
        }

        return result;
    }


    public static void main(String[] args) {
        String string = checkLength("WX（D）", 8);
        System.out.println(string);
    }

    /**
     * 读取lis结果
     * @param patientcode
     * @return
     */
    @Override
    @DataSource(DataSourceType.HONGQIAO)
    public List<LisDto> selectList(String patientcode){
        log.info("读取lis结果:"+patientcode);
        //lis库中使用8位短号
        String patientcode8=patientcode.length()<=8?patientcode:patientcode.substring(patientcode.length()-8);
        List<LisHqDto> lisHqDtos=baseMapper.selectList(patientcode8);
        List<LisDto> lisDtoS=new ArrayList<>();
        //每个小项只取最近一条结果
        Set<String> examKeys=new HashSet<>();
        for(LisHqDto lisHqDto:lisHqDtos){
            String key=lisHqDto.getItemCode()+"-"+lisHqDto.getExamCode();
            if(examKeys.contains(key)){
                continue;
            }else{
                examKeys.add(key);
            }
            LisDto lisDto=new LisDto();
            lisDto.setExamDateTime(getDateTimeByStr(lisHqDto.getNdate(),lisHqDto.getNtime()));
            lisDto.setLisybbh(lisHqDto.getLisybbh());
            lisDto.setItemCode(lisHqDto.getItemCode());
            lisDto.setExamCode(lisHqDto.getExamCode());
            lisDto.setExamItemValuesNumber(lisHqDto.getExamItemValuesNumber());
            lisDto.setExamItemValuesShort(lisHqDto.getExamItemValuesShort());
            lisDto.setExamItemValuesReport(lisHqDto.getExamItemValuesNumber()==null?lisHqDto.getExamItemValuesShort():lisHqDto.getExamItemValuesNumber().toString());
            lisDto.setRefRange(lisHqDto.getRefRange());
            lisDto.setStatus(getStatus(lisHqDto));
            lisDto.setUnits(lisHqDto.getUnits());
            lisDto.setLisCode(lisHqDto.getLisCode());
            lisDto.setExamDoctor(lisHqDto.getExamDoctor());
            lisDto.setAuditName(lisHqDto.getAuditName());
            lisDto.setInspectName(lisHqDto.getInspectName());
            lisDto.setAuditDate(getDateTimeByStr(lisHqDto.getAuditDate(),lisHqDto.getAuditTime()));
            lisDto.setInspectCode(lisHqDto.getInspectCode());
            lisDto.setReceiveDate(getDateTimeByStr(lisHqDto.getReceiveDate(),lisHqDto.getReceiveTime()));
            lisDtoS.add(lisDto);
        }
        log.info(JSONUtil.toJsonStr(lisDtoS));
        return lisDtoS;
    }


    LocalDateTime getDateTimeByStr(String date,String time){
        if(StrUtil.isNotEmpty(date)&& StrUtil.isNotEmpty(time)){
            String examDateTime=null;
            if(time.length()<4){
                examDateTime=date+"0"+time;
            }else{
                examDateTime=date+time;
            }
            return LocalDateTime.parse(examDateTime, DateTimeFormatter.ofPattern("yyyyMMddHHmm"));
        }else{
            return null;
        }
    }

    String getStatus(LisHqDto lisHqDto){
        String status=lisHqDto.getStatus();
        status=status==null?null:status.trim();
//        String result= StrUtil.isNotEmpty(lisHqDto.getExamItemValuesShort())|| StrUtil.isEmpty(status)
//                ?"":"H".equals(status)?"↑":"L".equals(status)?"↓":status.trim();//N正常H↑L↓
        //尿常规小项结果 -，也要求显示status
        String result= StrUtil.isEmpty(status)
                ?"":"H".equals(status)?"↑":"L".equals(status)?"↓":status.trim();//N正常H↑L↓
        return result;
    }
}
