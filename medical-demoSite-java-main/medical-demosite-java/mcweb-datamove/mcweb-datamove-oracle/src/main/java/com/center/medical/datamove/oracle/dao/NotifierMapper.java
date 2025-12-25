package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.Notifier;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * BG报告领取通知(Notifier)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:22:11
 */
public interface NotifierMapper extends BaseMapper<Notifier> {

    /**
     * 分页查询[BG报告领取通知]列表
     *
     * @param page  分页参数
     * @param param Notifier查询参数
     * @return 分页数据
     */
    IPage<Notifier> getPage(PageParam<Notifier> page, @Param("param") Notifier param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Notifier getInfoById(@Param("id") String id);

}
