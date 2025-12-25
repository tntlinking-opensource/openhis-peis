package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.BaseUnit;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 济南-计量单位(BaseUnit)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:12:37
 */
public interface BaseUnitMapper extends BaseMapper<BaseUnit> {

    /**
     * 分页查询[济南-计量单位]列表
     *
     * @param page  分页参数
     * @param param BaseUnit查询参数
     * @return 分页数据
     */
    IPage<BaseUnit> getPage(PageParam<BaseUnit> page, @Param("param") BaseUnit param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    BaseUnit getInfoById(@Param("id") String id);

}
