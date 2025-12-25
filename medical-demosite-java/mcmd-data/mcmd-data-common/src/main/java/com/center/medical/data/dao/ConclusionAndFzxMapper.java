package com.center.medical.data.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.ConclusionAndFzx;
import org.apache.ibatis.annotations.Param;

/**
 * JC结伦词和分中心关联表(ConclusionAndFzx)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:03:57
 */
public interface ConclusionAndFzxMapper extends BaseMapper<ConclusionAndFzx> {

    /**
     * 分页查询[JC结伦词和分中心关联表]列表
     *
     * @param page  分页参数
     * @param param ConclusionAndFzx查询参数
     * @return 分页数据
     */
    IPage<ConclusionAndFzx> getList(PageParam<ConclusionAndFzx> page, @Param("param") ConclusionAndFzx param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id ConclusionAndFzx查询参数
     * @return 分页数据
     */
    ConclusionAndFzx getInfoById(@Param("id") String id);

    /**
     * 插入公共结伦词分中心关联记录
     *
     * @param branchId
     */
    void addWithBrandId(String branchId);
}
