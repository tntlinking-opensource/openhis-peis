package com.center.medical.data.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.Basexamltemtype;
import com.center.medical.data.bean.param.ExamItemTypePrama;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * JC检查项目类型表(Basexamltemtype)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:51
 */
public interface BasexamltemtypeMapper extends BaseMapper<Basexamltemtype> {

    /**
     * 分页查询[JC检查项目类型表]列表
     *
     * @param page  分页参数
     * @param param Basexamltemtype查询参数
     * @return 分页数据
     */
    IPage<Basexamltemtype> getPage(PageParam<Basexamltemtype> page, @Param("param") ExamItemTypePrama param);

    /**
     * 根据输入码查询查询JC检查项目类型列表
     *
     * @param param Basexamltemtype查询参数
     * @return 列表数据
     */
    List<Basexamltemtype> getList(@Param("param") ExamItemTypePrama param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    Basexamltemtype getInfoById(@Param("id") String id);

}
