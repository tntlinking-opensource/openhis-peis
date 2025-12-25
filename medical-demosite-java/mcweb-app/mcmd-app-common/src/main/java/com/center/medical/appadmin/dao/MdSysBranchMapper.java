package com.center.medical.appadmin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.appadmin.bean.model.MdSysBranch;
import com.center.medical.appadmin.bean.param.MdSysBranchParam;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 分中心维护表(MdSysBranch)数据库访问层
 *
 * @author ay
 * @since 2024-06-11 16:03:16
 */
public interface MdSysBranchMapper extends BaseMapper<MdSysBranch> {

    /**
     * 分页查询[分中心维护表]列表
     *
     * @param page  分页参数
     * @param param MdSysBranch查询参数
     * @return 分页数据
     */
    IPage<MdSysBranch> getPage(PageParam<MdSysBranch> page, @Param("param") MdSysBranchParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdSysBranch getInfoById(@Param("id") Integer id);

}
