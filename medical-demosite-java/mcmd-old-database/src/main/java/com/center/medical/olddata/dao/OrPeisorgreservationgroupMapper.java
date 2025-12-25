package com.center.medical.olddata.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.OrPeisorgreservationgroup;
import org.apache.ibatis.annotations.Param;

/**
 * QT体检团体任务分组表(Peisorgreservationgroup)数据库访问层
 *
 * @author ay
 * @since 2023-08-12 11:54:58
 */
public interface OrPeisorgreservationgroupMapper extends BaseMapper<OrPeisorgreservationgroup> {

    /**
     * 分页查询[QT体检团体任务分组表]列表
     *
     * @param page  分页参数
     * @param param Peisorgreservationgroup查询参数
     * @return 分页数据
     */
    IPage<OrPeisorgreservationgroup> getPage(PageParam<OrPeisorgreservationgroup> page, @Param("param") OrPeisorgreservationgroup param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    OrPeisorgreservationgroup getInfoById(@Param("id") String id);

}
