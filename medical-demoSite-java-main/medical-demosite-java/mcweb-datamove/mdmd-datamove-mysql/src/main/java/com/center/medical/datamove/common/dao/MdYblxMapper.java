package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdYblx;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 样本类型(MdYblx)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:54:27
 */
public interface MdYblxMapper extends BaseMapper<MdYblx> {

    /**
     * 分页查询[样本类型]列表
     *
     * @param page  分页参数
     * @param param MdYblx查询参数
     * @return 分页数据
     */
    IPage<MdYblx> getPage(PageParam<MdYblx> page, @Param("param") MdYblx param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdYblx getInfoById(@Param("id") String id);

}
