package com.center.medical.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.bean.model.PeisOl;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 体检者线上信息(PeisOl)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:35:34
 */
public interface PeisOlMapper extends BaseMapper<PeisOl> {

    /**
     * 分页查询[体检者线上信息]列表
     *
     * @param page  分页参数
     * @param param PeisOl查询参数
     * @return 分页数据
     */
    IPage<PeisOl> getList(PageParam<PeisOl> page, @Param("param") PeisOl param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    PeisOl getInfoById(@Param("id") String id);

}
