package com.center.medical.reservation.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.dto.GroupOrderDto;
import com.center.medical.bean.dto.SysBranchDto;
import com.center.medical.bean.enums.ReservationStatus;
import com.center.medical.bean.model.Branch;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.common.config.LoadProperties;
import com.center.medical.common.constant.HttpStatus;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.CodeUtil;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.reservation.bean.dto.AppointmentAvailableDto;
import com.center.medical.reservation.bean.dto.AppointmentAvailableList;
import com.center.medical.reservation.bean.dto.AppointmentDto;
import com.center.medical.reservation.bean.dto.ReservationTimeDto;
import com.center.medical.reservation.bean.model.Reservation;
import com.center.medical.reservation.bean.model.ReservationSetting;
import com.center.medical.reservation.bean.param.AppointmentAvailableParam;
import com.center.medical.reservation.bean.param.ReservationCancelParam;
import com.center.medical.reservation.bean.param.ReservationSettingParam;
import com.center.medical.reservation.bean.vo.ReservationDateVo;
import com.center.medical.reservation.dao.ReservationOpenApiMapper;
import com.center.medical.reservation.service.ReservationOpenApiService;
import com.center.medical.reservation.service.ReservationService;
import com.center.medical.reservation.service.ReservationSettingService;
import com.center.medical.service.PeispatientService;
import com.center.medical.system.service.BranchService;
import com.center.medical.system.service.ISysBranchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 体检预约外部服务实现类
 *
 * @author ay
 * @since 2023-03-18 08:54:14
 */
@Slf4j
@Service("reservationOpenApiService")
@RequiredArgsConstructor
public class ReservationOpenApiServiceImpl extends ServiceImpl<ReservationOpenApiMapper, Reservation> implements ReservationOpenApiService {

    private final ReservationOpenApiMapper reservationOpenApiMapper;
    private final ReservationSettingService reservationSettingService;
    private final MapperFacade mapperFacade;
    private final BranchService branchService;
    private final ISysBranchService iSysBranchService;
    private final LoadProperties loadProperties;
    private final Snowflake snowflake;
    private final ReservationService reservationService;
    private final PeispatientService peispatientService;


    /**
     * 根据手机号获取我的待预约团体订单列表
     *
     * @param phone
     * @return
     */
    @Override
    public List<GroupOrderDto> getGroupOrderList(String phone) {
        List<GroupOrderDto> groupOrderList = reservationOpenApiMapper.getGroupOrderList(phone);
        for (GroupOrderDto item : groupOrderList) {
            if (StringUtils.isNotBlank(item.getFzxid())) {
                //获取分中心列表
                String[] branchList = item.getFzxid().split(",");
                List<Branch> list = branchService.list(new LambdaQueryWrapper<Branch>().select(Branch::getBranchId, Branch::getFzx, Branch::getId, Branch::getJm).in(Branch::getBranchId, branchList).eq(Branch::getIsDelete, 0));
                if (CollectionUtil.isNotEmpty(list)) {
                    item.setBranchList(mapperFacade.mapAsList(list, SysBranchDto.class));
                }
            }
            //TODO 判断可以预约的类型
            //是否存在申请的升级类型
            if (Objects.isNull(item.getLevelId())) {
                item.setLevelId(1);
            }
            //按照价格判断类型
        }
        log.info("根据手机号获取我的待预约团体订单列表groupOrderList:{}", groupOrderList);
        return groupOrderList;
    }

    /**
     * 获取可预约时间段列表
     *
     * @param param 筛选参数
     * @return
     */
    @Override
    public List<AppointmentAvailableList> getAvailableNums(AppointmentAvailableParam param) {
        ReservationSettingParam settingParam = mapperFacade.map(param, ReservationSettingParam.class);
        List<ReservationSetting> list = reservationSettingService.getList(settingParam);

        // 转换预约设置列表为预约时间段列表
        List<AppointmentAvailableList> appointmentList = list.stream().map(item -> {
            AppointmentAvailableDto itemDto = mapperFacade.map(item, AppointmentAvailableDto.class);
            itemDto.setTimeStr(item.getStartTime() + " - " + item.getEndTime());

            AppointmentAvailableList listItem = new AppointmentAvailableList();
            listItem.setTimeStr(itemDto.getTimeStr());
            listItem.setItemDto(Collections.singletonList(itemDto));

            return listItem;
        }).collect(Collectors.toList());
        log.info("获取可预约时间段列表1:{}、{}", list, appointmentList);
        return appointmentList;
    }

    /**
     * 提交预约申请
     *
     * @param data
     * @return 返回值为预约号
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String apply(AppointmentDto data) {
        Reservation reservation = mapperFacade.map(data, Reservation.class);
        reservation.setBizId(String.valueOf(data.getId()));
        
        // 判断选择的时间段是否可以预约
        ReservationSetting setting = reservationSettingService.getInfoById(reservation.getTimeId());
        if (Objects.isNull(setting)) {
            throw new ServiceException("预约设置不存在！", 409);
        }
        
        // 使用原子性更新，避免锁竞争
        if (reservationSettingService.updateAbleNumWithRetry(reservation.getTimeId(), -1) <= 0) {
            throw new ServiceException("预约失败，今天该时间段可预约人数已满， 请选择其他时间段！", 409);
        }

        // 设置预约信息
        reservation.setBizOrderNum(data.getNumorgresv());
        reservation.setTjtcmc(data.getComboName());
        reservation.setStatus(ReservationStatus.SUCCESS.value());
        reservation.setReservationDate(setting.getReservationDate());
        reservation.setLevelId(setting.getLevelId());
        reservation.setLevelName(setting.getLevelName());
        
        // 生成预约号
        String reservationNo = generateReservationNo(data.getBranchId(), reservation);
        reservation.setReservationNo(reservationNo);
        reservation.setCreatedate(new Date());
        reservation.setId(snowflake.nextIdStr());
        
        // 插入预约记录
        reservationOpenApiMapper.insert(reservation);
        log.info("接收外部第三方预约生成的预约号：{}", reservationNo);
        
        return reservationNo;
    }
    
    /**
     * 生成预约号
     */
    private String generateReservationNo(String branchId, Reservation reservation) {
        long sort = 0;
        String reservationNo;
        do {
            reservationNo = CodeUtil.getReservationNo(iSysBranchService.getBranchFlag(branchId) + loadProperties.online);
            sort = reservationOpenApiMapper.selectCount(new LambdaQueryWrapper<Reservation>()
                    .ge(Reservation::getReservationDate, DateUtil.beginOfDay(reservation.getReservationDate()))
                    .le(Reservation::getReservationDate, DateUtil.endOfDay(reservation.getReservationDate()))
                    .eq(Reservation::getReservationNo, reservationNo)
                    .eq(Reservation::getBranchId, reservation.getBranchId()));
        } while (sort > 0);
        return reservationNo;
    }

    /**
     * 预约取消
     *
     * @param param 预约取消参数
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<String> cancel(ReservationCancelParam param) {
        if (Objects.isNull(param) || Objects.isNull(param.getFUsecodehiden())
                || org.apache.commons.lang3.StringUtils.isBlank(param.getReservationNo())
                || org.apache.commons.lang3.StringUtils.isBlank(param.getPcodeOrOrderId())
                || org.apache.commons.lang3.StringUtils.isBlank(param.getSystemId())
        ) {
            return R.fail(HttpStatus.BAD_REQUEST, "参数有误！");
        }
        //判断是否存在预约记录
        LambdaQueryWrapper<Reservation> wrapper = new LambdaQueryWrapper<Reservation>()
                .eq(Reservation::getReservationNo, param.getReservationNo())
                .eq(Reservation::getFUsecodehiden, param.getFUsecodehiden())
                .eq(Reservation::getStatus, ReservationStatus.SUCCESS.value())
                .in(Reservation::getSystemId, param.getSystemId(),"0");//销售给他约的是0，但是用小程序传的不是0，所以要加上这个条件
        if (param.getFUsecodehiden() == 1) {
            //团检订单预约
            wrapper.eq(Reservation::getPatientcode, param.getPcodeOrOrderId());
        } else {
            //个检订单预约
            if ("app0715".equals(param.getSystemId()) || "0".equals(param.getSystemId())){
                wrapper.eq(Reservation::getPatientcode, param.getPcodeOrOrderId());
            }else {
                wrapper.eq(Reservation::getBizOrderNum, param.getPcodeOrOrderId());
            }
        }
        Reservation reservation = reservationOpenApiMapper.selectOne(wrapper);
        if (Objects.isNull(reservation)) {
            //没有符合条件的预约记录
            log.error("体检系统中没有符合条件的预约记录");
            return R.fail("体检系统中没有符合条件的预约记录");
        }
        reservation.setStatus(ReservationStatus.fail.value());
        reservation.setFailReason(param.getFailReason());
        reservation.setModifydate(new Date());
        reservation.setModifier(param.getModifier());
        reservationOpenApiMapper.updateById(reservation);
        // 恢复可预约人数
        ReservationSetting setting = reservationSettingService.getInfoById(reservation.getTimeId());
        if (Objects.nonNull(setting)) {
            reservationSettingService.updateAbleNumWithRetry(reservation.getTimeId(), 1);
        }
        // 删除体检者表的登记时间
        //如果存在体检号，还需要更新体检号的预约时间
        if (StringUtils.isNotBlank(reservation.getPatientcode())){
            //根据短号查询
            Peispatient peispatient = peispatientService.getOne(new LambdaQueryWrapper<Peispatient>()
                    .eq(Peispatient::getShortCode, reservation.getPatientcode().substring(reservation.getPatientcode().length() - 8)));
            if (ObjectUtils.isNotEmpty(peispatient)){
                LambdaUpdateWrapper<Peispatient> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
                lambdaUpdateWrapper.eq(Peispatient::getId, peispatient.getId())
                        .set(Peispatient::getFIsforprepare,0)
                        .set(Peispatient::getFIsforreserve, 0)
                        .set(Peispatient::getDateguidancereturned,null);
                peispatientService.update(null, lambdaUpdateWrapper);
            }
        }
        return R.ok("取消成功！");
    }


    /**
     * 获取我的待预约个检订单
     * @param phone
     * @return
     */
    @Override
    public List<GroupOrderDto> getPersonList(String phone) {
        List<GroupOrderDto> groupOrderList = reservationOpenApiMapper.getPersonList(phone);
        for (GroupOrderDto item : groupOrderList) {
            if (StringUtils.isNotBlank(item.getFzxid())) {
                //获取分中心列表
                String[] branchList = item.getFzxid().split(",");
                List<Branch> list = branchService.list(new LambdaQueryWrapper<Branch>().select(Branch::getBranchId, Branch::getFzx, Branch::getId, Branch::getJm).in(Branch::getBranchId, branchList).eq(Branch::getIsDelete, 0));
                if (CollectionUtil.isNotEmpty(list)) {
                    item.setBranchList(mapperFacade.mapAsList(list, SysBranchDto.class));
                }
            }
            //TODO 判断可以预约的类型
            //是否存在申请的升级类型
            if (Objects.isNull(item.getLevelId())) {
                item.setLevelId(1);
            }
            //按照价格判断类型
        }
        log.info("根据手机号获取我的待预约个检订单:{}", groupOrderList);
        return groupOrderList;
    }

    /**
     * 个检提交预约申请
     * @param param
     * @return
     */
    @Override
    public String personApply(AppointmentDto param) {
        log.info("个检提交预约申请参数:{}", param);
        Reservation reservation = mapperFacade.map(param, Reservation.class);
        String reservationNo = "";
        
        // 有两种情况：第一种，客服给他预约了，生成过一次预约记录
        if (StringUtils.isNotEmpty(param.getId())) {
            reservationNo = updateExistingReservation(param, reservation);
        } else {
            // 第二种就是客服没给他预约，执行插入操作
            reservationNo = createNewReservation(param, reservation);
        }

        return reservationNo;
    }
    
    /**
     * 更新已存在的预约记录
     */
    @Transactional(rollbackFor = Exception.class)
    private String updateExistingReservation(AppointmentDto param, Reservation reservation) {
        Reservation reservationDb = reservationService.getOne(new LambdaQueryWrapper<Reservation>()
                .eq(Reservation::getId, param.getId()));
        if (Objects.isNull(reservationDb)) {
            throw new ServiceException("预约号不存在或者已被删除！");
        }
        
        // 查看预约timeId是否变化
        if (Objects.isNull(reservation.getTimeId()) || reservationDb.getTimeId().equals(reservation.getTimeId())) {
            // timeID没变化
            if (reservation.getStatus() == -1) {
                // 恢复原来预约类型可预约数量
                reservationSettingService.updateAbleNumWithRetry(reservationDb.getTimeId(), 1);
            }
        } else {
            // timeID变化了
            if (StringUtils.isNotEmpty(param.getTimeId())) {
                ReservationSetting setting = reservationSettingService.getInfoById(param.getTimeId());
                if (Objects.isNull(setting)) {
                    throw new ServiceException("预约设置不存在！");
                }
            }
            
            // 恢复原来预约类型可预约数量
            if (StringUtils.isNotEmpty(reservationDb.getTimeId())) {
                reservationSettingService.updateAbleNumWithRetry(reservationDb.getTimeId(), 1);
            }
            
            // 扣除变化后的timeId的可预约人数
            if (reservationSettingService.updateAbleNumWithRetry(reservation.getTimeId(), -1) <= 0) {
                throw new ServiceException("预约失败，今天该时间段可预约人数已满， 请选择其他时间段！");
            }
        }
        
        reservation.setStatus(2);
        reservation.setModifydate(new Date());
        reservationService.updateById(reservation);
        
        return reservation.getReservationNo();
    }
    
    /**
     * 创建新的预约记录
     */
    @Transactional(rollbackFor = Exception.class)
    private String createNewReservation(AppointmentDto param, Reservation reservation) {
        // 判断选择的时间段是否可以预约
        ReservationSetting setting = reservationSettingService.getInfoById(reservation.getTimeId());
        if (Objects.isNull(setting)) {
            throw new ServiceException("预约设置不存在！", 409);
        }
        
        if (reservationSettingService.updateAbleNumWithRetry(reservation.getTimeId(), -1) <= 0) {
            throw new ServiceException("预约失败，今天该时间段可预约人数已满， 请选择其他时间段！", 409);
        }

        reservation.setBizOrderNum(param.getNumorgresv());
        reservation.setTjtcmc(param.getComboName());
        reservation.setStatus(ReservationStatus.SUCCESS.value());
        
        // 生成预约号
        String reservationNo = generateReservationNo(param.getBranchId(), reservation);
        reservation.setReservationNo(reservationNo);
        reservation.setCreatedate(new Date());
        reservation.setId(snowflake.nextIdStr());
        
        // 插入预约记录
        reservationOpenApiMapper.insert(reservation);
        log.info("接收外部第三方预约生成的预约号：{}", reservationNo);
        
        return reservationNo;
    }




    /**
     * 获取可预约时间段列表
     *
     * @param param 筛选参数
     * @return
     */
    @Override
    public List<AppointmentAvailableList> getNewAvailableNums(AppointmentAvailableParam param) {
        ReservationSettingParam settingParam = mapperFacade.map(param, ReservationSettingParam.class);
        List<ReservationSetting> list = reservationSettingService.getNewList(settingParam);

        // 转换预约设置列表为预约时间段列表
        List<AppointmentAvailableList> appointmentList = list.stream().map(item -> {
            AppointmentAvailableDto itemDto = mapperFacade.map(item, AppointmentAvailableDto.class);
            itemDto.setTimeStr(item.getStartTime() + " - " + item.getEndTime());

            AppointmentAvailableList listItem = new AppointmentAvailableList();
            listItem.setTimeStr(itemDto.getTimeStr());
            listItem.setItemDto(Collections.singletonList(itemDto));

            return listItem;
        }).collect(Collectors.toList());
        log.info("获取可预约时间段列表1:{}、{}", list, appointmentList);
        return appointmentList;
    }

    /**
     * 获取预约日期列表
     *
     * @param param
     * @return
     */
    @Override
    public List<ReservationDateVo> getReservationDateList(ReservationSettingParam param) {
        return reservationSettingService.getReservationDateList(param);
    }

    /**
     * 获取预约时间段列表
     * @param param
     * @return
     */
    @Override
    public List<ReservationTimeDto> getReservationTimeList(AppointmentAvailableParam param) {
        return reservationSettingService.getReservationTimeList(param);
    }
}

