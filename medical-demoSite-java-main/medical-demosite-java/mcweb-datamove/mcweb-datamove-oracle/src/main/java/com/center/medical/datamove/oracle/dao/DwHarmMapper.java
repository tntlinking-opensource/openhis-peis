package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.DwHarm;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * JC单位危害因素(DwHarm)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:18:31
 */
public interface DwHarmMapper extends BaseMapper<DwHarm> {

    /**
     * 分页查询[JC单位危害因素]列表
     *
     * @param page  分页参数
     * @param param DwHarm查询参数
     * @return 分页数据
     */
    IPage<DwHarm> getPage(PageParam<DwHarm> page, @Param("param") DwHarm param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    DwHarm getInfoById(@Param("id") String id);

}
