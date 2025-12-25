package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.TjbbGmd;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * KS骨密度体检报表(TjbbGmd)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:25:24
 */
public interface TjbbGmdMapper extends BaseMapper<TjbbGmd> {

    /**
     * 分页查询[KS骨密度体检报表]列表
     *
     * @param page  分页参数
     * @param param TjbbGmd查询参数
     * @return 分页数据
     */
    IPage<TjbbGmd> getPage(PageParam<TjbbGmd> page, @Param("param") TjbbGmd param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    TjbbGmd getInfoById(@Param("id") String id);

}
