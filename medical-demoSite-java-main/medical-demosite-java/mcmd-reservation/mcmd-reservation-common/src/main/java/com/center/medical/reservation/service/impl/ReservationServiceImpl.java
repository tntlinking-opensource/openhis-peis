package com.center.medical.reservation.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdcardUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.reservation.bean.model.*;
import com.center.medical.reservation.bean.param.*;
import com.center.medical.appadmin.service.AppointmentService;
import com.center.medical.appadmin.service.IndexImgService;
import com.center.medical.bean.enums.ReservationStatus;
import com.center.medical.bean.event.AppointmentSMSEvent;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.bean.param.AppointmentSMSParam;
import com.center.medical.common.config.AppointmentSmsConfig;
import com.center.medical.common.config.LoadProperties;
import com.center.medical.common.constant.Constants;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.CodeUtil;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.reservation.bean.dto.RGroupData;
import com.center.medical.reservation.bean.dto.ReservationData;
import com.center.medical.reservation.bean.model.*;
import com.center.medical.reservation.bean.param.*;
import com.center.medical.reservation.bean.vo.ReservationVo;
import com.center.medical.reservation.bean.vo.VipExportVo;
import com.center.medical.reservation.dao.ReservationMapper;
import com.center.medical.reservation.service.ReservationGroupTimeService;
import com.center.medical.reservation.service.ReservationNotifyService;
import com.center.medical.reservation.service.ReservationService;
import com.center.medical.reservation.service.ReservationSettingService;
import com.center.medical.service.PeispatientService;
import com.center.medical.system.service.ISysBranchService;
import com.center.medical.system.service.ISysConfigService;
import com.center.medical.workflow.service.WorkflowCaseService;
import com.center.medical.workflow.service.WorkflowService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 体检预约记录(Reservation)表服务实现类
 *
 * @author ay
 * @since 2023-03-18 08:54:14
 */
@Slf4j
@Service("reservationService")
@RequiredArgsConstructor
public class ReservationServiceImpl extends ServiceImpl<ReservationMapper, Reservation> implements ReservationService {

    private final ReservationMapper reservationMapper;
    private final ReservationSettingService reservationSettingService;
    private final LoadProperties loadProperties;
    private final ISysBranchService iSysBranchService;
    private final MapperFacade mapperFacade;
    private final Snowflake snowflake;
    private final ReservationNotifyService reservationNotifyService;
    private final ISysConfigService iSysConfigService;
    private final ApplicationEventPublisher applicationEventPublisher;

    private final PeispatientService peispatientService;
    private final WorkflowService workflowService;
    private final WorkflowCaseService workflowCaseService;

    private final AppointmentService appointmentService;
    private final IndexImgService indexImgService;
    private final ReservationGroupTimeService reservationGroupTimeService;

    /**
     * 分页查询[体检预约记录]列表
     *
     * @param page  分页参数
     * @param param Reservation查询参数
     * @return 分页数据
     */
    @Override
    public IPage<ReservationVo> getList(PageParam<Reservation> page, ReservationParam param) {
//        if (!SecurityUtils.hasRole(RoleAuthName.JUECE_MANAGE)) {
//            param.setXsjl(SecurityUtils.getUserNo());
//        }
        return reservationMapper.getList(page, param);
    }

    /**
     * 根据预约号获取记录详情
     *
     * @param reservationNo 预约号
     * @return 详情信息
     */
    @Override
    public ReservationVo getInfoByNo(String reservationNo) {
        return reservationMapper.getInfoByNo(reservationNo);
    }

    /**
     * 新增预约
     *
     * @param reservation 实体对象
     * @return 预约号
     */
    @Override
    @Transactional(rollbackFor = Exception.class,timeout = 30)
    public R<String> saOrUp(Reservation reservation) {
        long startTime = System.currentTimeMillis(); // 计时开始
//        SysUser admin = SecurityUtils.getLoginUser().getUser();
        if (Objects.nonNull(reservation) && StringUtils.isBlank(reservation.getReservationNo())) {
            // 新增预约记录
            // 判断是否存在相同的已预约中的用户：身份证为唯一标准
            LambdaQueryWrapper<Reservation> queryWrapper = new LambdaQueryWrapper<Reservation>()
                    .in(Reservation::getStatus, Arrays.asList(0, 1, 2, 3))
                    .eq(Reservation::getIsDelete,0);

            if (StringUtils.isNotBlank(reservation.getPatientcode())){
                //根据后8位模糊查询
                queryWrapper.likeLeft(Reservation::getPatientcode, reservation.getPatientcode().substring(reservation.getPatientcode().length() - 8));
            }else if (StringUtils.isNotBlank(reservation.getBizId())){
                //来自合作第三方的预约
                queryWrapper.eq(Reservation::getBizId, reservation.getBizId());
            }else {
                throw new ServiceException("参数不正确！", 409);
            }
            Long count = reservationMapper.selectCount(queryWrapper);
            if (count > 0) {
                throw new ServiceException("预约失败，你已预约过了，不能重复预约！", 409);
            }

            // 判断选择的时间段是否可以预约
            if (StringUtils.isNotEmpty(reservation.getTimeId())) {
                // 先查询设置信息
                ReservationSetting setting = reservationSettingService.getInfoById(reservation.getTimeId());
                if (Objects.isNull(setting)) {
                    throw new ServiceException("预约设置不存在！", 409);
                }
                
                // 使用原子性更新，避免锁竞争
                try {
                    Integer updateResult = reservationSettingService.updateAbleNumWithRetry(reservation.getTimeId(), -1);
                    if (updateResult <= 0) {
                        throw new ServiceException("预约失败，今天该时间段可预约人数已满， 请选择其他时间段！", 409);
                    }
                } catch (ServiceException e) {
                    throw e;
                } catch (Exception e) {
                    log.error("更新可预约人数失败: {}", e.getMessage());
                    throw new ServiceException("预约失败，系统繁忙请稍后重试！", 409);
                }
                
                //前端传过来的好像有问题，在这里重新设置下
                reservation.setLevelId(setting.getLevelId());
                reservation.setLevelName(setting.getLevelId() == 1 ? "普通会员" : setting.getLevelId() == 2 ? "vip" : "vvip");
            }

            //TODO wait 判断预约类型是否满足条件：消费金额/是否有申请记录

            //生成预约号
            Long sort = 0L;
            String reservationNo = null;
            do {
                reservationNo = CodeUtil.getReservationNo(iSysBranchService.getBranchFlag(null) + loadProperties.online);
                LambdaQueryWrapper<Reservation> lambdaQueryWrapper = new LambdaQueryWrapper<Reservation>()
                        .eq(Reservation::getReservationNo, reservationNo)
                        .eq(Reservation::getBranchId, reservation.getBranchId());
                if (ObjectUtil.isNotEmpty(reservation.getReservationDate())) {
                    lambdaQueryWrapper.ge(Reservation::getReservationDate, DateUtil.beginOfDay(reservation.getReservationDate()))
                            .le(Reservation::getReservationDate, DateUtil.endOfDay(reservation.getReservationDate()));
                }
                sort = reservationMapper.selectCount(lambdaQueryWrapper);
            } while (sort > 0);
            log.info("生成预约号:{}", reservationNo);
            reservation.setReservationNo(reservationNo);
            reservation.setStatus(ReservationStatus.SUCCESS.value());
            reservationMapper.insert(reservation);

        } else {
            // 更新预约记录信息
            Reservation reservationDb = reservationMapper.selectOne(new LambdaQueryWrapper<Reservation>()
                    .eq(Reservation::getReservationNo, reservation.getReservationNo())
                    .eq(Reservation::getId, reservation.getId()));
            if (Objects.isNull(reservationDb)) {
                throw new ServiceException("预约号不存在或者已被删除！");
            }
            //查看预约timeId是否变化
            if (Objects.isNull(reservation.getTimeId()) || reservationDb.getTimeId() == reservation.getTimeId()) {
                //timeID没变化
                if (reservation.getStatus() == -1) {
                    //恢复原来预约类型可预约数量
                    reservationSettingService.updateAbleNumWithRetry(reservationDb.getTimeId(), 1);
                }
            } else {
                //timeID变化了
                ReservationSetting setting = reservationSettingService.getInfoById(reservation.getTimeId());
                if (Objects.isNull(setting)) {
                    throw new ServiceException("预约设置不存在！", 409);
                }

                if (Objects.nonNull(reservationDb.getTimeId())) {
                    //恢复原来预约类型可预约数量
                    try {
                        reservationSettingService.updateAbleNumWithRetry(reservationDb.getTimeId(), 1);
                    } catch (Exception e) {
                        log.warn("恢复原时间段可预约人数失败: {}", e.getMessage());
                    }
                }

                // 扣除变化后的timeId的可预约人数
                try {
                    Integer updateResult = reservationSettingService.updateAbleNumWithRetry(reservation.getTimeId(), -1);
                    if (updateResult <= 0) {
                        throw new ServiceException("预约失败，今天该时间段可预约人数已满， 请选择其他时间段！", 409);
                    }
                } catch (ServiceException e) {
                    throw e;
                } catch (Exception e) {
                    log.error("更新可预约人数失败: {}", e.getMessage());
                    throw new ServiceException("预约失败，系统繁忙请稍后重试！", 409);
                }
            }

//            reservationDb.setStatus(reservation.getStatus());
//            reservationDb.setFailReason(reservation.getFailReason());
//            reservationDb.setModifydate(new Date());
//            reservationDb.setModifier(admin.getUserNo());
            reservationMapper.updateById(reservation);
        }
        //如果存在体检号，还需要更新体检号的预约时间
        if (StringUtils.isNotBlank(reservation.getPatientcode())){
            //根据短号查询
            Peispatient peispatient = peispatientService.getOne(new LambdaQueryWrapper<Peispatient>()
                    .eq(Peispatient::getShortCode, reservation.getPatientcode().substring(reservation.getPatientcode().length() - 8)));
            if (ObjectUtils.isNotEmpty(peispatient)){
                peispatient.setFIsforreserve(1);
                peispatient.setDateguidancereturned(reservation.getReservationDate());
                peispatientService.updateById(peispatient);
            }
        }
        long endTime = System.currentTimeMillis(); // 计时结束
        log.info("预约操作耗时: {}毫秒", (endTime - startTime)); // 输出耗时
        // TODO wait 同步至线上，同时推送通知第三方来源预约状态已改变

        return R.ok(reservation.getReservationNo());
    }

    /**
     * 删除预约记录
     *
     * @param ids 删除对象id集合
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean rmByIds(List<String> ids) {
        //已预约成功的不能删除： status==2
        Long count = reservationMapper.selectCount(new LambdaQueryWrapper<Reservation>().eq(Reservation::getStatus, 2)
                .in(Reservation::getId, ids).eq(Reservation::getIsDelete, 0));
        if (count > 0) {
            throw new ServiceException("删除失败：删除对象包含预约成功尚未结束的记录！");
        }

        //恢复预约人数
        ids.forEach(id -> {
            ReservationVo reservationDb = reservationMapper.getInfoById(id);
            reservationSettingService.updateAbleNum(reservationDb.getTimeId(), -1);
        });

        //删除预约记录
        Reservation reservation = new Reservation();
        reservation.setIsDelete(1);
        reservation.setModifydate(new Date());
        reservation.setModifier(SecurityUtils.getUserNo());
        reservationMapper.update(reservation, new LambdaUpdateWrapper<Reservation>()
                .in(Reservation::getId, ids));

        return Boolean.TRUE;
    }

    /**
     * 检查是否可以预约
     *
     * @param numorgresv    订单号
     * @param patientcode   体检号（团检才有）
     * @param fUsecodehiden 订单类型：0.个检 1.团检
     * @return
     */
    @Override
    public String check(String numorgresv, String patientcode, Integer fUsecodehiden) {
        Reservation reservation;
        //根据体检号查询是否已有预约
        if (fUsecodehiden == 1) {
            //团检：根据体检号判断是否预约过
            reservation = reservationMapper.selectOne(new LambdaQueryWrapper<Reservation>()
                    .eq(Reservation::getPatientcode, patientcode)
                    .eq(Reservation::getStatus, ReservationStatus.SUCCESS.value())
                    .eq(Reservation::getIsDelete, 0));
        } else {
            //根据康淘订单编号判断是否预约过
            reservation = reservationMapper.selectOne(new LambdaQueryWrapper<Reservation>()
                    .eq(Reservation::getBizOrderNum, numorgresv)
                    .eq(Reservation::getStatus, ReservationStatus.SUCCESS.value())
                    .eq(Reservation::getIsDelete, 0));
        }
        return Objects.isNull(reservation) ? null : reservation.getReservationNo();
    }


    /**
     * 根据id查体检预约记录详情
     *
     * @param id
     * @return
     */
    @Override
    public ReservationVo getInfoById(String id) {
        return reservationMapper.getInfoById(id);
    }


    /**
     * 批量修改日期及人员类型
     *
     * @param param
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean batchModify(BatchModifyParam param) {
        List<Reservation> reservationList = new ArrayList<>();
        // 修改体检预约设置可预约和已预约的人
        ReservationSettingCondition reservationSettingCondition = new ReservationSettingCondition(DateUtil.beginOfDay(param.getReservationDate())
                , param.getLevelId(), param.getBranchId(), param.getIds().size());
        List<Map<String, Object>> timeIdList = reservationSettingService.updateAbleNumWithoutId(reservationSettingCondition);
        if (CollectionUtil.isNotEmpty(timeIdList)) {
            timeIdList.forEach(itemId -> {
                Long count = Long.valueOf(itemId.get("value").toString());
                for (int i = 0; i < count; i++) {
                    //先取消之前预约的
                    String id = param.getIds().get(i);
                    ReservationVo reservationDb = reservationMapper.getInfoById(id);
                    if (StringUtils.isNotEmpty(reservationDb.getTimeId())){
                        reservationSettingService.updateAbleNum(reservationDb.getTimeId(), 1);
                    }
                    //再预约新的
                    Reservation reservation = new Reservation();
                    reservation.setId(id);
                    reservation.setReservationDate(param.getReservationDate());
                    reservation.setLevelId(param.getLevelId());
                    reservation.setLevelName(param.getLevelName());
                    reservation.setBranchId(param.getBranchId());
                    reservation.setTimeId((String) itemId.get("key"));
                    reservationList.add(reservation);
                }
            });
            if (CollectionUtil.isNotEmpty(reservationList)) {
                updateBatchById(reservationList);
            } else {
                throw new ServiceException("预约失败，无法修改预约记录！");
            }
        } else {
            throw new ServiceException("预约失败，" + DateUtil.format(param.getReservationDate(), "yyyy-MM-dd") + "当天可预约人数不足！");
        }
        return updateBatchById(reservationList);
    }

    /**
     * 新增团体预约记录
     *
     * @param reservationGroup
     * @return
     */
    @Override
    public Boolean addGroupReservation(ReservationGroup reservationGroup) {
        // 修改体检预约设置可预约和已预约的人
        ReservationSettingCondition reservationSettingCondition = new ReservationSettingCondition(DateUtil.beginOfDay(reservationGroup.getReservationDate())
                , reservationGroup.getLevelId(), reservationGroup.getBranchId(), reservationGroup.getCountAm());
        List<Map<String, Object>> timeIdList = reservationSettingService.updateAbleNumWithoutId(reservationSettingCondition);
        if (CollectionUtil.isNotEmpty(timeIdList)) {
            //预约详情，团体排检不计算真实预约位置
            List<ReservationGroupTime> reservationGroupTimeList = new ArrayList<>();
            timeIdList.forEach(itemId -> {
                ReservationGroupTime reservationGroupTime = new ReservationGroupTime();
                reservationGroupTime.setGroupId(reservationGroup.getId());
                reservationGroupTime.setTimeId((String) itemId.get("key"));
                reservationGroupTime.setCount(Integer.valueOf(itemId.get("value").toString()));
                reservationGroupTimeList.add(reservationGroupTime);
            });
            if (CollectionUtil.isNotEmpty(reservationGroupTimeList)) {
                reservationGroupTimeService.saveBatch(reservationGroupTimeList);
            } else {
                throw new ServiceException("预约失败，添加无法预约记录！");
            }
        } else {
            throw new ServiceException("预约失败，" + DateUtil.format(reservationGroup.getReservationDate(), "yyyy-MM-dd") + "当天可预约人数不足！");
        }
        return Boolean.TRUE;
    }

    /**
     * 更新预约记录的状态
     *
     * @param reservation
     * @param flag        是否需要发送通知请求：0.不需要 1.需要
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateStatus(Reservation reservation, Boolean flag) {
        reservationMapper.updateById(reservation);
        if (flag) {
            if (StringUtils.isNotBlank(reservation.getSystemId()) && (!reservation.getSystemId().equals("0"))) {
                //判断是否有存在未通知的
                Long count = reservationNotifyService.count(new LambdaQueryWrapper<ReservationNotify>()
                        .eq(ReservationNotify::getBizId, reservation.getId()).eq(ReservationNotify::getStatus, 0)
                        .eq(ReservationNotify::getSystemId, reservation.getSystemId()));
                if (count == 0) {
                    //新增预约通知记录
                    ReservationNotify notify = new ReservationNotify();
                    notify.setStatus(0);
                    notify.setBizId(reservation.getId());
                    notify.setSystemId(reservation.getSystemId());
                    reservationNotifyService.save(notify);
                }
            }

        }
        return Boolean.TRUE;
    }

    /**
     * 获取导出预约数据
     *
     * @param param
     */
    @Override
    public List<ReservationData> getExportData(ReservationParam param) {
        List<ReservationData> exportData = reservationMapper.getExportData(param);
        exportData.forEach(item -> {
            //计算年龄
            if (StringUtils.isNotEmpty(item.getIdcard())){
                //身份证合法才计算年龄
                if (IdcardUtil.isValidCard(item.getIdcard())){
                    item.setAge(IdcardUtil.getAgeByIdCard(item.getIdcard()));
                }
            }
        });
        return exportData;
    }

    /**
     * 团体预约信息按时间段导出数据
     *
     * @param param
     * @return
     */
    @Override
    public List<RGroupData> getGroupExportData(RgListParam param) {
        return reservationMapper.getGroupExportData(param);
    }


    /**
     * 根据预约号或体检号获取预约详情
     *
     * @param param
     * @return
     */
    @Override
    public Reservation getReservation(GetReservationParam param) {
        return reservationMapper.getReservation(param);
    }

    /**
     * 完成预约并发送预约短信
     *
     * @param reservation
     * @return
     */
    @Override
    public R<String> saOrUpReservation(Reservation reservation) {

//        //判断是否开启审批流
//        Workflow workflow = workflowService.isOpen(reservation.getBranchId(), WorkflowType.OVER_RESERVATION.getCode());
//        if (Objects.nonNull(workflow)) {
//            //开启的话,需要判断是否达到会员额度（VIP800、贵宾1500）
//            if ((reservation.getLevelId() == 2 && reservation.getMoneyamount() < 800.0)
//                    || (reservation.getLevelId() == 3 && reservation.getMoneyamount() < 1500.0)){
//                //走预约审核流
//                WorkflowCase workflowCase = new WorkflowCase();
//                workflowCase.setCaseName(reservation.getPatientcode()+"预约审核");
//                workflowCase.setBizId(reservation.getId());
//                workflowCase.setFlowId(workflow.getId());
//                workflowCase.setFzxid(reservation.getBranchId());
//                workflowCase.setRemark("");
//                workflowCase.setTypeFlag(workflow.getTypeFlag());
//                workflowCaseService.saOrUp(workflowCase);
//
//                //预约状态变为待审核
//                reservation.setStatus(0);
//            }
//        }

        saOrUp(reservation);

        // TODO: 2024/4/18 添加小程序的预约信息
//        Appointment appointment = mapperFacade.map(reservation, Appointment.class);
//        appointment.setStatus(2);
//        appointment.setId(snowflake.nextIdStr());
//        appointmentService.saveAppointment(appointment);

        //待预约状态才发送短信
        if (reservation.getStatus() == 2){
            // 发送预约短信
            AppointmentSmsConfig config = iSysConfigService.getSysConfigObject(Constants.APPOINTMENT_SMS_CONFIG, AppointmentSmsConfig.class);
            if (ObjectUtils.isNotEmpty(config) && config.getIsOpen()) {
                AppointmentSMSParam appointmentSMSParam = new AppointmentSMSParam(reservation.getPatientcode(), reservation.getLevelId().toString(),
                        reservation.getRealName(), reservation.getReservationDate(), reservation.getMobile());
                applicationEventPublisher.publishEvent(new AppointmentSMSEvent(appointmentSMSParam));
            }
        }
        return R.ok(reservation.getReservationNo());
    }

    /**
     * vip及贵宾导出
     * @param param
     * @return
     */
    @Override
    public List<VipExportVo> vipExport(VipExportParam param) {
        List<VipExportVo> list = reservationMapper.vipExport(param);
        int i = 1;
        for (VipExportVo vo : list) {
            vo.setRownum(i++);
        }
        return list;
    }
}

