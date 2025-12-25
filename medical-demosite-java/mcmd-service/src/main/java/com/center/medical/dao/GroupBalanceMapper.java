package com.center.medical.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.bean.model.GroupBalance;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 体检团体结算表(GroupBalance)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:35:36
 */
public interface GroupBalanceMapper extends BaseMapper<GroupBalance> {

    /**
     * 分页查询[体检团体结算表]列表
     *
     * @param page  分页参数
     * @param param GroupBalance查询参数
     * @return 分页数据
     */
    IPage<GroupBalance> getList(PageParam<GroupBalance> page, @Param("param") GroupBalance param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    GroupBalance getInfoById(@Param("id") String id);

}
