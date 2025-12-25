package com.center.medical.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.bean.model.DmyhResult;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * KS动脉硬化结果(DmyhResult)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:32
 */
public interface DmyhResultMapper extends BaseMapper<DmyhResult> {

    /**
     * 分页查询[KS动脉硬化结果]列表
     *
     * @param page  分页参数
     * @param param DmyhResult查询参数
     * @return 分页数据
     */
    IPage<DmyhResult> getList(PageParam<DmyhResult> page, @Param("param") DmyhResult param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    DmyhResult getInfoById(@Param("id") String id);

}
