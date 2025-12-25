package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdPeisQuestionnaire;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 体检者沃德国际健康问卷(MdPeisQuestionnaire)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:48:06
 */
public interface MdPeisQuestionnaireMapper extends BaseMapper<MdPeisQuestionnaire> {

    /**
     * 分页查询[体检者沃德国际健康问卷]列表
     *
     * @param page  分页参数
     * @param param MdPeisQuestionnaire查询参数
     * @return 分页数据
     */
    IPage<MdPeisQuestionnaire> getPage(PageParam<MdPeisQuestionnaire> page, @Param("param") MdPeisQuestionnaire param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdPeisQuestionnaire getInfoById(@Param("id") String id);

}
