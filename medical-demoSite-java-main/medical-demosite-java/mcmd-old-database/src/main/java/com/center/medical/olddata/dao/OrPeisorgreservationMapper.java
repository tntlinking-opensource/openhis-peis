package com.center.medical.olddata.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.OrPeisorgreservation;
import org.apache.ibatis.annotations.Param;

/**
 * 体检团体任务表(Peisorgreservation)数据库访问层
 *
 * @author ay
 * @since 2023-08-12 11:54:56
 */
public interface OrPeisorgreservationMapper extends BaseMapper<OrPeisorgreservation> {

    /**
     * 分页查询[体检团体任务表]列表
     *
     * @param page  分页参数
     * @param param Peisorgreservation查询参数
     * @return 分页数据
     */
    IPage<OrPeisorgreservation> getPage(PageParam<OrPeisorgreservation> page, @Param("param") OrPeisorgreservation param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    OrPeisorgreservation getInfoById(@Param("id") String id);

}
