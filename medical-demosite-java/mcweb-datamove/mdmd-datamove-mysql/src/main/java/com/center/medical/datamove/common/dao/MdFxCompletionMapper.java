package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdFxCompletion;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 本次职业健康检查漏检人员及漏检项目人员一览表(MdFxCompletion)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:46:18
 */
public interface MdFxCompletionMapper extends BaseMapper<MdFxCompletion> {

    /**
     * 分页查询[本次职业健康检查漏检人员及漏检项目人员一览表]列表
     *
     * @param page  分页参数
     * @param param MdFxCompletion查询参数
     * @return 分页数据
     */
    IPage<MdFxCompletion> getPage(PageParam<MdFxCompletion> page, @Param("param") MdFxCompletion param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdFxCompletion getInfoById(@Param("id") String id);

}
