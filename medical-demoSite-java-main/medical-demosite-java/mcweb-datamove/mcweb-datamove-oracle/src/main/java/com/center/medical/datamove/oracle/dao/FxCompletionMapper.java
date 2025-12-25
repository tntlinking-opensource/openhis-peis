package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.FxCompletion;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 本次职业健康检查漏检人员及漏检项目人员一览表(FxCompletion)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:18:47
 */
public interface FxCompletionMapper extends BaseMapper<FxCompletion> {

    /**
     * 分页查询[本次职业健康检查漏检人员及漏检项目人员一览表]列表
     *
     * @param page  分页参数
     * @param param FxCompletion查询参数
     * @return 分页数据
     */
    IPage<FxCompletion> getPage(PageParam<FxCompletion> page, @Param("param") FxCompletion param);


}
