package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.FxHarm;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 职业团检分析-危害因素(FxHarm)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:18:51
 */
public interface FxHarmMapper extends BaseMapper<FxHarm> {

    /**
     * 分页查询[职业团检分析-危害因素]列表
     *
     * @param page  分页参数
     * @param param FxHarm查询参数
     * @return 分页数据
     */
    IPage<FxHarm> getPage(PageParam<FxHarm> page, @Param("param") FxHarm param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    FxHarm getInfoById(@Param("id") String id);

}
