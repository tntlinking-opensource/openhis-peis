package com.center.medical.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.bean.model.Dlqk;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * KS锻炼情况(Dlqk)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:44
 */
public interface DlqkMapper extends BaseMapper<Dlqk> {

    /**
     * 分页查询[KS锻炼情况]列表
     *
     * @param page  分页参数
     * @param param Dlqk查询参数
     * @return 分页数据
     */
    IPage<Dlqk> getList(PageParam<Dlqk> page, @Param("param") Dlqk param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    Dlqk getInfoById(@Param("id") String id);

}
