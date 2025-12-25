package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdFxPositive;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 综合分析-阳性小结(MdFxPositive)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:46:19
 */
public interface MdFxPositiveMapper extends BaseMapper<MdFxPositive> {

    /**
     * 分页查询[综合分析-阳性小结]列表
     *
     * @param page  分页参数
     * @param param MdFxPositive查询参数
     * @return 分页数据
     */
    IPage<MdFxPositive> getPage(PageParam<MdFxPositive> page, @Param("param") MdFxPositive param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdFxPositive getInfoById(@Param("id") String id);

}
