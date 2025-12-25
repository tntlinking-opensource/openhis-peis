package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.ZyFhcsGrClass;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * JC个人防护用品种类(ZyFhcsGrClass)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:30:56
 */
public interface ZyFhcsGrClassMapper extends BaseMapper<ZyFhcsGrClass> {

    /**
     * 分页查询[JC个人防护用品种类]列表
     *
     * @param page  分页参数
     * @param param ZyFhcsGrClass查询参数
     * @return 分页数据
     */
    IPage<ZyFhcsGrClass> getPage(PageParam<ZyFhcsGrClass> page, @Param("param") ZyFhcsGrClass param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    ZyFhcsGrClass getInfoById(@Param("id") String id);

}
