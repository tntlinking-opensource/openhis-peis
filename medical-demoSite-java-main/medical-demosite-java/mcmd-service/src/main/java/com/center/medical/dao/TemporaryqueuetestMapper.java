package com.center.medical.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.bean.model.Temporaryqueuetest;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * temporaryqueuetest(Temporaryqueuetest)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:38
 */
public interface TemporaryqueuetestMapper extends BaseMapper<Temporaryqueuetest> {

    /**
     * 分页查询[temporaryqueuetest]列表
     *
     * @param page  分页参数
     * @param param Temporaryqueuetest查询参数
     * @return 分页数据
     */
    IPage<Temporaryqueuetest> getList(PageParam<Temporaryqueuetest> page, @Param("param") Temporaryqueuetest param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    Temporaryqueuetest getInfoById(@Param("id") String id);

}
