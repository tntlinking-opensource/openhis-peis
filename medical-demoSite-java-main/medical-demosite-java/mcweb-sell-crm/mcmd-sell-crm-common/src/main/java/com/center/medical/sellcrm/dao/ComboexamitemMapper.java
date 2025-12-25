package com.center.medical.sellcrm.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.sellcrm.bean.model.Comboexamitem;
import org.apache.ibatis.annotations.Param;

/**
 * 用于判断职业小结(Comboexamitem)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:35:26
 */
public interface ComboexamitemMapper extends BaseMapper<Comboexamitem> {

    /**
     * 分页查询[用于判断职业小结]列表
     *
     * @param page  分页参数
     * @param param Comboexamitem查询参数
     * @return 分页数据
     */
    IPage<Comboexamitem> getList(PageParam<Comboexamitem> page, @Param("param") Comboexamitem param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    Comboexamitem getInfoById(@Param("id") String id);

}
