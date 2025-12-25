package com.center.medical.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.bean.model.WzZysWhys;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * KS问诊—职业史—危害因素(WzZysWhys)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:05
 */
public interface WzZysWhysMapper extends BaseMapper<WzZysWhys> {

    /**
     * 分页查询[KS问诊—职业史—危害因素]列表
     *
     * @param page  分页参数
     * @param param WzZysWhys查询参数
     * @return 分页数据
     */
    IPage<WzZysWhys> getList(PageParam<WzZysWhys> page, @Param("param") WzZysWhys param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    WzZysWhys getInfoById(@Param("id") String id);

}
