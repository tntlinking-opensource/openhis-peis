package com.center.medical.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.bean.model.TjbbGmd;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * KS骨密度体检报表(TjbbGmd)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:35:38
 */
public interface TjbbGmdMapper extends BaseMapper<TjbbGmd> {

    /**
     * 分页查询[KS骨密度体检报表]列表
     *
     * @param page  分页参数
     * @param param TjbbGmd查询参数
     * @return 分页数据
     */
    IPage<TjbbGmd> getList(PageParam<TjbbGmd> page, @Param("param") TjbbGmd param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    TjbbGmd getInfoById(@Param("id") String id);

}
