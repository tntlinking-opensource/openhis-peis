package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdZyFhcsGrClass;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * JC个人防护用品种类(MdZyFhcsGrClass)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:54:28
 */
public interface MdZyFhcsGrClassMapper extends BaseMapper<MdZyFhcsGrClass> {

    /**
     * 分页查询[JC个人防护用品种类]列表
     *
     * @param page  分页参数
     * @param param MdZyFhcsGrClass查询参数
     * @return 分页数据
     */
    IPage<MdZyFhcsGrClass> getPage(PageParam<MdZyFhcsGrClass> page, @Param("param") MdZyFhcsGrClass param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdZyFhcsGrClass getInfoById(@Param("id") String id);

}
