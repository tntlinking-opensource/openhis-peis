package com.center.medical.sellcrm.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.sellcrm.bean.model.Customerfollow;
import com.center.medical.sellcrm.bean.param.CustomerfollowPram;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 客户跟踪表(Customerfollow)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-21 09:02:48
 */
public interface CustomerfollowMapper extends BaseMapper<Customerfollow> {

    /**
     * 分页查询阶段跟踪列表
     *
     * @param page  分页参数
     * @param param Customerfollow查询参数
     * @return 分页数据
     */
    IPage<Customerfollow> getPage(PageParam<Customerfollow> page, @Param("param") CustomerfollowPram param);

    /**
     * 获取指定客户跟踪记录
     *
     * @param param 查询条件
     * @return 所有数据
     */
    List<Customerfollow> getListByCumId(@Param("param") CustomerfollowPram param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Customerfollow getInfoById(@Param("id") String id);
}
