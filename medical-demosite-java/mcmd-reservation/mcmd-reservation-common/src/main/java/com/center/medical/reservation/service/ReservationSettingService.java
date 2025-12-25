package com.center.medical.reservation.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
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

import java.util.List;
import java.util.Map;

/**
 * 预约各检区设置(ReservationSetting)表服务接口
 *
 * @author ay
 * @since 2023-03-18 08:54:15
 */
public interface ReservationSettingService extends IService<ReservationSetting> {

    /**
     * 分页查询[预约各检区设置]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<ReservationSetting> getPage(PageParam<ReservationSetting> page, ReservationPageParam param);

    /**
     * 获取预约时段列表
     *
     * @param param
     * @return
     */
    List<ReservationSetting> getList(ReservationSettingParam param);



    /**
     * 获取预约日期列表
     *
     * @param param
     * @return
     */
    List<ReservationDateVo> getReservationDateList(ReservationSettingParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    ReservationSetting getInfoById(String id);

    /**
     * 更新可预约人数
     *
     * @param id    预约设置ID
     * @param count 变更数量，负值表示减少，正值表示增加
     */
    Integer updateAbleNum(String id, Integer count);

    /**
     * 使用乐观锁更新可预约人数，带重试机制
     *
     * @param id    预约设置ID
     * @param count 变更数量，负值表示减少，正值表示增加
     * @return 更新结果
     */
    Integer updateAbleNumWithRetry(String id, Integer count);

    /**
     * 更新可预约人数
     *
     * @param reservationSettingCondition 根据条件更新可预约人数
     * @return 时间段Id集合
     */
    List<Map<String, Object>> updateAbleNumWithoutId(ReservationSettingCondition reservationSettingCondition);

    /**
     * 获取团体可预约列表
     *
     * @param param
     * @return
     */
    List<ReservationSettingGroupDto> groupList(ReservationSettingGroupParam param);

    /**
     * 分页查询预约详情列表
     *
     * @param page  分页参数
     * @param param 筛选条件
     * @return
     */
    IPage<ReservationTotalDto> groupPage(PageParam<ReservationSetting> page, ReservationSettingGroupParam param);

    /**
     * 获取日期预约信息导出数据
     *
     * @param param
     */
    List<ReservationTotalDto> getExportDetailData(ReservationSettingGroupParam param);

    /**
     * 分页查询预约日期
     * @param page
     * @param param
     * @return
     */
    IPage<QueryReservationVo> queryReservationDate(PageParam<QueryReservationVo> page, QueryReservationParam param);

    /**
     * 分页查询预约时间
     * @param page
     * @param param
     * @return
     */
    IPage<QueryReservationTimeVo> queryReservationTime(PageParam<QueryReservationTimeVo> page, QueryReservationParam param);

    /**
     * 批量设置
     * @param param
     * @return
     */
    Boolean batchSettings(BatchSettingDataParam param);

    /**
     * 批量关闭
     * @param ids
     * @return
     */
    Boolean batchClose(List<String> ids);

    /**
     * 获取可预约时间段列表 不向下兼容会员类型
     * @param settingParam
     * @return
     */
    List<ReservationSetting> getNewList(ReservationSettingParam settingParam);

    /**
     * 获取预约时间段列表
     * @param param
     * @return
     */
    List<ReservationTimeDto> getReservationTimeList(AppointmentAvailableParam param);
}

