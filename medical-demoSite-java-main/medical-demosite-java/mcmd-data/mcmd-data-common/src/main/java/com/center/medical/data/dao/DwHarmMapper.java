package com.center.medical.data.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.DwHarm;
import org.apache.ibatis.annotations.Param;

/**
 * 单位危害因素(DwHarm)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:17
 */
public interface DwHarmMapper extends BaseMapper<DwHarm> {

    /**
     * 分页查询[单位危害因素]列表
     *
     * @param page  分页参数
     * @param param DwHarm查询参数
     * @return 分页数据
     */
    IPage<DwHarm> getList(PageParam<DwHarm> page, @Param("param") DwHarm param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    DwHarm getInfoById(@Param("id") String id);

}
