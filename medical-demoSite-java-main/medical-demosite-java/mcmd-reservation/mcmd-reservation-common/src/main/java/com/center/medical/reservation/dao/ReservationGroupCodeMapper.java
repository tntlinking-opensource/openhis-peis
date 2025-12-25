package com.center.medical.reservation.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.reservation.bean.model.ReservationGroupCode;
import com.center.medical.reservation.bean.param.ReGroupCodePageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 团体预约分享码(ReservationGroupCode)数据库访问层
 *
 * @author ay
 * @since 2024-03-08 16:38:39
 */
public interface ReservationGroupCodeMapper extends BaseMapper<ReservationGroupCode> {

    /**
     * 分页查询[团体预约分享码]列表
     *
     * @param page  分页参数
     * @param param ReservationGroupCode查询参数
     * @return 分页数据
     */
    IPage<ReservationGroupCode> getPage(PageParam<ReservationGroupCode> page, @Param("param") ReGroupCodePageParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    ReservationGroupCode getInfoById(@Param("id") String id);

    /**
     * 查看套餐的价格
     * @param groupId
     * @return
     */
    Double getPriceByGroupId(@Param("groupId") String groupId);
}
