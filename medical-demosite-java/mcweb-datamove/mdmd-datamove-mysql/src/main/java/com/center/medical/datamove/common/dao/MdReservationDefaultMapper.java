package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdReservationDefault;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 预约各检区默认设置(MdReservationDefault)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:48:18
 */
public interface MdReservationDefaultMapper extends BaseMapper<MdReservationDefault> {

    /**
     * 分页查询[预约各检区默认设置]列表
     *
     * @param page  分页参数
     * @param param MdReservationDefault查询参数
     * @return 分页数据
     */
    IPage<MdReservationDefault> getPage(PageParam<MdReservationDefault> page, @Param("param") MdReservationDefault param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdReservationDefault getInfoById(@Param("id") Object id);

}
