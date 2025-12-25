package com.center.medical.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.bean.model.Dictoccupation;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 职业类型表(Dictoccupation)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:35:19
 */
public interface DictoccupationMapper extends BaseMapper<Dictoccupation> {

    /**
     * 分页查询[职业类型表]列表
     *
     * @param page  分页参数
     * @param param Dictoccupation查询参数
     * @return 分页数据
     */
    IPage<Dictoccupation> getList(PageParam<Dictoccupation> page, @Param("param") Dictoccupation param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    Dictoccupation getInfoById(@Param("id") String id);

}
