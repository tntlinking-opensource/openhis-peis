package com.center.medical.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.bean.model.PeispatientReservationCharge;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 体检者结算表(PeispatientReservationCharge)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:03
 */
public interface PeispatientReservationChargeMapper extends BaseMapper<PeispatientReservationCharge> {

    /**
     * 分页查询[体检者结算表]列表
     *
     * @param page  分页参数
     * @param param PeispatientReservationCharge查询参数
     * @return 分页数据
     */
    IPage<PeispatientReservationCharge> getList(PageParam<PeispatientReservationCharge> page, @Param("param") PeispatientReservationCharge param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    PeispatientReservationCharge getInfoById(@Param("id") String id);

}
