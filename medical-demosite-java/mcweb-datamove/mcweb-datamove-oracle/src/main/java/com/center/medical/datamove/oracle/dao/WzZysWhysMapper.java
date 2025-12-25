package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.WzZysWhys;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * KS问诊—职业史—危害因素(WzZysWhys)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:30:53
 */
public interface WzZysWhysMapper extends BaseMapper<WzZysWhys> {

    /**
     * 分页查询[KS问诊—职业史—危害因素]列表
     *
     * @param page  分页参数
     * @param param WzZysWhys查询参数
     * @return 分页数据
     */
    IPage<WzZysWhys> getPage(PageParam<WzZysWhys> page, @Param("param") WzZysWhys param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    WzZysWhys getInfoById(@Param("id") String id);

}
