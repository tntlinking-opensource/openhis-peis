package com.center.medical.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.PacsItems;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * PACS-收费项目(PacsItems)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:52
 */
public interface PacsItemMapper extends BaseMapper<PacsItems> {

    /**
     * 分页查询[PACS-收费项目]列表
     *
     * @param page  分页参数
     * @param param PacsItems查询参数
     * @return 分页数据
     */
    IPage<PacsItems> getList(PageParam<PacsItems> page, @Param("param") PacsItems param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    PacsItems getInfoById(@Param("id") String id);

}
