package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.Dictoccupation;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * JC职业表(Dictoccupation)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:18:13
 */
public interface DictoccupationMapper extends BaseMapper<Dictoccupation> {

    /**
     * 分页查询[JC职业表]列表
     *
     * @param page  分页参数
     * @param param Dictoccupation查询参数
     * @return 分页数据
     */
    IPage<Dictoccupation> getPage(PageParam<Dictoccupation> page, @Param("param") Dictoccupation param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Dictoccupation getInfoById(@Param("id") String id);

}
