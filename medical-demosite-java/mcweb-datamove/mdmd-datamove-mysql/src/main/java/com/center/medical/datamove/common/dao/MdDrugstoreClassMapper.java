package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdDrugstoreClass;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 药品分类(MdDrugstoreClass)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:46:14
 */
public interface MdDrugstoreClassMapper extends BaseMapper<MdDrugstoreClass> {

    /**
     * 分页查询[药品分类]列表
     *
     * @param page  分页参数
     * @param param MdDrugstoreClass查询参数
     * @return 分页数据
     */
    IPage<MdDrugstoreClass> getPage(PageParam<MdDrugstoreClass> page, @Param("param") MdDrugstoreClass param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdDrugstoreClass getInfoById(@Param("id") String id);

}
