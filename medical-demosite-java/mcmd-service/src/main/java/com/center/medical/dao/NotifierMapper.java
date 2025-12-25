package com.center.medical.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.bean.model.Notifier;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * BG报告领取通知(Notifier)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:35:20
 */
public interface NotifierMapper extends BaseMapper<Notifier> {

    /**
     * 分页查询[BG报告领取通知]列表
     *
     * @param page  分页参数
     * @param param Notifier查询参数
     * @return 分页数据
     */
    IPage<Notifier> getList(PageParam<Notifier> page, @Param("param") Notifier param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    Notifier getInfoById(@Param("id") String id);

}
