package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdTotalDoctor;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 总检-医生 关联表(MdTotalDoctor)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:49:09
 */
public interface MdTotalDoctorMapper extends BaseMapper<MdTotalDoctor> {

    /**
     * 分页查询[总检-医生 关联表]列表
     *
     * @param page  分页参数
     * @param param MdTotalDoctor查询参数
     * @return 分页数据
     */
    IPage<MdTotalDoctor> getPage(PageParam<MdTotalDoctor> page, @Param("param") MdTotalDoctor param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdTotalDoctor getInfoById(@Param("id") String id);

}
