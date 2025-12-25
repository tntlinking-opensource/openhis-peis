package com.center.medical.olddata.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.OrLung;
import org.apache.ibatis.annotations.Param;

/**
 * KS肺功能(Lung)数据库访问层
 *
 * @author ay
 * @since 2024-06-05 15:44:03
 */
public interface OrLungMapper extends BaseMapper<OrLung> {

    /**
     * 分页查询[KS肺功能]列表
     *
     * @param page  分页参数
     * @param param Lung查询参数
     * @return 分页数据
     */
    IPage<OrLung> getPage(PageParam<OrLung> page, @Param("param") OrLung param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    OrLung getInfoById(@Param("id") String id);

}
