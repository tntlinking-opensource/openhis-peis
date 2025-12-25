package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.DanagerObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * TJ危害因素收费项目(DanagerObject)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:18:07
 */
public interface DanagerObjectMapper extends BaseMapper<DanagerObject> {

    /**
     * 分页查询[TJ危害因素收费项目]列表
     *
     * @param page  分页参数
     * @param param DanagerObject查询参数
     * @return 分页数据
     */
    IPage<DanagerObject> getPage(PageParam<DanagerObject> page, @Param("param") DanagerObject param);


}
