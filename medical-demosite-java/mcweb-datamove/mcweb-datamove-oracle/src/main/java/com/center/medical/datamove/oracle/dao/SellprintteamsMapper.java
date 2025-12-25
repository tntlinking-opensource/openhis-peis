package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.Sellprintteams;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 销售打印项目分类设置表(Sellprintteams)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:24:54
 */
public interface SellprintteamsMapper extends BaseMapper<Sellprintteams> {

    /**
     * 分页查询[销售打印项目分类设置表]列表
     *
     * @param page  分页参数
     * @param param Sellprintteams查询参数
     * @return 分页数据
     */
    IPage<Sellprintteams> getPage(PageParam<Sellprintteams> page, @Param("param") Sellprintteams param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Sellprintteams getInfoById(@Param("id") String id);

}
