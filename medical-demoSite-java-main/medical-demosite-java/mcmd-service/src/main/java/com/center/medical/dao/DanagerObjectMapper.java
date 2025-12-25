package com.center.medical.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.bean.model.DanagerObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * TJ危害因素收费项目(DanagerObject)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:37
 */
public interface DanagerObjectMapper extends BaseMapper<DanagerObject> {

    /**
     * 分页查询[TJ危害因素收费项目]列表
     *
     * @param page  分页参数
     * @param param DanagerObject查询参数
     * @return 分页数据
     */
    IPage<DanagerObject> getList(PageParam<DanagerObject> page, @Param("param") DanagerObject param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    DanagerObject getInfoById(@Param("id") String id);

}
