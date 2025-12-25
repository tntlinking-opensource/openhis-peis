package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdPeisorgreservationgroup;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 体检者任务分组(MdPeisorgreservationgroup)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:48:09
 */
public interface MdPeisorgreservationgroupMapper extends BaseMapper<MdPeisorgreservationgroup> {

    /**
     * 分页查询[体检者任务分组]列表
     *
     * @param page  分页参数
     * @param param MdPeisorgreservationgroup查询参数
     * @return 分页数据
     */
    IPage<MdPeisorgreservationgroup> getPage(PageParam<MdPeisorgreservationgroup> page, @Param("param") MdPeisorgreservationgroup param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdPeisorgreservationgroup getInfoById(@Param("id") String id);

}
