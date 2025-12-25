package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.PeisQuestionnaire;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 体检者沃德国际健康问卷(PeisQuestionnaire)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:22:58
 */
public interface PeisQuestionnaireMapper extends BaseMapper<PeisQuestionnaire> {

    /**
     * 分页查询[体检者沃德国际健康问卷]列表
     *
     * @param page  分页参数
     * @param param PeisQuestionnaire查询参数
     * @return 分页数据
     */
    IPage<PeisQuestionnaire> getPage(PageParam<PeisQuestionnaire> page, @Param("param") PeisQuestionnaire param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    PeisQuestionnaire getInfoById(@Param("id") String id);

}
