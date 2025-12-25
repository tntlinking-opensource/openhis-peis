package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdReservationSetting;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 预约各检区设置(MdReservationSetting)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:48:19
 */
public interface MdReservationSettingMapper extends BaseMapper<MdReservationSetting> {

    /**
     * 分页查询[预约各检区设置]列表
     *
     * @param page  分页参数
     * @param param MdReservationSetting查询参数
     * @return 分页数据
     */
    IPage<MdReservationSetting> getPage(PageParam<MdReservationSetting> page, @Param("param") MdReservationSetting param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdReservationSetting getInfoById(@Param("id") Object id);

}
