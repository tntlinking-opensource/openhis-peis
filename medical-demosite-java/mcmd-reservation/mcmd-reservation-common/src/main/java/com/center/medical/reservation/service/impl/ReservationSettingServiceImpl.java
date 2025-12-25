package com.center.medical.reservation.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.Arith;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.reservation.bean.dto.ReservationSettingGroupDto;
import com.center.medical.reservation.bean.dto.ReservationTimeDto;
import com.center.medical.reservation.bean.dto.ReservationTotalDto;
import com.center.medical.reservation.bean.model.ReservationSetting;
import com.center.medical.reservation.bean.param.*;
import com.center.medical.reservation.bean.param.*;
import com.center.medical.reservation.bean.vo.QueryReservationTimeVo;
import com.center.medical.reservation.bean.vo.QueryReservationVo;
import com.center.medical.reservation.bean.vo.ReservationDateVo;
import com.center.medical.reservation.dao.ReservationSettingMapper;
import com.center.medical.reservation.service.ReservationSettingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 预约各检区设置(ReservationSetting)表服务实现类
 *
 * @author ay
 * @since 2023-03-18 08:54:15
 */
@Slf4j
@Service("reservationSettingService")
@RequiredArgsConstructor
public class ReservationSettingServiceImpl extends ServiceImpl<ReservationSettingMapper, ReservationSetting> implements ReservationSettingService {

    private final ReservationSettingMapper reservationSettingMapper;

    /**
     * 分页查询[预约各检区设置]列表
     *
     * @param page  分页参数
     * @param param ReservationSetting查询参数
     * @return 分页数据
     */
    @Override
    public IPage<ReservationSetting> getPage(PageParam<ReservationSetting> page, ReservationPageParam param) {
        return reservationSettingMapper.getPage(page, param);
    }

    /**
     * 获取预约时段列表
     *
     * @param param
     * @return
     */
    public List<ReservationSetting> getList(ReservationSettingParam param) {
        return reservationSettingMapper.getList(param);
    }

    /**
     * 获取预约日期列表
     *
     * @param param
     * @return
     */
    @Override
    public List<ReservationDateVo> getReservationDateList(ReservationSettingParam param) {
        return reservationSettingMapper.getReservationDateList(param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public ReservationSetting getInfoById(String id) {
        return reservationSettingMapper.getInfoById(id);
    }

    /**
     * 更新可预约人数
     *
     * @param id    预约设置ID
     * @param count 变更数量，负值表示减少，正值表示增加
     */
    @Override
    public Integer updateAbleNum(String id, Integer count) {
        return reservationSettingMapper.updateAbleNum(id, count);
    }

    /**
     * 原子性更新可预约人数，避免锁竞争
     *
     * @param id    预约设置ID
     * @param count 变更数量，负值表示减少，正值表示增加
     * @return 更新结果
     */
    @Override
    public Integer updateAbleNumWithRetry(String id, Integer count) {
        // 计算绝对数量，用于条件判断（仅在减少时使用）
        Integer absCount = Math.abs(count);
        
        // 直接使用原子性更新，避免锁竞争
        Integer result = reservationSettingMapper.updateAbleNumAtomic(id, count, absCount);
        
        if (result > 0) {
            return result;
        } else {
            // 根据操作类型提供不同的错误信息
            if (count < 0) {
                throw new ServiceException("预约失败，该时间段可预约人数已满，请选择其他时间段！");
            } else {
                throw new ServiceException("更新可预约人数失败，请重试！");
            }
        }
    }

    /**
     * 更新可预约人数
     *
     * @param reservationSettingCondition 根据条件更新可预约人数
     * @return 时间段Id集合
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<Map<String, Object>> updateAbleNumWithoutId(ReservationSettingCondition reservationSettingCondition) {
        Integer countAm = reservationSettingCondition.getCountAm();
        //查询可预约的列表
        List<ReservationSetting> settingList = reservationSettingMapper.selectList(new LambdaQueryWrapper<ReservationSetting>()
                .eq(ReservationSetting::getReservationDate, reservationSettingCondition.getReservationDate())
                .eq(ReservationSetting::getBranchId, reservationSettingCondition.getBranchId())
                .eq(ReservationSetting::getStatus, 1)
                .eq(ReservationSetting::getLevelId, reservationSettingCondition.getLevelId())
                .gt(ReservationSetting::getAbleNum, 0));
        List<Map<String, Object>> timeIdList = new ArrayList<>();
        //设置已预约人数,单个setting可预约人数可能不够,所以做了循环设置
        for (ReservationSetting setting : settingList) {
            if (setting.getAbleNum() >= countAm) {
                Integer success = reduceAbleNumCycle(setting.getId(), countAm);
                if (success > 0) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("key", setting.getId());
                    map.put("value", success);
                    timeIdList.add(map);
                    if (success == countAm) {
                        countAm = 0;
                        break;
                    } else {
                        countAm = countAm - success;
                    }
                }
            } else {
                Integer success = reduceAbleNumCycle(setting.getId(), setting.getAbleNum());
                if (success > 0) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("key", setting.getId());
                    map.put("value", success);
                    timeIdList.add(map);
                    if (success == countAm) {
                        countAm = 0;
                        break;
                    } else {
                        countAm = countAm - success;
                    }
                }
            }
        }
        if (countAm > 0) {
            throw new ServiceException("预约失败,当前可预约人数不足" + reservationSettingCondition.getCountAm() + "人,请刷新后再调整人数!");
        }
        return timeIdList;
    }

    /**
     * 获取团体可预约列表
     *
     * @param param
     * @return
     */
    @Override
    public List<ReservationSettingGroupDto> groupList(ReservationSettingGroupParam param) {
        return reservationSettingMapper.groupList(param);
    }

    /**
     * 分页查询预约详情列表
     *
     * @param page  分页参数
     * @param param 筛选条件
     * @return
     */
    @Override
    public IPage<ReservationTotalDto> groupPage(PageParam<ReservationSetting> page, ReservationSettingGroupParam param) {
        return reservationSettingMapper.groupPage(page, param);
    }

    /**
     * 获取日期预约信息导出数据
     *
     * @param param
     */
    @Override
    public List<ReservationTotalDto> getExportDetailData(ReservationSettingGroupParam param) {
        return reservationSettingMapper.getExportDetailData(param);
    }

    /**
     * 循环更新
     *
     * @param id
     * @param count
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public Integer reduceAbleNumCycle(String id, Integer count) {
        Integer success = 0;
        // 使用原子性更新，避免锁竞争
        Integer absCount = Math.abs(count);
        if (reservationSettingMapper.updateAbleNumAtomic(id, -count, absCount) > 0) {
            success = count;
        } else {
            ReservationSetting item = reservationSettingMapper.getInfoById(id);
            if (item.getAbleNum() > 0) {
                Integer result = reduceAbleNumCycle(id, -item.getAbleNum());
                if (result != 0) {
                    success = result;
                }
            } else {
                success = -1;
            }
        }
        return success;
    }


    /**
     * 分页查询预约日期
     * @param page
     * @param param
     * @return
     */
    @Override
    public IPage<QueryReservationVo> queryReservationDate(PageParam<QueryReservationVo> page, QueryReservationParam param) {
        return reservationSettingMapper.queryReservationDate(page,param);
    }

    /**
     * 分页查询预约时间
     * @param page
     * @param param
     * @return
     */
    @Override
    public IPage<QueryReservationTimeVo> queryReservationTime(PageParam<QueryReservationTimeVo> page, QueryReservationParam param) {
        return reservationSettingMapper.queryReservationTime(page,param);
    }


    /**
     * 批量设置
     * @param param
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean batchSettings(BatchSettingDataParam param) {
        if (ObjectUtils.isEmpty(param) || CollectionUtil.isEmpty(param.getSettingsParam()) || ObjectUtils.isEmpty(param.getReservationDate())){
            throw new ServiceException("请填写完整信息!");
        }
        for (BatchSettingsParam batchSettingsParam : param.getSettingsParam()) {
            if (ObjectUtils.isEmpty(batchSettingsParam.getNum())){
                continue;
            }
            //查询当天的预约设置
            List<ReservationSetting> settingList = reservationSettingMapper.selectList(new LambdaQueryWrapper<ReservationSetting>()
                    .eq(ReservationSetting::getReservationDate, param.getReservationDate())
                    .eq(ReservationSetting::getStatus, 1)
                    .eq(ReservationSetting::getLevelId, batchSettingsParam.getLevelId())
            );
            if (CollectionUtil.isEmpty(settingList)){
                continue;
            }
            //算出要设置的人数 = 总人数 / 条数
            int num = (int) Arith.div(batchSettingsParam.getNum(), settingList.size(), 0);
            for (ReservationSetting reservationSetting : settingList) {
                reservationSetting.setMaxNum(num);
                reservationSetting.setStatus(batchSettingsParam.getStatus());
                reservationSettingMapper.updateById(reservationSetting);
            }

        }
        return Boolean.TRUE;
    }

    /**
     * 批量关闭
     * @param ids
     * @return
     */
    @Override
    public Boolean batchClose(List<String> ids) {
        ReservationSetting reservationSetting = new ReservationSetting();
        reservationSetting.setStatus(0);
        reservationSetting.setModifier(SecurityUtils.getUserNo());
        reservationSettingMapper.update(reservationSetting, new LambdaQueryWrapper<ReservationSetting>()
                .in(ReservationSetting::getId, ids));
        return Boolean.TRUE;
    }

    /**
     * 获取可预约时间段列表 不向下兼容会员类型
     *
     * @param param
     * @return
     */
    public List<ReservationSetting> getNewList(ReservationSettingParam param) {
        return reservationSettingMapper.getNewList(param);
    }

    /**
     * 获取预约时间段列表
     * @param param
     * @return
     */
    @Override
    public List<ReservationTimeDto> getReservationTimeList(AppointmentAvailableParam param) {
        return reservationSettingMapper.getReservationTimeList(param);
    }
}

