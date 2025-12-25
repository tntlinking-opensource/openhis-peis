package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdBaseUnit;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 济南-计量单位(MdBaseUnit)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:45:10
 */
public interface MdBaseUnitMapper extends BaseMapper<MdBaseUnit> {

    /**
     * 分页查询[济南-计量单位]列表
     *
     * @param page  分页参数
     * @param param MdBaseUnit查询参数
     * @return 分页数据
     */
    IPage<MdBaseUnit> getPage(PageParam<MdBaseUnit> page, @Param("param") MdBaseUnit param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdBaseUnit getInfoById(@Param("id") String id);

}
