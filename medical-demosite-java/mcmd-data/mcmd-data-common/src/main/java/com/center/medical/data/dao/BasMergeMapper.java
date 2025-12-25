package com.center.medical.data.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.BasMerge;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 合并结伦词(BasMerge)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:14
 */
public interface BasMergeMapper extends BaseMapper<BasMerge> {

    /**
     * 分页查询[合并结伦词]列表
     *
     * @param page  分页参数
     * @param param BasMerge查询参数
     * @return 分页数据
     */
    IPage<BasMerge> getList(PageParam<BasMerge> page, @Param("param") BasMerge param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    BasMerge getInfoById(@Param("id") String id);

    /**
     * 通过Con和mid获取合并结伦词
     * @param str
     * @param mId
     * @param length
     * @return
     */
    List<BasMerge> getMergeByCon(@Param("str")String str, @Param("mId")String mId, @Param("length")int length);



}
