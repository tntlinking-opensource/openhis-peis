package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdCreatecomboType;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 最小套餐分类(MdCreatecomboType)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:45:19
 */
public interface MdCreatecomboTypeMapper extends BaseMapper<MdCreatecomboType> {

    /**
     * 分页查询[最小套餐分类]列表
     *
     * @param page  分页参数
     * @param param MdCreatecomboType查询参数
     * @return 分页数据
     */
    IPage<MdCreatecomboType> getPage(PageParam<MdCreatecomboType> page, @Param("param") MdCreatecomboType param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdCreatecomboType getInfoById(@Param("id") String id);

}
