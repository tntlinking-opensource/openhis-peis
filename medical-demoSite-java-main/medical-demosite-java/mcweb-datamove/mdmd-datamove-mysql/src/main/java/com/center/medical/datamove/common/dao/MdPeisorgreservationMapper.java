package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdPeisorgreservation;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 体检者团体任务(MdPeisorgreservation)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:48:09
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
