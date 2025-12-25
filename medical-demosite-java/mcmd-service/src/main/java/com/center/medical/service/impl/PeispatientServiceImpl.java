package com.center.medical.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.dto.*;
import com.center.medical.bean.param.*;
import com.center.medical.bean.event.DelReservationEvent;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.bean.model.PeispatientChargeMain;
import com.center.medical.bean.model.Peispatientcharge;
import com.center.medical.bean.model.Peispatientfeeitem;
import com.center.medical.bean.vo.OrderUserVo;
import com.center.medical.bean.vo.SearchCodeVo;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.MD5;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.dao.PeispatientMapper;
import com.center.medical.dao.PeispatientchargeMapper;
import com.center.medical.service.PeispatientChargeMainService;
import com.center.medical.service.PeispatientService;
import com.center.medical.service.PeispatientfeeitemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * QT体检者表(Peispatient)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-12-06 10:15:52
 */
@Slf4j
@Service("peispatientService")
@RequiredArgsConstructor
public class PeispatientServiceImpl extends ServiceImpl<PeispatientMapper, Peispatient> implements PeispatientService {

    private final PeispatientMapper peispatientMapper;
    private final PeispatientChargeMainService peispatientChargeMainService;
    private final PeispatientfeeitemService peispatientfeeitemService;
    private final PeispatientchargeMapper peispatientchargeMapper;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final MapperFacade mapperFacade;

    /**
     * 分页查询[QT体检者表]列表
     *
     * @param page  分页参数
     * @param param Peispatient查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Peispatient> getPage(PageParam<Peispatient> page, Peispatient param) {
        return peispatientMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Peispatient getInfoById(String id) {
        return peispatientMapper.getInfoById(id);
    }

    @Override
    public IPage<Peispatient> listData(PageParam<Peispatient> page, String ksId) {
        return null;
    }

    /**
     * 通过体检码获取记录
     *
     * @param patientCode
     * @return
     */
    @Override
    public Peispatient getByPatientCode(String patientCode) {
        return peispatientMapper.getByPatientCode(patientCode);
    }


    /**
     * 删除体检号
     *
     * @param patientCode
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deletePeispatient(String patientCode) {
        //去空格
        if (ObjectUtils.isNotEmpty(patientCode)) {
            patientCode = patientCode.trim();
        }
        if (StringUtils.isNotEmpty(patientCode)) {
            //删除体检者表
            peispatientMapper.delete(new QueryWrapper<Peispatient>().eq("patientcode", patientCode));
            //体检者缴费表
            peispatientchargeMapper.delete(new QueryWrapper<Peispatientcharge>()
                    .eq("patientcode", patientCode));
            //体检者费用主表
            peispatientChargeMainService.remove(new QueryWrapper<PeispatientChargeMain>()
                    .eq("patientcode", patientCode));
            //删除收费项目表
            peispatientfeeitemService.remove(new QueryWrapper<Peispatientfeeitem>()
                    .eq("id_patient", patientCode));
            //删除预约信息
            applicationEventPublisher.publishEvent(new DelReservationEvent(patientCode));

        } else {
            throw new ServiceException("体检号不能为空！");
        }
        return Boolean.TRUE;
    }


    /**
     * 搜索体检号
     *
     * @param param
     * @return
     */
    @Override
    public List<SearchCodeVo> searchCode(ReportSearchCodeParam param) {
        return peispatientMapper.searchCode(param);
    }

    /**
     * 查询体检者
     *
     * @param param 筛选条件
     * @return
     */
    @Override
    public List<PatientDto> getPatientDto(PatientDtoParam param) {
        List<PatientDto> patientList = peispatientMapper.getPatientByTime(param);
        return patientList;
    }

    @Override
    public List<PatientDuplicate> geDuplicate(String ddh) {
        List<PatientDuplicate> list = peispatientMapper.geDuplicate(ddh);
        return list;
    }

    @Override
    public Boolean deduplication(PatientDuplicate item) {
        log.warn("1、去重操作PatientDuplicate：{}", JSONUtil.toJsonStr(item));
        List<Peispatient> list = peispatientMapper.geDuplicatePatients(item);
        log.warn("2、去重操作list：{}", JSONUtil.toJsonStr(list));
        List<String> delCodes = new ArrayList<>();
        Boolean registered = Boolean.FALSE;
        Boolean yushou = Boolean.FALSE;
        Boolean startAPP = Boolean.FALSE;
        Peispatient newPt = null;
        //处理去重
        for (Peispatient pt : list) {
            if (Objects.nonNull(pt.getMoneyamountpaid()) && pt.getFRegistered() == 1) {
                //保留已检
                log.warn("3、去重操作已检pt：{}", JSONUtil.toJsonStr(pt));
                registered = Boolean.TRUE;
                continue;
            }
            if (Objects.nonNull(pt.getMoneyamountpaid()) && pt.getMoneyamountpaid() > 0) {
                //保留预收
                log.warn("4、去重操作预收pt：{}", JSONUtil.toJsonStr(pt));
                yushou = Boolean.TRUE;
                continue;
            }
            if (pt.getPatientcode().startsWith("APP") && !startAPP) {
                //保留第一个APP开头
                log.warn("5、去重操作保留APP的pt：{}", JSONUtil.toJsonStr(pt));
                newPt = pt;
                startAPP = Boolean.TRUE;
            }
            delCodes.add(pt.getPatientcode());
        }

        if (registered || yushou) {
            //体检者已检或者预收，保留已检和预收，删除其他
            execDeduplication(newPt, false, delCodes);
        } else {
            // 体检者未来检，优先保留预收的，删除其他的；没有预收保留APP开头，分中心字段设置为空，档案号设置为空，删除其他的；如果没有APP开头的，则将保留第一条，将体检号改为APP开头，分中心字段设置为空，档案号设置为空
            if (startAPP) {
                //有APP开头
                execDeduplication(newPt, false, delCodes);
            } else {
                // 没有APP开头的，保留第一条
                newPt = list.get(0);
                execDeduplication(newPt, true, delCodes);
            }
        }

        log.warn("6、去重操作判断结果：registered={}, yushou={}, startAPP={}, newPt={}, delCodes={}", registered, yushou, startAPP, JSONUtil.toJsonStr(newPt), JSONUtil.toJsonStr(delCodes));

        return Boolean.TRUE;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean execDeduplication(Peispatient newPt, Boolean upToApp, List<String> delCodes) {
        String newPCode = "";
        if (Objects.nonNull(newPt) && !upToApp) {
            //保留APP开头，分中心字段设置为空，档案号设置为空
            peispatientMapper.updateDeduplication(newPt.getId(), newPt.getPatientcode());
            newPCode = newPt.getPatientcode();
        } else if (Objects.nonNull(newPt) && upToApp) {
            //保留第一条，将体检号改为APP开头，分中心字段设置为空，档案号设置为空
            newPCode = "APP" + StrUtil.subSufByLength(newPt.getPatientcode(), 10);
            log.warn("7、去重操作新的体检号：{}", newPCode);
            peispatientMapper.updateDeduplication(newPt.getId(), newPCode);
        }

        if (Objects.nonNull(newPt)) {
            //更新收费项目
            List<Peispatientfeeitem> peispatientfeeitems = peispatientfeeitemService.list(new LambdaQueryWrapper<Peispatientfeeitem>().eq(Peispatientfeeitem::getIdPatient, newPt.getPatientcode()));
            log.warn("8、去重操作更新的收费项目peispatientfeeitems：{}", JSONUtil.toJsonStr(peispatientfeeitems));
            for (Peispatientfeeitem feeItem : peispatientfeeitems) {
                feeItem.setModifydate(new Date());
                feeItem.setIdPatient(newPCode);
            }
            peispatientfeeitemService.updateBatchById(peispatientfeeitems);

            //更新收费主表
            List<PeispatientChargeMain> chargeMains = peispatientChargeMainService.list(new LambdaQueryWrapper<PeispatientChargeMain>().eq(PeispatientChargeMain::getPatientcode, newPt.getPatientcode()));
            log.warn("9、去重操作更新的收费主表chargeMains：{}", JSONUtil.toJsonStr(chargeMains));
            for (PeispatientChargeMain chargeMain : chargeMains) {
                chargeMain.setModifydate(new Date());
                chargeMain.setPatientcode(newPCode);
            }
            peispatientChargeMainService.updateBatchById(chargeMains);
        }

        //删除其他体检者
        for (String delCode : delCodes) {
            if (Objects.nonNull(newPt) && newPt.getPatientcode().equals(delCode)) {
                continue;
            }
            log.warn("9.4、1111：{}", newPt);
            //删除体检者
            List<Peispatient> delPeispatients = peispatientMapper.selectList(new LambdaQueryWrapper<Peispatient>().eq(Peispatient::getPatientcode, delCode));
            log.warn("10、去重操作删除体检者peispatients：{}", JSONUtil.toJsonStr(delPeispatients));
            List<String> pIds = delPeispatients.stream().map(Peispatient::getId).collect(Collectors.toList());
            peispatientMapper.deleteBatchIds(pIds);

            //删除收费项目
            List<Peispatientfeeitem> delFeeItems = peispatientfeeitemService.list(new LambdaQueryWrapper<Peispatientfeeitem>().eq(Peispatientfeeitem::getIdPatient, delCode));
            log.warn("11、去重操作删除体检者收费项目feeItems：{}", JSONUtil.toJsonStr(delFeeItems));
            List<String> feeIds = delFeeItems.stream().map(Peispatientfeeitem::getId).collect(Collectors.toList());
            peispatientfeeitemService.removeBatchByIds(feeIds);

            //删除收费主表
            List<PeispatientChargeMain> delChargeMains = peispatientChargeMainService.list(new LambdaQueryWrapper<PeispatientChargeMain>().eq(PeispatientChargeMain::getPatientcode, delCode));
            log.warn("12、去重操作删除体检者主表delChargeMains：{}", JSONUtil.toJsonStr(delChargeMains));
            List<String> pcmIds = delChargeMains.stream().map(PeispatientChargeMain::getId).collect(Collectors.toList());
            peispatientChargeMainService.removeBatchByIds(pcmIds);

            //删除预约信息
            //TODO
        }
        return Boolean.TRUE;
    }

    /**
     * 根据订单号获取体检者列表
     *
     * @param params 查询条件
     * @return
     */
    @Override
    public IPage<OrderUserVo> getByDdh(PageParam<OrderUserVo> page, OrderUserParam params) {
        return peispatientMapper.getByDdh(page, params);
    }

    /**
     * 查询推送给第三方的体检者数据
     *
     * @param params
     * @return
     */
    @Override
    public void pushDataToCoo(RequestParam params) {
        String str = params.getParams();
        UserExamDataParam param = JSONUtil.toBean(str, UserExamDataParam.class);
        if (StringUtils.isBlank(param.getOrderId())){
            return;
        }

        List<UserExamDataDto> userExamDataList = new ArrayList<>();
        List<UserExamDataDto> peispatients = peispatientMapper.getPushData(param);
        log.info("查询推送给第三方的体检者数据.peispatients:{}", peispatients);
        int i = 1;
        //获取体检结果
        for(UserExamDataDto item : peispatients){
            //获取每个科室的检查结果和小结
            List<ExamItemDataDto> itemList = peispatientMapper.getSectionResult(item.getPatientcode());
            item.setExamtemList(itemList);
//            log.info("查询推送给第三方的体检者数据.itemList:{}", itemList);
            //获取总检结果
            List<SectionTotalDto> sectionTotals = peispatientMapper.getSectionTotal(item.getPatientcode());
            for (SectionTotalDto sectionTotal : sectionTotals) {
                if (sectionTotal.getExamType() == 0){
                    //健康总检
                    item.setHealthResult(mapperFacade.map(sectionTotal, HealthResultDto.class));
                }
                if (sectionTotal.getExamType() == 1){
                    //职业总检
                    item.setOccupResult(mapperFacade.map(sectionTotal, OccupResultDto.class));
                }
            }
//            log.info("查询推送给第三方的体检者数据.sectionTotals:{}", sectionTotals);

            userExamDataList.add(item);
            if (i % 10 == 0){
                log.info("合作单位开放接口.查询订单ID下的客户体检数据列表userExamDataList.size：{}", userExamDataList.size());
                Map<String, Object> result = new HashMap<>();
                //生成签名：业务方授权码+业务标识+请求标识，拼接字符串后进行MD5（32位大写）加密
                result.put("sign", MD5.encode(params.getAuthCode() + params.getBsFlag() + param.getFlag()).toUpperCase());
                result.put("records", JSONUtil.toJsonStr(userExamDataList));
                String post = HttpUtil.post(param.getCallBack(), result);
                log.info("推送结果：{}", post);
                userExamDataList = new ArrayList<>();
            }
            i++;
        }

    }


    @Override
    public long checkPatientcode(String patientcode, String phone) {
        return peispatientMapper.checkPatientcode(patientcode,phone);
    }

    public static void main(String[] args) {
        System.out.println(20%10);
    }
}

