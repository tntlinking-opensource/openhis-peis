package com.center.medical.reservation.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
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
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 预约各检区设置(ReservationSetting)表数据库访问层
 *
 * @author ay
 * @since 2023-03-18 08:54:15
 */
public interface ReservationSettingMapper extends BaseMapper<ReservationSetting> {

    /**
     * 分页查询[预约各检区设置]列表
     *
     * @param page  分页参数
     * @param param ReservationSetting查询参数
     * @return 分页数据
     */
    IPage<ReservationSetting> getPage(PageParam<ReservationSetting> page, @Param("param") ReservationPageParam param);

    /**
     * 获取预约时段列表
     *
     * @param param
     * @return
     */
    List<ReservationSetting> getList(@Param("param") ReservationSettingParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    ReservationSetting getInfoById(@Param("id") String id);

    /**
     * 更新可预约人数
     *
     * @param id    预约设置ID
     * @param count 变更数量，负值表示减少，正值表示增加
     */
    Integer updateAbleNum(@Param("id") String id, @Param("count") Integer count);

    /**
     * 原子性更新可预约人数，避免锁竞争
     *
     * @param id    预约设置ID
     * @param count 变更数量，负值表示减少，正值表示增加
     * @param absCount 绝对数量，用于条件判断
     */
    Integer updateAbleNumAtomic(@Param("id") String id, @Param("count") Integer count, @Param("absCount") Integer absCount);

    /**
     * 获取团体可预约列表
     *
     * @param param
     * @return
     */
    List<ReservationSettingGroupDto> groupList(@Param("param") ReservationSettingGroupParam param);

    /**
     * 分页查询预约详情列表
     *
     * @param page  分页参数
     * @param param 筛选条件
     * @return
     */
    IPage<ReservationTotalDto> groupPage(PageParam<ReservationSetting> page, @Param("param") ReservationSettingGroupParam param);

    /**
     * 获取日期预约信息导出数据
     *
     * @param param
     */
    List<ReservationTotalDto> getExportDetailData(@Param("param") ReservationSettingGroupParam param);

    /**
     * 分页查询预约日期
     * @param page
     * @param param
     * @return
     */
    IPage<QueryReservationVo> queryReservationDate(PageParam<QueryReservationVo> page, @Param("param") QueryReservationParam param);

    /**
     * 分页查询预约时间
     * @param page
     * @param param
     * @return
     */
    IPage<QueryReservationTimeVo> queryReservationTime(PageParam<QueryReservationTimeVo> page, @Param("param") QueryReservationParam param);

    /**
     * 获取可预约时间段列表 不向下兼容会员类型
     *
     * @param param
     * @return
     */
    List<ReservationSetting> getNewList(@Param("param") ReservationSettingParam param);



    /**
     * 获取预约日期列表
     *
     * @param param
     * @return
     */
    List<ReservationDateVo> getReservationDateList(@Param("param") ReservationSettingParam param);

    /**
     * 获取预约时间段列表
     * @param param
     * @return
     */
    List<ReservationTimeDto> getReservationTimeList(@Param("param") AppointmentAvailableParam param);
}
