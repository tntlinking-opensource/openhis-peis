package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdDanagerObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * TJ危害因素收费项目(MdDanagerObject)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:45:20
 */
public interface MdDanagerObjectMapper extends BaseMapper<MdDanagerObject> {

    /**
     * 分页查询[TJ危害因素收费项目]列表
     *
     * @param page  分页参数
     * @param param MdDanagerObject查询参数
     * @return 分页数据
     */
    IPage<MdDanagerObject> getPage(PageParam<MdDanagerObject> page, @Param("param") MdDanagerObject param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdDanagerObject getInfoById(@Param("id") String id);

}
