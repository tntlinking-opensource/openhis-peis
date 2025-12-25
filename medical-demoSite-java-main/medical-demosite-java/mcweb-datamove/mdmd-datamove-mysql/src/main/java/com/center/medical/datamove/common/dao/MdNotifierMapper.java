package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdNotifier;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * BG报告领取通知(MdNotifier)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:46:27
 */
public interface MdNotifierMapper extends BaseMapper<MdNotifier> {

    /**
     * 分页查询[BG报告领取通知]列表
     *
     * @param page  分页参数
     * @param param MdNotifier查询参数
     * @return 分页数据
     */
    IPage<MdNotifier> getPage(PageParam<MdNotifier> page, @Param("param") MdNotifier param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdNotifier getInfoById(@Param("id") String id);

}
