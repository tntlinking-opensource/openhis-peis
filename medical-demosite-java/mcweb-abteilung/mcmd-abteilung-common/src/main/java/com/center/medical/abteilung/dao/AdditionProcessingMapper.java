package com.center.medical.abteilung.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.abteilung.bean.param.AdditionProcessingParam;
import com.center.medical.abteilung.bean.vo.AdditionProcessingVo;
import com.center.medical.bean.model.HandleNewProjects;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * KS检验科加项处理(HandleNewProjects)表数据库访问层
 *
 * @author makejava
 * @since 2023-01-29 10:48:47
 */
public interface AdditionProcessingMapper extends BaseMapper<HandleNewProjects> {

    /**
     * 分页查询[KS检验科加项处理]列表
     *
     * @param page  分页参数
     * @param param HandleNewProjects查询参数
     * @return 分页数据
     */
    IPage<AdditionProcessingVo> getList(PageParam<HandleNewProjects> page, @Param("param") AdditionProcessingParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    HandleNewProjects getInfoById(@Param("id") String id);


}
