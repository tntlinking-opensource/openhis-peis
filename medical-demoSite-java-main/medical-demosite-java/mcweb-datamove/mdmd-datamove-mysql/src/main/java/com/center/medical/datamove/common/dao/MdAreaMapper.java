package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdArea;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 籍贯表(MdArea)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:45:06
 */
public interface MdAreaMapper extends BaseMapper<MdArea> {

    /**
     * 分页查询[籍贯表]列表
     *
     * @param page  分页参数
     * @param param MdArea查询参数
     * @return 分页数据
     */
    IPage<MdArea> getPage(PageParam<MdArea> page, @Param("param") MdArea param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdArea getInfoById(@Param("id") String id);

}
