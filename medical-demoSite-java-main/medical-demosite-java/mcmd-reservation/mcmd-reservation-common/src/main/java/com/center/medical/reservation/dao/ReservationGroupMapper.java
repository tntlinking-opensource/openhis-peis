package com.center.medical.reservation.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.reservation.bean.dto.ReservationGroupData;
import com.center.medical.reservation.bean.model.ReservationGroup;
import com.center.medical.reservation.bean.param.RgListParam;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 团体预约记录(ReservationGroup)数据库访问层
 *
 * @author makejava
 * @since 2023-08-31 16:45:53
 */
public interface ReservationGroupMapper extends BaseMapper<ReservationGroup> {

    /**
     * 分页查询[团体预约记录]列表
     *
     * @param page  分页参数
     * @param param ReservationGroup查询参数
     * @return 分页数据
     */
    IPage<ReservationGroup> getPage(PageParam<ReservationGroup> page, @Param("param") RgListParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    ReservationGroup getInfoById(@Param("id") String id);

    /**
     * 获取团队预约信息导出数据
     *
     * @param param
     */
    List<ReservationGroupData> getExportData(@Param("param") RgListParam param);

    /**
     * 获取今天已预约的人数
     * @param idOrg
     * @return
     */
    Integer getReservationNum(@Param("idOrg") String idOrg,@Param("reservationDate") Date reservationDate,@Param("id") String id);
}
