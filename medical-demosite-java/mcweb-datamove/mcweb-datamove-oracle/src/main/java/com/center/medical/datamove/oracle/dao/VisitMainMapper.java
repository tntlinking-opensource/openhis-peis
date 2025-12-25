package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.VisitMain;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 与迟检、阳性、不合格样本回访表一对多关联，(VisitMain)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:25:51
 */
public interface VisitMainMapper extends BaseMapper<VisitMain> {

    /**
     * 分页查询[与迟检、阳性、不合格样本回访表一对多关联，]列表
     *
     * @param page  分页参数
     * @param param VisitMain查询参数
     * @return 分页数据
     */
    IPage<VisitMain> getPage(PageParam<VisitMain> page, @Param("param") VisitMain param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    VisitMain getInfoById(@Param("id") String id);

}
