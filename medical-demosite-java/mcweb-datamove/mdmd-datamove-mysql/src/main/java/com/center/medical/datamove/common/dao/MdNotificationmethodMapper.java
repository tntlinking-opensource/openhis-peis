package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdNotificationmethod;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 通知方式（领取方式）表(MdNotificationmethod)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:46:27
 */
public interface MdNotificationmethodMapper extends BaseMapper<MdNotificationmethod> {

    /**
     * 分页查询[通知方式（领取方式）表]列表
     *
     * @param page  分页参数
     * @param param MdNotificationmethod查询参数
     * @return 分页数据
     */
    IPage<MdNotificationmethod> getPage(PageParam<MdNotificationmethod> page, @Param("param") MdNotificationmethod param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdNotificationmethod getInfoById(@Param("id") String id);

}
