package com.center.medical.reservation.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.BranchScope;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.reservation.bean.model.ReservationDefault;
import com.center.medical.reservation.bean.param.ReservationPageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 预约各检区默认设置(ReservationDefault)表数据库访问层
 *
 * @author ay
 * @since 2023-03-18 08:54:14
 */
public interface ReservationDefaultMapper extends BaseMapper<ReservationDefault> {

    /**
     * 分页查询[预约各检区默认设置]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    @BranchScope(alias = "rd.branch_id")
    IPage<ReservationDefault> getList(PageParam<ReservationDefault> page, @Param("param") ReservationPageParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    ReservationDefault getInfoById(@Param("id") String id);

}
