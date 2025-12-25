package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.Dlqk;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * KS锻炼情况(Dlqk)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:18:17
 */
public interface DlqkMapper extends BaseMapper<Dlqk> {

    /**
     * 分页查询[KS锻炼情况]列表
     *
     * @param page  分页参数
     * @param param Dlqk查询参数
     * @return 分页数据
     */
    IPage<Dlqk> getPage(PageParam<Dlqk> page, @Param("param") Dlqk param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Dlqk getInfoById(@Param("id") String id);

}
