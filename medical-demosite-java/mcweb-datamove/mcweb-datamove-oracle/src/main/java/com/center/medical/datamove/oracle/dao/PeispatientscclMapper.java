package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.Peispatientsccl;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 体检号生成策略(Peispatientsccl)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:23:31
 */
public interface PeispatientscclMapper extends BaseMapper<Peispatientsccl> {

    /**
     * 分页查询[体检号生成策略]列表
     *
     * @param page  分页参数
     * @param param Peispatientsccl查询参数
     * @return 分页数据
     */
    IPage<Peispatientsccl> getPage(PageParam<Peispatientsccl> page, @Param("param") Peispatientsccl param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Peispatientsccl getInfoById(@Param("id") String id);

}
