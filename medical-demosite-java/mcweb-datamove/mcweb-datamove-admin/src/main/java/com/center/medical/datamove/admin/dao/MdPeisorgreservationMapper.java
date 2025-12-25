package com.center.medical.datamove.admin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.datamove.admin.bean.model.MdPeisorgreservation;
import org.apache.ibatis.annotations.Param;

/**
 * 体检者团体任务(MdPeisorgreservation)数据库访问层
 *
 * @author ay
 * @since 2023-08-12 11:53:29
 */
public interface MdPeisorgreservationMapper extends BaseMapper<MdPeisorgreservation> {

    /**
     * 分页查询[体检者团体任务]列表
     *
     * @param page  分页参数
     * @param param MdPeisorgreservation查询参数
     * @return 分页数据
     */
    IPage<MdPeisorgreservation> getPage(PageParam<MdPeisorgreservation> page, @Param("param") MdPeisorgreservation param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdPeisorgreservation getInfoById(@Param("id") String id);

}
