package com.center.medical.olddata.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.OrCreatecombo;
import org.apache.ibatis.annotations.Param;

/**
 * 套餐表(Createcombo)数据库访问层
 *
 * @author ay
 * @since 2023-07-25 21:56:41
 */
public interface OrCreatecomboMapper extends BaseMapper<OrCreatecombo> {

    /**
     * 分页查询[套餐表]列表
     *
     * @param page  分页参数
     * @param param Createcombo查询参数
     * @return 分页数据
     */
    IPage<OrCreatecombo> getPage(PageParam<OrCreatecombo> page, @Param("param") OrCreatecombo param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    OrCreatecombo getInfoById(@Param("id") String id);

}
