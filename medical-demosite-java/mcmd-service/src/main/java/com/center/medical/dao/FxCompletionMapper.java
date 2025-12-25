package com.center.medical.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.bean.model.FxCompletion;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 本次职业健康检查漏检人员及漏检项目人员一览表(FxCompletion)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:17
 */
public interface FxCompletionMapper extends BaseMapper<FxCompletion> {

    /**
     * 分页查询[本次职业健康检查漏检人员及漏检项目人员一览表]列表
     *
     * @param page  分页参数
     * @param param FxCompletion查询参数
     * @return 分页数据
     */
    IPage<FxCompletion> getList(PageParam<FxCompletion> page, @Param("param") FxCompletion param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    FxCompletion getInfoById(@Param("id") String id);

}
