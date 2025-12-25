package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdSatisfaction;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * KF满意度(MdSatisfaction)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:48:27
 */
public interface MdSatisfactionMapper extends BaseMapper<MdSatisfaction> {

    /**
     * 分页查询[KF满意度]列表
     *
     * @param page  分页参数
     * @param param MdSatisfaction查询参数
     * @return 分页数据
     */
    IPage<MdSatisfaction> getPage(PageParam<MdSatisfaction> page, @Param("param") MdSatisfaction param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdSatisfaction getInfoById(@Param("id") String id);

}
