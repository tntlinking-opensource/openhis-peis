package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdReservation;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 体检预约记录(MdReservation)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:48:17
 */
public interface MdReservationMapper extends BaseMapper<MdReservation> {

    /**
     * 分页查询[体检预约记录]列表
     *
     * @param page  分页参数
     * @param param MdReservation查询参数
     * @return 分页数据
     */
    IPage<MdReservation> getPage(PageParam<MdReservation> page, @Param("param") MdReservation param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdReservation getInfoById(@Param("id") Long id);

}
