package com.center.medical.sellcrm.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.sellcrm.bean.model.Riskclientcon;
import org.apache.ibatis.annotations.Param;

/**
 * 高危人员关联表(Riskclientcon)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-22 19:29:10
 */
public interface RiskclientconMapper extends BaseMapper<Riskclientcon> {

    /**
     * 分页查询[高危人员关联表]列表
     *
     * @param page  分页参数
     * @param param Riskclientcon查询参数
     * @return 分页数据
     */
    IPage<Riskclientcon> getPage(PageParam<Riskclientcon> page, @Param("param") Riskclientcon param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Riskclientcon getInfoById(@Param("id") String id);

}
