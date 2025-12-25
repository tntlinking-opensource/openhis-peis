package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdVisitMain;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 与迟检、阳性、不合格样本回访表一对多关联，(MdVisitMain)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:49:30
 */
public interface MdVisitMainMapper extends BaseMapper<MdVisitMain> {

    /**
     * 分页查询[与迟检、阳性、不合格样本回访表一对多关联，]列表
     *
     * @param page  分页参数
     * @param param MdVisitMain查询参数
     * @return 分页数据
     */
    IPage<MdVisitMain> getPage(PageParam<MdVisitMain> page, @Param("param") MdVisitMain param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdVisitMain getInfoById(@Param("id") String id);

}
