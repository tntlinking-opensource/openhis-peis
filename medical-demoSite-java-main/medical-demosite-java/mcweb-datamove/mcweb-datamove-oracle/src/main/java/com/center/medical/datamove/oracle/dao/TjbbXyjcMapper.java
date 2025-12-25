package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.TjbbXyjc;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * KS血压检测(TjbbXyjc)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:25:28
 */
public interface TjbbXyjcMapper extends BaseMapper<TjbbXyjc> {

    /**
     * 分页查询[KS血压检测]列表
     *
     * @param page  分页参数
     * @param param TjbbXyjc查询参数
     * @return 分页数据
     */
    IPage<TjbbXyjc> getPage(PageParam<TjbbXyjc> page, @Param("param") TjbbXyjc param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    TjbbXyjc getInfoById(@Param("id") String id);

}
