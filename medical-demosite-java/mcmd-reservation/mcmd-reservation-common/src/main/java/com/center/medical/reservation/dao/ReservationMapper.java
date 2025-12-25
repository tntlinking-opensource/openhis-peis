package com.center.medical.reservation.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.reservation.bean.dto.RGroupData;
import com.center.medical.reservation.bean.dto.ReservationData;
import com.center.medical.reservation.bean.model.Reservation;
import com.center.medical.reservation.bean.param.GetReservationParam;
import com.center.medical.reservation.bean.param.ReservationParam;
import com.center.medical.reservation.bean.param.RgListParam;
import com.center.medical.reservation.bean.param.VipExportParam;
import com.center.medical.reservation.bean.vo.ReservationVo;
import com.center.medical.reservation.bean.vo.VipExportVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 体检预约记录(Reservation)表数据库访问层
 *
 * @author ay
 * @since 2023-03-18 08:54:13
 */
public interface ReservationMapper extends BaseMapper<Reservation> {

    /**
     * 分页查询[体检预约记录]列表
     *
     * @param page  分页参数
     * @param param Reservation查询参数
     * @return 分页数据
     */
//    @BranchScope(alias = "r.branch_id")
    IPage<ReservationVo> getList(PageParam<Reservation> page, @Param("param") ReservationParam param);

    /**
     * 根据预约号获取记录详情
     *
     * @param reservationNo 预约号
     * @return 详情信息
     */
    ReservationVo getInfoByNo(@Param("reservationNo") String reservationNo);

    /**
     * 根据id查体检预约记录详情
     *
     * @param id
     * @return
     */
    ReservationVo getInfoById(@Param("id") String id);

    /**
     * 获取导出预约数据
     *
     * @param param
     */
    List<ReservationData> getExportData(@Param("param") ReservationParam param);

    /**
     * 团体预约信息按时间段导出数据
     *
     * @param param
     * @return
     */
    List<RGroupData> getGroupExportData(RgListParam param);

    /**
     * 根据预约号或体检号获取预约详情
     * @param param
     * @return
     */
    Reservation getReservation(@Param("param") GetReservationParam param);

    /**
     * vip及贵宾导出
     * @param param
     * @return
     */
    List<VipExportVo> vipExport(@Param("param") VipExportParam param);
}
