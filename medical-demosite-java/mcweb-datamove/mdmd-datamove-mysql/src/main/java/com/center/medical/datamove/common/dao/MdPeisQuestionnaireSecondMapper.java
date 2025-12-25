package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdPeisQuestionnaireSecond;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 健康体检问卷(MdPeisQuestionnaireSecond)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:48:07
 */
public interface MdPeisQuestionnaireSecondMapper extends BaseMapper<MdPeisQuestionnaireSecond> {

    /**
     * 分页查询[健康体检问卷]列表
     *
     * @param page  分页参数
     * @param param MdPeisQuestionnaireSecond查询参数
     * @return 分页数据
     */
    IPage<MdPeisQuestionnaireSecond> getPage(PageParam<MdPeisQuestionnaireSecond> page, @Param("param") MdPeisQuestionnaireSecond param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdPeisQuestionnaireSecond getInfoById(@Param("id") String id);

}
