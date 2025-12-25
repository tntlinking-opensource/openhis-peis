package com.center.medical.pacslis.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.*;
import com.center.medical.abteilung.bean.model.SectionResultMain;
import com.center.medical.abteilung.dao.SectionResultMainMapper;
import com.center.medical.common.config.PacsConfig;
import com.center.medical.common.constant.Constants;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.core.domain.entity.SysUser;
import com.center.medical.common.enums.PacsType;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.dao.*;
import com.center.medical.data.bean.model.Items;
import com.center.medical.data.dao.ItemsMapper;
import com.center.medical.pacslis.bean.dto.PacsDto;
import com.center.medical.pacslis.bean.dto.PacsItemDto;
import com.center.medical.pacslis.dao.PacsInterfaceMapper;
import com.center.medical.pacslis.service.LisInterfaceService;
import com.center.medical.pacslis.service.PacsInterfaceService;
import com.center.medical.reception.service.OutsideMainService;
import com.center.medical.sellcrm.bean.model.Comboexamitem;
import com.center.medical.service.PeisStateService;
import com.center.medical.service.PeispatientfeeitemService;
import com.center.medical.system.dao.SysUserMapper;
import com.center.medical.system.service.ISysConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author xhp
 * @since 2023-03-07 10:41
 */
@Slf4j
@Service("pacsInterfaceService")
@RequiredArgsConstructor
public class PacsInterfaceServiceImpl extends ServiceImpl<PacsInterfaceMapper, PacsResult> implements PacsInterfaceService {
    private final LisInterfaceService lisInterfaceService;
    private final AttachmentMapper attachmentMapper;
    private final PeispatientfeeitemMapper peispatientfeeitemMapper;
    private final SysUserMapper sysUserMapper;
    private final SectionResultMainMapper sectionResultMainMapper;
    private final PeispatientMapper peispatientMapper;
    private final OutsideMainService outsideMainService;
    private final PeisStateService peisStateService;
    private final PeispatientfeeitemService peispatientfeeitemService;
    private final ISysConfigService iSysConfigService;
    private final ItemsMapper itemsMapper;
    private final PacsItemMapper pacsItemMapper;
    private final PacsSectionResultTwoMapper pacsSectionResultTwoMapper;


    /**
     * 获取pacs结果
     * @param patientcode
     * @return
     */
    @Override
    public R<String> receive(String patientcode){
        //调用接口获取结果
        String url = iSysConfigService.getDomain().getLisDomain() + "/open/api/pacs/list";

        Map<String, Object> param = new HashMap<>();
        log.info("获取pacs结果体检号:" + patientcode);
        param.put("patientcode", patientcode);

        String pacsConfigStr = iSysConfigService.selectConfigByKey(Constants.PACS_CONFIG);
        param.put("pacsConfigStr",pacsConfigStr);
//        log.info("打印一下参数:" + param);

        String s = HttpUtil.get(url, param);
//        log.info("请求结果：" + s);
        R responseEntity = JSONUtil.toBean(s, R.class);

        if (200 != responseEntity.getCode()) {
            log.error("请求pacs接口失败{}、{}", url, JSONUtil.toJsonStr(responseEntity.getMsg()), patientcode);
            return R.fail("获取失败！");
        }
        if (ObjectUtils.isEmpty(responseEntity.getData())) {
            return R.fail("没有体检号" + patientcode + "的数据！");
        }

//        log.info("请求pacs接口成功", JSONUtil.toJsonStr(responseEntity.getData()));

        //保存结果
        List<PacsDto> pacsDtos  = ((List<JSONObject>) responseEntity.getData()).stream().map(
                jsonObject -> jsonObject.toBean(PacsDto.class)
        ).collect(Collectors.toList());
        if (pacsDtos.size() == 0) {
            log.error("请求pacs接口失败：{}", "没有体检号" + patientcode + "的数据！");
            return R.fail("没有体检号" + patientcode + "的数据！");
        }

        save(patientcode, pacsDtos);

        return R.ok("获取成功");
    }

    /**
     * PACS和LIS不同，PACS需要考虑体检系统自带PACS
     * 双审核 可能既使用体检系统审核，又使用易影审核。这种情况保留体检系统审核结果。是咱们自己大夫已经审核的就不要覆盖了，涉及报告内容是否一致的问题。
     * 不一定每个科室都用易影,可能只对接部分科室
     * 一个科室也不一定都用易影
     * @param patientcode
     * @param pacsDtoList
     */
    @Override
    @Transactional
    public void save(String patientcode, List<PacsDto> pacsDtoList){
        Peispatient peispatient = lisInterfaceService.checkPeispatient(patientcode);

        Integer shortCode=peispatient.getShortCode();//短号
        String tjlx=peispatient.getIdExamtype();//体检类型
        String jhys=peispatient.getJhys();//接害因素
        String medicaltype=peispatient.getMedicaltype();//职业检查类型
        String patientname=peispatient.getPatientname();
        Map<String, List<Comboexamitem>> ceis = lisInterfaceService.getComboexamitems(jhys,medicaltype,tjlx);
        Map<String,Set<String>> filePaths=new HashMap<String, Set<String>>();//科室id：图片路径  用于判断图像路径重复；重复的图像只保存一条
        Map<String,StringBuilder> main_map=new HashMap<String,StringBuilder>();//KEY:KSID,VALUE:小结
        Map<String,StringBuilder> zy_main_map=new HashMap<String,StringBuilder>();//KEY:KSID,VALUE:小结
        Map<String,StringBuilder> con_map=new HashMap<String,StringBuilder>();//KEY:KSID,VALUE:只有结论(目前只有海关使用)
        Map<String,StringBuilder> zy_con_map=new HashMap<String,StringBuilder>();//KEY:KSID,VALUE:只有结论(目前只有海关使用)
        Map<String,Map<String,Object>> doctor_map=new HashMap<String, Map<String,Object>>();//保存其中一个检查人审核人审核时间
        PacsConfig pacsConfig=iSysConfigService.getSysConfigObject(Constants.PACS_CONFIG, PacsConfig.class);
        String rn = System.getProperty("line.separator");//换行符
        Set<String> depIdsWithThirdResult=new HashSet<>();//从第三方PACS获取到了结果的科室ID，只有获取到结果，才修改section_result_main。

        //查询所有对接了第三方pacs的科室的项目，包含已检项目
        List<PacsItemDto> pacsItemDtos= baseMapper.selectItemList(patientcode,pacsConfig.getDeptNos());
        for(PacsItemDto pacsItemDto:pacsItemDtos){
//            String itemCode=pacsItemDto.getExamfeeitemCode();//收费项目Code
            String id_ks=pacsItemDto.getIdKs();//收费项目科室ID
            String patient_fee_id=pacsItemDto.getId();//体检者收费项目ID
            Peispatientfeeitem feeitem=peispatientfeeitemMapper.selectById(patient_fee_id);//体检者收费项目
            String itemId=pacsItemDto.getIdExamfeeitem();//收费项目ID
            String itemName=pacsItemDto.getExamfeeitemNameprn();//收费项目打印名称

            Items tjItem=itemsMapper.selectById(itemId);
            String itemCode=tjItem.getExamfeeitemCode();
            String pacsItemId=null;
            if(StrUtil.isNotEmpty(itemCode)){
                PacsItems items = pacsItemMapper.selectOne(
                        new QueryWrapper<PacsItems>()
                                .eq("examfeeitem_code", itemCode)
                                .eq("is_delete", 0)
                );
                pacsItemId=items.getId();
            }

            //如果项目已审核
            if(feeitem.getFExaminated()!=null && feeitem.getFExaminated().intValue()==1){
                //体检系统结果
                PacsResult pacsResultMedical=baseMapper.selectOne(
                        new LambdaQueryWrapper<PacsResult>()
                                .eq(PacsResult::getPatientcode,patientcode)
                                .eq(PacsResult::getItemId,itemId)
                                .eq(PacsResult::getIsNewPacs,1)
                );
                //如果是使用体检系统审核的,不能覆盖结果
                if(pacsResultMedical!=null){
                    String examResultDesc=pacsResultMedical.getExamresultdesc();
                    String examResultSummary=pacsResultMedical.getExamresultsummary();
                    //小结
                    if(main_map.get(id_ks)==null){
                        StringBuilder conclusions=new StringBuilder();
                        if(examResultDesc!=null){
                            conclusions.append("["+itemName+"]所见："+rn);
                            conclusions.append(examResultDesc+rn);
                        }
                        if(examResultSummary!=null){
                            conclusions.append("["+itemName+"]提示："+rn);
                            conclusions.append(examResultSummary+rn);
                        }
                        main_map.put(id_ks,conclusions);
                    }else{
                        StringBuilder conclusions=main_map.get(id_ks);
                        if(examResultDesc!=null){
                            conclusions.append("["+itemName+"]所见："+rn);
                            conclusions.append(examResultDesc+rn);
                        }
                        if(examResultSummary!=null){
                            conclusions.append("["+itemName+"]提示："+rn);
                            conclusions.append(examResultSummary+rn);
                        }
                    }
                    if(con_map.get(id_ks)==null){
                        StringBuilder conclusions=new StringBuilder();
                        if(examResultSummary!=null){
                            conclusions.append("["+itemName+"]提示："+rn);
                            conclusions.append(examResultSummary+rn);
                        }
                        con_map.put(id_ks,conclusions);
                    }else{
                        StringBuilder conclusions=con_map.get(id_ks);
                        if(examResultSummary!=null){
                            conclusions.append("["+itemName+"]提示："+rn);
                            conclusions.append(examResultSummary+rn);
                        }
                    }
                    if(ceis!=null&&ceis.get(itemId)!=null){
                        if(zy_main_map.get(id_ks)==null){
                            StringBuilder conclusions=new StringBuilder();
                            if(examResultDesc!=null){
                                conclusions.append("["+itemName+"]所见："+rn);
                                conclusions.append(examResultDesc+rn);
                            }
                            if(examResultSummary!=null){
                                conclusions.append("["+itemName+"]提示："+rn);
                                conclusions.append(examResultSummary+rn);
                            }
                            zy_main_map.put(id_ks,conclusions);
                        }else{
                            StringBuilder conclusions=zy_main_map.get(id_ks);
                            if(examResultDesc!=null){
                                conclusions.append("["+itemName+"]所见："+rn);
                                conclusions.append(examResultDesc+rn);
                            }
                            if(examResultSummary!=null){
                                conclusions.append("["+itemName+"]提示："+rn);
                                conclusions.append(examResultSummary+rn);
                            }
                        }
                        if(zy_con_map.get(id_ks)==null){
                            StringBuilder conclusions=new StringBuilder();
                            if(examResultSummary!=null){
                                conclusions.append("["+itemName+"]提示："+rn);
                                conclusions.append(examResultSummary+rn);
                            }
                            zy_con_map.put(id_ks,conclusions);
                        }else{
                            StringBuilder conclusions=zy_con_map.get(id_ks);
                            if(examResultSummary!=null){
                                conclusions.append("["+itemName+"]提示："+rn);
                                conclusions.append(examResultSummary+rn);
                            }
                        }
                    }
                    //记录这个科室存在体检系统审核的项目  只有全部在体检系统审核了，才会有section_result_main
//                    if(doctor_map.get(id_ks)==null){
//                        Map<String,Object> dmap= new HashMap<>();
//                        dmap.put("isNewSys","1");
//                        doctor_map.put(id_ks, dmap);
//                    }else{
//                        doctor_map.get(id_ks).put("isNewSys","1");
//                    }
                    continue;
                }
            }

            //第三方pacs结果
            PacsDto pacsDto=searchResult(pacsDtoList,itemCode,patient_fee_id);

            /** 可能是此功能上线前的已检项目。如果中间表没有结果，不做任何处理，不要清除以前的结果。 */
            if(pacsDto==null){
                continue;
            }
            //记录这个项目从第三方pacs系统中获取了结果
            depIdsWithThirdResult.add(id_ks);

            //删除旧数据
            baseMapper.delete(
                    new LambdaQueryWrapper<PacsResult>()
                            .eq(PacsResult::getPatientcode,patientcode)
                            .eq(PacsResult::getItemId,itemId)
            );
            //可能是体检系统PACS保存但没审核，可以覆盖
            pacsSectionResultTwoMapper.delete(
                    new QueryWrapper<PacsSectionResultTwo>()
                            .eq("charges_id", pacsItemId)
                            .eq("patientcode", patientcode)
            );

            //peispatientfeeitem
            feeitem.setFExaminated(1);
            Date examDateTime=pacsDto.getExamdatetime();
            if(examDateTime!=null){
                feeitem.setExaminatetime(examDateTime);
            }
            peispatientfeeitemMapper.updateById(feeitem);

            String examDoctor=pacsDto.getExamdoctor();//检查师 检查人用户名
            String examResultDesc=pacsDto.getExamresultdesc();//检查结果描述
            String examResultSummary=pacsDto.getExamresultsummary();//检查结果总结
            String imageFullPath=pacsDto.getImagefullpath();//文件路径
            String auditDoctor=pacsDto.getAuditDoctor();//审核人用户名

            //pacsresult
            PacsResult pacsResult= new PacsResult();
            pacsResult.setExamdatetime(examDateTime);
            pacsResult.setExamdoctor(examDoctor);
            pacsResult.setExamresultdesc(examResultDesc);
            pacsResult.setExamresultsummary(examResultSummary);
            pacsResult.setImagefullpath(imageFullPath);
            pacsResult.setUsername(examDoctor);//当前登录用户、录入人
            pacsResult.setPatientcode(patientcode);
            pacsResult.setPatientname(patientname);
            pacsResult.setItemId(itemId);
            pacsResult.setItemName(itemName);
            pacsResult.setShortCode(shortCode);
            pacsResult.setDepId(id_ks);
            pacsResult.setExamfeeitemCode(itemCode);
            pacsResult.setAuditDoctor(auditDoctor);
            pacsResult.setExamresultisnormal("0");
            pacsResult.setFResulttransfered(0);
            pacsResult.setWriteDate(examDateTime);
            pacsResult.setPacsItemId(pacsItemId);
            if(PacsType.YIYING.name().equals(pacsDto.getType())){
                pacsResult.setIsNewPacs(2);
            }
            baseMapper.insert(pacsResult);

            //attachment
            //易影传图和获取结果是分开的,不用处理

            //小结
            if(main_map.get(id_ks)==null){
                StringBuilder conclusions=new StringBuilder();
                if(examResultDesc!=null){
                    conclusions.append("["+itemName+"]所见："+rn);
                    conclusions.append(examResultDesc+rn);
                }
                if(examResultSummary!=null){
                    conclusions.append("["+itemName+"]提示："+rn);
                    conclusions.append(examResultSummary+rn);
                }
                main_map.put(id_ks,conclusions);
            }else{
                StringBuilder conclusions=main_map.get(id_ks);
                if(examResultDesc!=null){
                    conclusions.append("["+itemName+"]所见："+rn);
                    conclusions.append(examResultDesc+rn);
                }
                if(examResultSummary!=null){
                    conclusions.append("["+itemName+"]提示："+rn);
                    conclusions.append(examResultSummary+rn);
                }
            }
            if(con_map.get(id_ks)==null){
                StringBuilder conclusions=new StringBuilder();
                if(examResultSummary!=null){
                    conclusions.append("["+itemName+"]提示："+rn);
                    conclusions.append(examResultSummary+rn);
                }
                con_map.put(id_ks,conclusions);
            }else{
                StringBuilder conclusions=con_map.get(id_ks);
                if(examResultSummary!=null){
                    conclusions.append("["+itemName+"]提示："+rn);
                    conclusions.append(examResultSummary+rn);
                }
            }
            if(ceis!=null&&ceis.get(itemId)!=null){
                if(zy_main_map.get(id_ks)==null){
                    StringBuilder conclusions=new StringBuilder();
                    if(examResultDesc!=null){
                        conclusions.append("["+itemName+"]所见："+rn);
                        conclusions.append(examResultDesc+rn);
                    }
                    if(examResultSummary!=null){
                        conclusions.append("["+itemName+"]提示："+rn);
                        conclusions.append(examResultSummary+rn);
                    }
                    zy_main_map.put(id_ks,conclusions);
                }else{
                    StringBuilder conclusions=zy_main_map.get(id_ks);
                    if(examResultDesc!=null){
                        conclusions.append("["+itemName+"]所见："+rn);
                        conclusions.append(examResultDesc+rn);
                    }
                    if(examResultSummary!=null){
                        conclusions.append("["+itemName+"]提示："+rn);
                        conclusions.append(examResultSummary+rn);
                    }
                }
                if(zy_con_map.get(id_ks)==null){
                    StringBuilder conclusions=new StringBuilder();
                    if(examResultSummary!=null){
                        conclusions.append("["+itemName+"]提示："+rn);
                        conclusions.append(examResultSummary+rn);
                    }
                    zy_con_map.put(id_ks,conclusions);
                }else{
                    StringBuilder conclusions=zy_con_map.get(id_ks);
                    if(examResultSummary!=null){
                        conclusions.append("["+itemName+"]提示："+rn);
                        conclusions.append(examResultSummary+rn);
                    }
                }
            }

            //保存审核人
            if(doctor_map.get(id_ks)==null){
                SysUser examDoc=sysUserMapper.selectUserByUserName(examDoctor);
                SysUser auditDoc=sysUserMapper.selectUserByUserName(auditDoctor);
                if(examDoc!=null && auditDoc!=null){
                    Map<String,Object> dmap=new HashMap<String, Object>();
                    dmap.put("auditName",auditDoctor);
                    dmap.put("rummaerName", examDoctor);
                    dmap.put("auditTime", examDateTime);
                    dmap.put("auditId", auditDoc.getUserNo());
                    dmap.put("rummagerId", examDoc.getUserNo());
                    doctor_map.put(id_ks, dmap);
                }
            }

        }

        if(main_map.isEmpty()){
            throw new ServiceException("没有体检号"+patientcode+"的数据");
        }

        //sectionresultmain
        for(Map.Entry<String,StringBuilder> entry:main_map.entrySet()){
            String id_ks=entry.getKey();
            if(!depIdsWithThirdResult.contains(id_ks))continue;

            String conclusions=entry.getValue().toString();
            String cons=con_map.get(id_ks).toString();
            SectionResultMain main=sectionResultMainMapper.selectOne(
                    new LambdaQueryWrapper<SectionResultMain>()
                        .eq(SectionResultMain::getPatientcode,patientcode)
                    .eq(SectionResultMain::getDepId,id_ks)
            );
            if(main==null){
                main=new SectionResultMain();
                main.setDepId(id_ks);
                main.setPatientcode(patientcode);
                main.setShortCode(shortCode);
                main.setAssociativeTable("PACS_RESULT");
            }
            main.setIsFinish(0);//未上传
            main.setConclusions(conclusions);
            main.setPacsConclusions(cons);
            if("1".equals(tjlx)||"3".equals(tjlx)){
                main.setZyConclusions(conclusions);
                main.setZyPacsConclusions(cons);
            }else if("2".equals(tjlx)){
                main.setZyConclusions(zy_main_map.get(id_ks)==null?null:zy_main_map.get(id_ks).toString());
                main.setZyPacsConclusions(zy_con_map.get(id_ks)==null?null:zy_con_map.get(id_ks).toString());
            }
            Map<String,Object>dmap=doctor_map.get(id_ks);
            if(dmap!=null){
                String auditId=dmap.get("auditId")==null?null:dmap.get("auditId").toString();
                Date auditTime=dmap.get("auditTime")==null?null:(Date)dmap.get("auditTime");
                main.setAuditId(auditId);
                main.setAuditTime(auditTime);
                main.setAuditName(dmap.get("auditName")==null?null:dmap.get("auditName").toString());
                main.setRummagerId(dmap.get("rummagerId")==null?null:dmap.get("rummagerId").toString());
                main.setRummagerName(dmap.get("rummaerName")==null?null:dmap.get("rummaerName").toString());
                main.setRummagerTime(auditTime);
                main.setWriteTime(auditTime);
                main.setWriteId(auditId);
            }

            //判断科室已全部完成检查
            List<Peispatientfeeitem> peispatientfeeitemList = peispatientfeeitemMapper.selectList(new QueryWrapper<Peispatientfeeitem>()
                    .eq("id_ks", id_ks)
                    .eq("id_patient", patientcode)
                    .eq("change_item", 0)
                    .eq("f_giveup", 0)
                    .eq("f_feecharged", 1)
                    .isNull("f_transferedhl7")
                    .eq("f_examinated", 0)
                    .eq("sfjj", 0));
            if (CollectionUtil.isEmpty(peispatientfeeitemList)) {
                main.setIsAudit(1);//科室已全部完成检查
            }else{
                main.setIsAudit(0);
            }

            if(main.getId()==null){
                sectionResultMainMapper.insert(main);
            }else{
                sectionResultMainMapper.updateById(main);
            }
        }

        //判断分检完成
        if (outsideMainService.getAllSfxmtzjStatus(patientcode)) {
            peispatient.setFReadytofinal(1);//0:已备单 1:分检完成
            peisStateService.setScbs(patientcode, 0);
            peispatient.setReadytofinalDate(new Date());
            List<Peispatientfeeitem> other_items = peispatientfeeitemMapper.getNoCheckItems(patientcode);
            for (Peispatientfeeitem other : other_items) {
                other.setFExaminated(1);//设置未关联科室项目为已检
            }
            peispatientfeeitemService.updateBatchById(other_items);
        }

        //peispatient
        peispatient.setFExamstarted(1);
        peispatientMapper.updateById(peispatient);
    }

    /**
     * 查找收费项目结果
     * @param pacsDtoList
     * @param itemCode 收费项目接口代码
     * @param patient_fee_id 体检者收费项目id
     * @return
     */
    PacsDto searchResult(List<PacsDto> pacsDtoList,String itemCode,String patient_fee_id){
        PacsDto pacsDtoResult=null;
        //易影
        for(PacsDto pacsDto:pacsDtoList){
            if(patient_fee_id.equals(pacsDto.getFeeitemId())){
                return pacsDto;
            }
        }

        if(StrUtil.isNotEmpty(itemCode)){
            for(PacsDto pacsDto:pacsDtoList){
                if(itemCode.equals(pacsDto.getExamfeeitemCode())){
                    pacsDtoResult=pacsDto;
                    break;
                }
            }
        }

        return pacsDtoResult;
    }

    @Override
    public List<String> receivePacsDataUser(){
        PacsConfig pacsConfig=iSysConfigService.getSysConfigObject(Constants.PACS_CONFIG, PacsConfig.class);
        return baseMapper.receivePacsDataUser(pacsConfig.getDeptNos(),pacsConfig.getDaysAgo());
    }
}
