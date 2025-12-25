package com.center.medical.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.HandleNewProjects;
import com.center.medical.bean.param.HandleNewParam;
import com.center.medical.bean.vo.HandleNewVo;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * KS检验科加项处理(HandleNewProjects)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:20
 */
public interface HandleNewProjectsMapper extends BaseMapper<HandleNewProjects> {

    /**
     * 分页查询[KS检验科加项处理]列表
     *
     * @param page  分页参数
     * @param param HandleNewProjects查询参数
     * @return 分页数据
     */
    IPage<HandleNewVo> getList(PageParam<HandleNewProjects> page, @Param("param") HandleNewParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    HandleNewProjects getInfoById(@Param("id") String id);

}
