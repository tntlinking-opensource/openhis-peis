package com.center.medical.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.bean.model.DrugAndFzx;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 药品分中心映射(DrugAndFzx)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:33
 */
public interface DrugAndFzxMapper extends BaseMapper<DrugAndFzx> {

    /**
     * 分页查询[药品分中心映射]列表
     *
     * @param page  分页参数
     * @param param DrugAndFzx查询参数
     * @return 分页数据
     */
    IPage<DrugAndFzx> getList(PageParam<DrugAndFzx> page, @Param("param") DrugAndFzx param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    DrugAndFzx getInfoById(@Param("id") String id);

}
