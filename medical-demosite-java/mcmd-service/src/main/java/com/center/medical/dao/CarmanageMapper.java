package com.center.medical.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.bean.model.Carmanage;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 体检车管理(Carmanage)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:35
 */
public interface CarmanageMapper extends BaseMapper<Carmanage> {

    /**
     * 分页查询[体检车管理]列表
     *
     * @param page  分页参数
     * @param param Carmanage查询参数
     * @return 分页数据
     */
    IPage<Carmanage> getList(PageParam<Carmanage> page, @Param("param") Carmanage param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    Carmanage getInfoById(@Param("id") String id);

}
