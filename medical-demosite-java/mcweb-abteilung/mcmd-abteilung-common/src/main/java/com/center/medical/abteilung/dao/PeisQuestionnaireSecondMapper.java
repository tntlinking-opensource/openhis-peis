package com.center.medical.abteilung.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.abteilung.bean.model.PeisQuestionnaireSecond;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 健康体检问卷(PeisQuestionnaireSecond)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:37
 */
public interface PeisQuestionnaireSecondMapper extends BaseMapper<PeisQuestionnaireSecond> {

    /**
     * 分页查询[健康体检问卷]列表
     *
     * @param page  分页参数
     * @param param PeisQuestionnaireSecond查询参数
     * @return 分页数据
     */
    IPage<PeisQuestionnaireSecond> getList(PageParam<PeisQuestionnaireSecond> page, @Param("param") PeisQuestionnaireSecond param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    PeisQuestionnaireSecond getInfoById(@Param("id") String id);

}
