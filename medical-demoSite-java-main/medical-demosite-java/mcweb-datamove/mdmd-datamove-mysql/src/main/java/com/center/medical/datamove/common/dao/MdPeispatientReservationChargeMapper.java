package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdPeispatientReservationCharge;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 体检者结算表(MdPeispatientReservationCharge)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:48:13
 */
public interface MdPeispatientReservationChargeMapper extends BaseMapper<MdPeispatientReservationCharge> {

    /**
     * 分页查询[体检者结算表]列表
     *
     * @param page  分页参数
     * @param param MdPeispatientReservationCharge查询参数
     * @return 分页数据
     */
    IPage<MdPeispatientReservationCharge> getPage(PageParam<MdPeispatientReservationCharge> page, @Param("param") MdPeispatientReservationCharge param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdPeispatientReservationCharge getInfoById(@Param("id") String id);

}
