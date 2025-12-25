package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdNotifySmsVisit;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 短信回访表(MdNotifySmsVisit)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:46:27
 */
public interface MdNotifySmsVisitMapper extends BaseMapper<MdNotifySmsVisit> {

    /**
     * 分页查询[短信回访表]列表
     *
     * @param page  分页参数
     * @param param MdNotifySmsVisit查询参数
     * @return 分页数据
     */
    IPage<MdNotifySmsVisit> getPage(PageParam<MdNotifySmsVisit> page, @Param("param") MdNotifySmsVisit param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdNotifySmsVisit getInfoById(@Param("id") String id);

}
