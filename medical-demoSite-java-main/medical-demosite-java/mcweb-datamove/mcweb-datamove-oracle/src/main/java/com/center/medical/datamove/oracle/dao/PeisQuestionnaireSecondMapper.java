package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.PeisQuestionnaireSecond;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 健康体检问卷(PeisQuestionnaireSecond)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:22:59
 */
public interface PeisQuestionnaireSecondMapper extends BaseMapper<PeisQuestionnaireSecond> {

    /**
     * 分页查询[健康体检问卷]列表
     *
     * @param page  分页参数
     * @param param PeisQuestionnaireSecond查询参数
     * @return 分页数据
     */
    IPage<PeisQuestionnaireSecond> getPage(PageParam<PeisQuestionnaireSecond> page, @Param("param") PeisQuestionnaireSecond param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    PeisQuestionnaireSecond getInfoById(@Param("id") String id);

}
