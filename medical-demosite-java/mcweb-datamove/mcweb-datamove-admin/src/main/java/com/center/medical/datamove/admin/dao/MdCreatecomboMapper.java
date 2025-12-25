package com.center.medical.datamove.admin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.datamove.admin.bean.model.MdCreatecombo;
import org.apache.ibatis.annotations.Param;

/**
 * 最小套餐(MdCreatecombo)数据库访问层
 *
 * @author ay
 * @since 2023-07-25 21:58:11
 */
public interface MdCreatecomboMapper extends BaseMapper<MdCreatecombo> {

    /**
     * 分页查询[最小套餐]列表
     *
     * @param page  分页参数
     * @param param MdCreatecombo查询参数
     * @return 分页数据
     */
    IPage<MdCreatecombo> getPage(PageParam<MdCreatecombo> page, @Param("param") MdCreatecombo param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdCreatecombo getInfoById(@Param("id") String id);

}
