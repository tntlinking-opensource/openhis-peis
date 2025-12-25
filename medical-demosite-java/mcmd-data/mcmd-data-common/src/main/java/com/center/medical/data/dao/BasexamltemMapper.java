package com.center.medical.data.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.Basexamltem;
import com.center.medical.data.bean.param.BaseExamItemParam;
import com.center.medical.data.bean.vo.ExamsByItemVo;
import org.apache.ibatis.annotations.Param;

/**
 * JC检查项目表(Basexamltem)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:28
 */
public interface BasexamltemMapper extends BaseMapper<Basexamltem> {

    /**
     * 分页查询[JC检查项目表]列表
     *
     * @param page  分页参数
     * @param param Basexamltem查询参数
     * @return 分页数据
     */
    IPage<Basexamltem> getList(PageParam<Basexamltem> page, @Param("param") BaseExamItemParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    Basexamltem getInfoById(@Param("id") String id);

    /**
     * 根据inputCode获取检查项目名称
     * @param page
     * @param inputCode
     * @return
     */
    PageParam<Basexamltem> getAllJcid(PageParam<Basexamltem> page,@Param("inputCode") String inputCode);

    /**
     * 获取右侧表格子表格数据
     * @param page
     * @param id
     * @return
     */
    IPage<ExamsByItemVo> getExamsByItemId(PageParam<ExamsByItemVo> page,@Param("id") String id);
}
