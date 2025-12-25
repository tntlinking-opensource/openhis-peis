package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.DmyhResult;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * KS动脉硬化结果(DmyhResult)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:18:19
 */
public interface DmyhResultMapper extends BaseMapper<DmyhResult> {

    /**
     * 分页查询[KS动脉硬化结果]列表
     *
     * @param page  分页参数
     * @param param DmyhResult查询参数
     * @return 分页数据
     */
    IPage<DmyhResult> getPage(PageParam<DmyhResult> page, @Param("param") DmyhResult param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    DmyhResult getInfoById(@Param("id") String id);

}
