package com.center.medical.data.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.Printtype;
import com.center.medical.data.bean.param.PrinttypePrama;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 销售打印分类设置(Printtype)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-18 09:11:15
 */
public interface PrinttypeMapper extends BaseMapper<Printtype> {

    /**
     * 分页查询[销售打印分类设置]列表
     *
     * @param page  分页参数
     * @param param Printtype查询参数
     * @return 分页数据
     */
    IPage<Printtype> getPage(PageParam<Printtype> page, @Param("param") PrinttypePrama param);

    /**
     * 根据输入码销售打印分类设置列表
     *
     * @param param 查询条件
     * @return 所有数据
     */
    List<Printtype> getList(@Param("param") PrinttypePrama param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Printtype getInfoById(@Param("id") String id);

}
