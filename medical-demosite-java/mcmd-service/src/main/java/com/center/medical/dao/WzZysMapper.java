package com.center.medical.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.bean.model.WzZys;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * KS问诊——职业史(WzZys)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:35:11
 */
public interface WzZysMapper extends BaseMapper<WzZys> {

    /**
     * 分页查询[KS问诊——职业史]列表
     *
     * @param page  分页参数
     * @param param WzZys查询参数
     * @return 分页数据
     */
    IPage<WzZys> getList(PageParam<WzZys> page, @Param("param") WzZys param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    WzZys getInfoById(@Param("id") String id);


}
