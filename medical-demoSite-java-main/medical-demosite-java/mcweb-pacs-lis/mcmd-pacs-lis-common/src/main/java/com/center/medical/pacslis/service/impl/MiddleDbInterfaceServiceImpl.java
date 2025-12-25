package com.center.medical.pacslis.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.enums.ExamType;
import com.center.medical.bean.enums.SexType;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.bean.model.Peispatientfeeitem;
import com.center.medical.common.config.LisConfig;
import com.center.medical.common.config.LoadProperties;
import com.center.medical.common.constant.CacheConstants;
import com.center.medical.common.constant.Constants;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.core.domain.entity.SysDept;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.redis.RedisUtil;
import com.center.medical.data.bean.model.*;
import com.center.medical.data.dao.*;
import com.center.medical.dao.PeispatientMapper;
import com.center.medical.dao.PeispatientfeeitemMapper;
import com.center.medical.olddata.service.VCheckReqmainMysqlService;
import com.center.medical.pacslis.bean.dto.*;
import com.center.medical.pacslis.dao.MiddleDbInterfaceMapper;
import com.center.medical.pacslis.service.MiddleDbInterfaceService;
import com.center.medical.service.PeispatientfeeitemService;
import com.center.medical.system.dao.SysDeptMapper;
import com.center.medical.system.service.ISysConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.*;

/**
 * @author xhp
 * @since 2023-02-28 13:40
 */
@Slf4j
@Service("middleDbInterfaceService")
@RequiredArgsConstructor
public class MiddleDbInterfaceServiceImpl extends ServiceImpl<MiddleDbInterfaceMapper, Peispatient> implements MiddleDbInterfaceService {
    private final PeispatientMapper peispatientMapper;
    private final PeispatientfeeitemMapper peispatientfeeitemMapper;
    private final YblxMapper yblxMapper;
    private final SysDeptMapper sysDeptMapper;
    private final ItemsMapper itemsMapper;
    private final InspectChargeMapper inspectChargeMapper;
    private final BasexamltemMapper basexamltemMapper;
    private final ISysConfigService iSysConfigService;
    private final WsjgMapper wsjgMapper;
    private final SpecimenTypeMapper specimenTypeMapper;
    private final PeispatientfeeitemService peispatientfeeitemService;
    private final VCheckReqmainMysqlService vCheckReqmainMysqlService;
    @Autowired
    private LoadProperties loadProperties;

    @Override
    public void insert(String patientcodes) {
        // log.info("1开始插入中间库：{}", DateUtils.parseDateToStr("yyyy-MM-dd HH:mm:ss", new Date()));

//        ExecutorService executorService = Executors.newSingleThreadExecutor();
//        executorService.submit(() -> {
        //执行保存操作，线程池设置了一个核心线程和最大线程都为1的线程池，空闲时间为1200秒。如果在1200秒内没有任务提交，线程池会自动释放线程资源。当有新任务提交时，线程池会重新启动线程来处理任务。
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 6, 1200, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
        // 提交任务到线程池
        executor.submit(() -> {
            //可能会存在传多个体检号的情况
            String[] split = patientcodes.split(",");
            for (String patientcode : split) {
                // 调用接口插入中间库
                MiddleDbDto middleDbDto = getInsertMiddleDbData(patientcode);
                log.info(JSONUtil.toJsonStr(middleDbDto));
                if (middleDbDto.getMiddleDbItemDtos().size() != 0) {
                    String url = iSysConfigService.getDomain().getLisDomain() + "/open/api/middleDb/insert";
//                    String url =  "http://localhost:8092/open/api/middleDb/insert";
                    String param = JSONUtil.toJsonStr(middleDbDto);
                    log.info("请求地址：" + url);
                    String s = null;
                    try {
                        s = HttpUtil.post(url, param);
                    } catch (Exception e) {
                        // 请求插入中间库失败
                        log.error("2插入中间库失败{}，{}，{}，{}", url, JSONUtil.toJsonStr(e), patientcode, param);
                    }
                    log.info("3响应结果：" + s);
                    if (StringUtils.isBlank(s)) {
                        RedisUtil.set(CacheConstants.SEND_MIDDLE_DB_FAIL_KEY + patientcode, patientcode, 0);
                    } else {
                        R responseEntity = JSONUtil.toBean(s, R.class);
                        if (200 != responseEntity.getCode()) {
                            log.error("4插入中间库失败{}，{}，{}，{}", url, JSONUtil.toJsonStr(responseEntity.getMsg()), patientcode, param);
                            // 将失败记录存储到缓存中，等待数据重发
                            RedisUtil.set(CacheConstants.SEND_MIDDLE_DB_FAIL_KEY + patientcode, patientcode, 0);
                        } else {
                            if (StringUtils.isNotBlank(RedisUtil.get(CacheConstants.SEND_MIDDLE_DB_FAIL_KEY + patientcode))) {
                                //将失败记录缓存删除
                                RedisUtil.delByKeys(Arrays.asList(CacheConstants.SEND_MIDDLE_DB_FAIL_KEY + patientcode));
                            }
                            //修改插入标志
                            setInserted(middleDbDto);
                        }
                        // log.info("5插入中间库成功：{}", JSONUtil.toJsonStr(responseEntity.getData()));
                    }

                } else {
                    log.info("6没有收费项目，不插入中间库");
                }
                // log.info("7插入中间库完成，关闭异步任务：{}", DateUtils.parseDateToStr("yyyy-MM-dd HH:mm:ss", new Date()));
//                executorService.shutdown();
            }


        });
        // log.info("8插入中间库操作结束：{}", DateUtils.parseDateToStr("yyyy-MM-dd HH:mm:ss", new Date()));
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        int i = 0;
        int t = 8;
        // 创建线程池
        ExecutorService threadPool = Executors.newFixedThreadPool(5);
        do {
            for (int j = 0; j < t; j++) {
                System.out.println("异步任务开始执行：" + i + "---" + j);
                // 创建异步执行任务:
//                ExecutorService executorService = Executors.newSingleThreadExecutor();
                threadPool.submit(() -> {
                    System.out.println(Thread.currentThread() + " start,time->" + System.currentTimeMillis());
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                    }
                    System.out.println(Thread.currentThread() + " exit,time->" + System.currentTimeMillis());
                });
            }
        } while (1 == 1);

        // System.out.println("main thread exit,time->" + System.currentTimeMillis());
    }

    @Override
    public MiddleDbDto getInsertMiddleDbData(String patientcode) {
        Peispatient peispatient = peispatientMapper.selectOne(
                new QueryWrapper<Peispatient>()
                        .eq("patientcode", patientcode)
        );
        if (peispatient == null) {
            throw new ServiceException("体检号" + patientcode + "不存在！");
        }
        String patientcode8 = patientcode.length() <= 8 ? patientcode : patientcode.substring(patientcode.length() - 8);

        MiddleDbDto middleDbDto = new MiddleDbDto();

        //人员信息
        MiddleDbPatientDto middleDbPatientDto = new MiddleDbPatientDto();
        middleDbDto.setMiddleDbPatientDto(middleDbPatientDto);
        middleDbPatientDto.setIdcardNo(peispatient.getIdcardno());
        middleDbPatientDto.setPatientName(peispatient.getPatientname());
        middleDbPatientDto.setIdPatientclass(peispatient.getIdPatientclass() == null
                ? null
                : Integer.parseInt(peispatient.getIdPatientclass()));
        if (StrUtil.isNotEmpty(peispatient.getPatientarchiveno())) {
            middleDbPatientDto.setIdPatientArchive(Integer.valueOf(peispatient.getPatientarchiveno().substring(peispatient.getPatientarchiveno().length() - 8)));
        }
        middleDbPatientDto.setInputCode(peispatient.getInputCode());
        middleDbPatientDto.setSex(SexType.getName(peispatient.getIdSex()));
        if (peispatient.getBirthdate() != null) {
            middleDbPatientDto.setBirthDate(new Timestamp(peispatient.getBirthdate().getTime()));
        }
        middleDbPatientDto.setAge(peispatient.getAge() == null ? null : (int) Math.floor(Double.valueOf(peispatient.getAge())));
        middleDbPatientDto.setMarriage(peispatient.getMarriage());
        middleDbPatientDto.setResAddress(peispatient.getAddress());
        middleDbPatientDto.setPhone(peispatient.getPhone());
        middleDbPatientDto.setOrgName(peispatient.getOrgName());
        middleDbPatientDto.setDoctorReg(peispatient.getDoctorreg());
        ExamType examClass = ExamType.instance(peispatient.getIdExamclass());
        middleDbPatientDto.setExamTypeName(Objects.isNull(examClass) ? null : examClass.name());
        // middleDbPatientDto.setIdPatient(peispatient.getShortCode());
        middleDbPatientDto.setIdPatient(Integer.parseInt(patientcode8));//使用短号是9位的,检验科那可能合不上，所以使用8位的体检号
        middleDbPatientDto.setStrIdpatient(patientcode);
        middleDbPatientDto.setPatientCode(patientcode8);
        middleDbPatientDto.setDoctorOpen(peispatient.getDoctorapply());

        //接口类型
        List<MiddleDbPatientTransTargetDto> middleDbPatientTransTargetDtos = new ArrayList<>();
        middleDbDto.setMiddleDbPatientTransTargetDtos(middleDbPatientTransTargetDtos);
        List<MiddleDbYblxListDto> middleDbYblxListDtoList = baseMapper.selectYblxList(patientcode);
        for (MiddleDbYblxListDto middleDbYblxListDto : middleDbYblxListDtoList) {
            MiddleDbPatientTransTargetDto middleDbPatientTransTargetDto = new MiddleDbPatientTransTargetDto();
            middleDbPatientTransTargetDtos.add(middleDbPatientTransTargetDto);
            middleDbPatientTransTargetDto.setTransfterTarget(middleDbYblxListDto.getJklx());
            middleDbPatientTransTargetDto.setFeeItemList(middleDbYblxListDto.getName());
            middleDbPatientTransTargetDto.setIdLabType(middleDbYblxListDto.getIdLabtype());
            middleDbPatientTransTargetDto.setLabTypeName(middleDbYblxListDto.getLabtypeName());
        }

        //收费项目
        List<MiddleDbItemDto> middleDbItemDtos = new ArrayList<>();
        middleDbDto.setMiddleDbItemDtos(middleDbItemDtos);
        List<MiddleDbExamDto> middleDbExamDtos = new ArrayList<>();
        middleDbDto.setMiddleDbExamDtos(middleDbExamDtos);
        List<Peispatientfeeitem> peispatientfeeitems = peispatientfeeitemMapper.selectList(
                new QueryWrapper<Peispatientfeeitem>()
                        .eq("id_patient", patientcode)
                        .eq("change_item", 0)
                        .isNotNull("id_ks")
        );
        for (Peispatientfeeitem peispatientfeeitem : peispatientfeeitems) {
            Items items = itemsMapper.selectById(peispatientfeeitem.getIdExamfeeitem());
            if (items == null) continue;
            if(StrUtil.isEmpty(items.getIdDepart()))continue;
            SysDept sysDept = sysDeptMapper.getByDeptNo(items.getIdDepart());
            if (sysDept==null || !"1".equals(sysDept.getIsFunction())) continue;

            MiddleDbItemDto middleDbItemDto = new MiddleDbItemDto();
            middleDbItemDtos.add(middleDbItemDto);
            middleDbItemDto.setFeeitemId(peispatientfeeitem.getId());
            //检验科除了尿常规
            if (
                    !(
                            (items.getExamfeeitemName() != null && items.getExamfeeitemName().indexOf("尿常规") != -1)
                                    || (sysDept.getSjbggs() == null || sysDept.getSjbggs().intValue() != 1)
                    )
            ) {
                middleDbItemDto.setHisExecDepartCode("SEND");
            }
            middleDbItemDto.setIdExamFeeItem(items.getExamfeeitemid());
            middleDbItemDto.setExamFeeItemName(StringUtils.truncateForSqlServerVarchar50(items.getExamfeeitemName()));//数据库里是varchar50,按GBK字节长度截断字符串
            middleDbItemDto.setUnitPrice(peispatientfeeitem.getPrice());
            middleDbItemDto.setFactPrice(peispatientfeeitem.getFactprice());
            middleDbItemDto.setIdLabType(StrUtil.isEmpty(items.getIdLabtype()) ? 0 : Integer.parseInt(items.getIdLabtype()));
            if (StrUtil.isNotEmpty(items.getIdLabtype())) {
                Yblx yblx = yblxMapper.selectById(items.getIdLabtype());
                if (yblx != null) {
                    middleDbItemDto.setLabTypeName(yblx.getName());
                }
            }
            middleDbItemDto.setExamFeeItemCode(items.getExamfeeitemCode());
            middleDbItemDto.setDepartName(sysDept.getDeptName());
            middleDbItemDto.setTransfterTarget(sysDept.getJklx());
            middleDbItemDto.setIdDepart(Integer.parseInt(sysDept.getKsh()));
            String idCooporg = items.getIdCooporg();
            if (StrUtil.isNotEmpty(idCooporg)) {
                Wsjg wsjg = wsjgMapper.getInfoById(idCooporg);
                if (wsjg != null) {
                    middleDbItemDto.setFeeTypeCode(idCooporg);
                    middleDbItemDto.setFeeTypeName(wsjg.getName());
                }
            }
            String specimenTypeCode = items.getSpecimenTypeCode();
            if (StrUtil.isNotEmpty(specimenTypeCode)) {
                SpecimenType specimenType = specimenTypeMapper.selectOne(
                        new QueryWrapper<SpecimenType>()
                                .eq("code", specimenTypeCode)
                                .eq("status", 1)
                );
                if (specimenType != null) {
                    middleDbItemDto.setLabTypeCode(specimenType.getSpecimenName());
                }
            }

            //需要插入IntPatientTransFeeItem
            middleDbItemDto.setXsdyfl(items.getXsdyfl());
            middleDbItemDto.setFeeChargeTime(peispatientfeeitem.getFeechargetime() == null ? null : new Timestamp(peispatientfeeitem.getFeechargetime().getTime()));

            //检查小项
            List<InspectCharge> inspectCharges = inspectChargeMapper.selectList(
                    new QueryWrapper<InspectCharge>()
                            .eq("charge_id", items.getId())
                            .eq("is_delete", 0)
            );
            for (InspectCharge inspectCharge : inspectCharges) {
                Basexamltem basexamltem = basexamltemMapper.selectById(inspectCharge.getInspectId());
                if (basexamltem == null) continue;
                MiddleDbExamDto middleDbExamDto = new MiddleDbExamDto();
                middleDbExamDtos.add(middleDbExamDto);
                middleDbExamDto.setDepartName(middleDbItemDto.getDepartName());
                middleDbExamDto.setTransfterTarget(middleDbItemDto.getTransfterTarget());
                middleDbExamDto.setIdDepart(middleDbItemDto.getIdDepart());
                middleDbExamDto.setExamItemName(basexamltem.getExamitemName());
                middleDbExamDto.setExamItemCode(basexamltem.getInterfaceCode());
                middleDbExamDto.setIdExamItem(NumberUtil.isInteger(basexamltem.getId()) ? Integer.parseInt(basexamltem.getId()) : null);
            }
        }

        //lis配置
        LisConfig lisConfig=iSysConfigService.getSysConfigObject(Constants.LIS_CONFIG, LisConfig.class);
        middleDbDto.setLisConfig(lisConfig);

        return middleDbDto;
    }

    @Transactional
    @Override
    public void setInserted(MiddleDbDto middleDbDto) {
        String patientcode = middleDbDto.getMiddleDbPatientDto().getStrIdpatient();
        Peispatient peispatient = peispatientMapper.selectOne(
                new QueryWrapper<Peispatient>()
                        .eq("patientcode", patientcode)
        );
        peispatient.setFSettlenone(2);
        peispatientMapper.updateById(peispatient);

        List<MiddleDbItemDto> middleDbItemDtos = middleDbDto.getMiddleDbItemDtos();
        List<String> feeitemIds = new ArrayList<>();
        for (MiddleDbItemDto middleDbItemDto : middleDbItemDtos) {
            //耗时太长
            // Peispatientfeeitem peispatientfeeitem=peispatientfeeitemMapper.selectById(middleDbItemDto.getFeeitemId());
            // peispatientfeeitem.setFLabsendtolis(1);
            // peispatientfeeitemMapper.updateById(peispatientfeeitem);
            feeitemIds.add(middleDbItemDto.getFeeitemId());
        }
        if (CollectionUtil.isNotEmpty(feeitemIds)) {
            List<Peispatientfeeitem> peispatientfeeitems = peispatientfeeitemMapper.selectList(
                    new QueryWrapper<Peispatientfeeitem>()
                            .in("id", feeitemIds)
            );
            for (Peispatientfeeitem peispatientfeeitem : peispatientfeeitems) {
                peispatientfeeitem.setFLabsendtolis(1);
            }
            peispatientfeeitemService.updateBatchById(peispatientfeeitems);
        }

    }



    @Transactional
    public void setState(String patientcode) {
        Peispatient peispatient = peispatientMapper.selectOne(
                new QueryWrapper<Peispatient>()
                        .eq("patientcode", patientcode)
        );
        peispatient.setFSettlenone(2);
        peispatientMapper.updateById(peispatient);

        //功能科室的项目才已发
        List<String> functionKsIds = sysDeptMapper.getFunctionKsIds(1);
        Peispatientfeeitem peispatientfeeitem = new Peispatientfeeitem();
        peispatientfeeitem.setFLabsendtolis(1);
        peispatientfeeitemService.update(peispatientfeeitem,new LambdaQueryWrapper<Peispatientfeeitem>()
                .eq(Peispatientfeeitem::getIdPatient, patientcode)
                .in(Peispatientfeeitem::getIdKs,functionKsIds));
    }
}
