package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.Harm;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * JC危害因素(Harm)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:19:10
 */
public interface HarmMapper extends BaseMapper<Harm> {

    /**
     * 分页查询[JC危害因素]列表
     *
     * @param page  分页参数
     * @param param Harm查询参数
     * @return 分页数据
     */
    IPage<Harm> getPage(PageParam<Harm> page, @Param("param") Harm param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Harm getInfoById(@Param("id") String id);

}
