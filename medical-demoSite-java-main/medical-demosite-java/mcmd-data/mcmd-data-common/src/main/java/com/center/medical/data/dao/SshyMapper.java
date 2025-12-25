package com.center.medical.data.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.Sshy;
import org.apache.ibatis.annotations.Param;

/**
 * 创建团体客户要选择的所属行业在这里维护(Sshy)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-19 14:11:08
 */
public interface SshyMapper extends BaseMapper<Sshy> {

    /**
     * 分页查询[创建团体客户要选择的所属行业在这里维护]列表
     *
     * @param page  分页参数
     * @param param Sshy查询参数
     * @return 分页数据
     */
    IPage<Sshy> getPage(PageParam<Sshy> page, @Param("param") Sshy param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Sshy getInfoById(@Param("id") String id);

}
