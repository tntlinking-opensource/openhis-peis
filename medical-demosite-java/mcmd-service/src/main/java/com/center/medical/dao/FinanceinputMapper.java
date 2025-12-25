package com.center.medical.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.bean.model.Financeinput;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 销售财务录入表(Financeinput)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:01
 */
public interface FinanceinputMapper extends BaseMapper<Financeinput> {

    /**
     * 分页查询[销售财务录入表]列表
     *
     * @param page  分页参数
     * @param param Financeinput查询参数
     * @return 分页数据
     */
    IPage<Financeinput> getList(PageParam<Financeinput> page, @Param("param") Financeinput param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    Financeinput getInfoById(@Param("id") String id);

}
