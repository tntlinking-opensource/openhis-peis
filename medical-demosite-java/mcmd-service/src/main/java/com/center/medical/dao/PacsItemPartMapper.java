package com.center.medical.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.bean.model.PacsItemPart;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 项目部位表(PacsItemPart)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:28
 */
public interface PacsItemPartMapper extends BaseMapper<PacsItemPart> {

    /**
     * 分页查询[项目部位表]列表
     *
     * @param page  分页参数
     * @param param PacsItemPart查询参数
     * @return 分页数据
     */
    IPage<PacsItemPart> getList(PageParam<PacsItemPart> page, @Param("param") PacsItemPart param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    PacsItemPart getInfoById(@Param("id") String id);

}
