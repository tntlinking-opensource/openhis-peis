package com.center.medical.pacs.task;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.center.medical.bean.enums.MarriageType;
import com.center.medical.bean.model.PacsItems;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.bean.model.Peispatientfeeitem;
import com.center.medical.bean.model.SysBranch;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.http.HttpUtils;
import com.center.medical.data.bean.model.Items;
import com.center.medical.data.service.ItemsService;
import com.center.medical.pacs.bean.dto.PickupBrDto;
import com.center.medical.pacs.bean.model.IntPatient;
import com.center.medical.pacs.bean.model.IntPatientFeeItem;
import com.center.medical.service.PacsItemsService;
import com.center.medical.service.PeispatientService;
import com.center.medical.service.PeispatientarchiveService;
import com.center.medical.service.PeispatientfeeitemService;
import com.center.medical.system.service.ISysBranchService;
import com.center.medical.system.service.ISysConfigService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author: ay
 * @date: 2025-08-14 15:46
 * @description: 抓取中间库职业的数据
 */
@Slf4j
@Component("pickupBrTask")
public class PickupBrTask {
    @Resource
    private ISysConfigService iSysConfigService;
    @Resource
    private PeispatientService peispatientService;
    @Resource
    private ISysBranchService iSysBranchService;
    @Resource
    private PeispatientarchiveService peispatientarchiveService;
    @Resource
    private ItemsService itemsService;
    @Resource
    private PacsItemsService pacsItemsService;
    @Resource
    private PeispatientfeeitemService peispatientfeeitemService;



    /**
     * 抓取中间库职业的数据
     *
     * @return
     */
    public void start() {
        log.info("抓取中间库职业的数据定时任务开始");
        String urlPrefix = iSysConfigService.getDomain().getLisDomain();
//        String urlPrefix = "http://localhost:8092";
        String url = urlPrefix + "/open/api/pacs/pickupBr";
        String s = HttpUtils.sendGet(url, null);
        R responseEntity = JSONUtil.toBean(s, R.class);
        if (200 != responseEntity.getCode()) {
            throw new ServiceException("抓取中间库职业的数据拉取失败！");
        }
        List<String> PickupCode = new ArrayList<>();
        List<PickupBrDto> list = JSON.parseArray(responseEntity.getData().toString(), PickupBrDto.class);
        if (CollectionUtils.isEmpty(list)){
            log.info("抓取中间库职业的数据为空，任务结束！");
            return;
        }
        log.info("抓取中间库职业的数据返回信息：{}",JSON.toJSONString(list));
        for (PickupBrDto dto : list) {
            IntPatient intPatient = dto.getIntPatient();
            String patientCode = intPatient.getPatientCode();

            Peispatient patient = peispatientService.getOne(new LambdaQueryWrapper<Peispatient>()
                    .eq(Peispatient::getPatientcode,patientCode).eq(Peispatient::getIdExamplace,1.0));//用idExamplace作为pacs中间库抓取的标识
            if(patient==null){
                patient=new Peispatient();
                patient.setPatientcode(patientCode);
                patient.setIdExamtype("0");
            }
            patient.setFRegistered(1);
            patient.setIdcardno(intPatient.getIdcardNo());
            patient.setPatientname(intPatient.getPatientName());
            patient.setInputCode(intPatient.getInputCode());
            patient.setIdSex("男".equals(intPatient.getSex())?0:"女".equals(intPatient.getSex())?1:null);
            patient.setBirthdate(intPatient.getBirthDate());
            patient.setAge(intPatient.getAge());
            String marry=intPatient.getMarriage();
            if(StringUtils.isNotEmpty(marry)){
                patient.setIdMarriage(MarriageType.getValueByName(marry));
                patient.setMarriage(marry);
                SysBranch defaultBranch = iSysBranchService.getDefaultBranch();
                patient.setHospitalcode(iSysBranchService.getBranchFlag(defaultBranch.getBranchId()));
                patient.setFPaused(0);
            }
            patient.setAddress(intPatient.getResAddress());
            patient.setPhone(intPatient.getPhone());
            patient.setOrgName(intPatient.getOrgName());
            patient.setOrgDepart(intPatient.getOrgDepart());
            patient.setDateregister(intPatient.getDateRegister());
            patient.setIdExamplace(1.0);//用idExamplace作为pacs抓取的标识
            // 绑定档案
            try {
                log.info("开始绑定档案");
                String patientarchiveno = peispatientarchiveService.bingIArchive(patient, false, "402848e35c9f2a3b015caeb8c0cf0bc3");
                patient.setPatientarchiveno(patientarchiveno);
            } catch (Exception e) {
                log.error("绑定档案失败！",e);
            }

            peispatientService.saveOrUpdate(patient);


            //收费项目
            List<IntPatientFeeItem> intfeeitems = dto.getIntPatientFeeItems();
            for(IntPatientFeeItem intitem:intfeeitems){
                String itemId=intitem.getIdExamFeeItem()==null?null:intitem.getIdExamFeeItem().toString();
                String itemName=intitem.getExamFeeItemName();
                if(itemId==null){
                    log.info("体检号:"+patientCode+"收费项目："+itemName+"的id不存在");
                    continue;
                }
                Items item = itemsService.getInfoById(itemId);
                if(item==null){
                    log.info("体检号:"+patientCode+"收费项目："+itemName+"在体检软件中不存在");
                    continue;
                }
                if(item.getExamfeeitemCode()==null){
                    log.info("体检号:"+patientCode+"收费项目："+itemName+"没有维护接口代码");
                    continue;
                }
                PacsItems pitem = pacsItemsService.getOne(new LambdaQueryWrapper<PacsItems>().eq(PacsItems::getExamfeeitemCode,item.getExamfeeitemCode()));
                if(pitem==null){
                    log.info("体检号:"+patientCode+"收费项目："+itemName+"没有对应接口代码的pacs项目");
                    continue;
                }
                Peispatientfeeitem feeitem = peispatientfeeitemService.getOne(new LambdaQueryWrapper<Peispatientfeeitem>()
                        .eq(Peispatientfeeitem::getIdPatient,patientCode)
                        .eq(Peispatientfeeitem::getIdExamfeeitem,pitem.getId()));

                if(feeitem==null){
                    feeitem=new Peispatientfeeitem();
                    feeitem.setIdPatient(patientCode);
                    feeitem.setIdExamfeeitem(pitem.getId());
                    feeitem.setExamfeeitemName(pitem.getExamfeeitemName());
                    feeitem.setIdKs(pitem.getIdDepart());
                }
                feeitem.setFRegistered(1);
                feeitem.setFDelayed(0);
                feeitem.setSfjx(0);
                feeitem.setFFeecharged(1);
                feeitem.setFGiveup(0);
                feeitem.setChangeItem(0);

                peispatientfeeitemService.saveOrUpdate(feeitem);
            }

            PickupCode.add(patientCode);
        }
        if (CollectionUtils.isNotEmpty(PickupCode)){
            log.info("设置标识请求数据：{}", PickupCode);
            String setUrl = urlPrefix + "/open/api/pacs/setFTransfered";
            String string = HttpUtils.sendGet(setUrl, "patientcodes=" + String.join(",", PickupCode));
            log.info("设置标识返回数据：{}", string);
        }

        log.info("抓取中间库职业的数据定时任务结束");
    }

}
